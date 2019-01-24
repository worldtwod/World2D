package com.titicolab.puppeteer;

import android.content.Context;

import com.titicolab.nanux.core.Controller;
import com.titicolab.nanux.core.ObservableLifeCycle;
import com.titicolab.nanux.core.Puppeteer;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.graphics.texture.TextureManager;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.puppeteer.view.GLGraphicView;

public class AndroidGraphicBuilder {

    private static final int DEFAULT_MAX_SIZE_SPRITES = 1000;
    private static final int DEFAULT_MAX_SIZE_GEOMETRIES = 200;
    
    private final Context context;

    private int sizeSprites;
    private int sizeGeometries;
    private boolean debugGlView;

    private DisplayInfo         mDisplayInfo;
    private DrawTools.Builder   mDrawToolsBuilder;
    private Controller          mController;
    private GLGraphicView       mGLGraphicView;
    private TextureManager      mTextureManager;
    private ObservableInput     mObservableInput;
    private ObservableLifeCycle mObservableLifeCycle;
    private AndroidRenderer     mObservableRenderer;

    private boolean mDisplayFPS = false;


    AndroidGraphicBuilder(Context context) {
        this.context = context;
        this.sizeGeometries = DEFAULT_MAX_SIZE_GEOMETRIES;
        this.sizeSprites    = DEFAULT_MAX_SIZE_SPRITES;
        this.debugGlView = false;

    }

    public AndroidGraphicBuilder setGLGameViewDebug(boolean debug) {
        this.debugGlView = debug;
        return this;
    }

    public AndroidGraphicBuilder setGLGameView(GLGraphicView mGLGraphicView){
        this.mGLGraphicView = mGLGraphicView;
        return this;
    }

    public AndroidGraphicBuilder setSizeSprites(int sizeSprites) {
        this.sizeSprites = sizeSprites;
        return this;
    }

    public AndroidGraphicBuilder setSizeGeometries(int sizeGeometries) {
        this.sizeGeometries = sizeGeometries;
        return this;
    }

    public AndroidGraphicBuilder setController(Controller controller) {
        mController = controller;
        return this;
    }

    public AndroidGraphicBuilder setDisplayFPS(boolean displayFPS) {
        mDisplayFPS = displayFPS;
        return this ;
    }

    /**
     * Build the AndroidGraphic
     */
    public AndroidGraphic build(){

        DisplayInfo displayInfo = mDisplayInfo!=null?mDisplayInfo:
                providerDisplayInfo(context);

        GLGraphicView glGraphicView  =  mGLGraphicView!=null?mGLGraphicView:
                providerGraphicView(context,displayInfo,debugGlView);

        TextureManager textureManager = mTextureManager!=null?mTextureManager:
                providerTextureManager(context,displayInfo);

        DrawTools.Builder drawToolsBuilder = mDrawToolsBuilder !=null?mDrawToolsBuilder:
                providerDrawTools(context,sizeSprites,sizeGeometries);

        Controller controller =  mController!=null? mController:
                providerController(drawToolsBuilder);

        ObservableInput observableInput = mObservableInput!=null? mObservableInput:
                providerObservableInput(glGraphicView);

        ObservableLifeCycle observableLifeCycle = mObservableLifeCycle!=null? mObservableLifeCycle:
                providerObservableLifeCycle();

        AndroidRenderer observableRenderer = mObservableRenderer!=null?mObservableRenderer:
                providerObservableRenderer(glGraphicView);


        AndroidGraphic androidGame = new AndroidGraphic(
                controller,
                displayInfo,
                textureManager,
                observableRenderer,
                observableLifeCycle,
                observableInput,
                null,
                glGraphicView);

        observableRenderer.setGraphicContext(androidGame);
        controller.showFPS(mDisplayFPS);

        return  androidGame;
    }


    /**  Providers to inject instances in Graphic context implementation *******************/

    private DisplayInfo providerDisplayInfo(Context context) {
        return new AndroidDisplayMetrics(context);
    }

    private DrawTools.Builder providerDrawTools(Context context,int sizeSprites, int sizeGeometries){
        return new AndroidDrawToolsBuilder(context,sizeSprites, sizeGeometries);
    }

    private AndroidTextureManager providerTextureManager(Context context, DisplayInfo displayInfo){
        return new AndroidTextureManager(
                context, new RunnerTask(), displayInfo);
    }

    /**
     * This provides a default GLGameView
     * @param context     app context
     * @param displayInfo display info
     * @param debug       GL debugGlView
     */
    private GLGraphicView providerGraphicView(Context context, DisplayInfo displayInfo, boolean debug){
        GLGraphicView glGraphicView =  new GLGraphicView(context);
        glGraphicView.setUpConfiguration();
        glGraphicView.setDebug(debug);
        glGraphicView.getHolder().setFixedSize(
                displayInfo.getFixWidth(),
                displayInfo.getFixHeight());
        return glGraphicView;
    }

    /** provide default controller **/
    private Controller providerController(DrawTools.Builder drawTools){
        return  new Puppeteer(drawTools);
    }

    /** provider to observers **/
    private ObservableInput providerObservableInput(GLGraphicView glGraphicView){
        return new AndroidInput(glGraphicView);
    }
    private ObservableLifeCycle providerObservableLifeCycle(){
        return new AndroidLifeCycle();
    }

    private AndroidRenderer providerObservableRenderer(GLGraphicView glGraphicView){
        return new AndroidRenderer(glGraphicView);
    }


}
