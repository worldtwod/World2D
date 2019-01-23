package com.titicolab.puppeteer;

import android.content.Context;

import com.titicolab.nanux.core.BaseGraphic;
import com.titicolab.nanux.core.Controller;
import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.core.ObservableLifeCycle;
import com.titicolab.nanux.core.ObservableRenderer;
import com.titicolab.nanux.core.Puppeteer;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.core.SceneLauncher;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.screen.GraphicView;
import com.titicolab.nanux.touch.ObservableInput;
import com.titicolab.nanux.util.DisplayInfo;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.puppeteer.view.GLGraphicView;

public class AndroidGraphicBuilder {

    private static final int DEFAULT_MAX_SIZE_SPRITES = 1000;
    private static final int DEFAULT_MAX_SIZE_GEOMETRIES = 200;
    
    private final Context context;

    private GLGraphicView mGLGraphicView;
    private SceneLauncher sceneLauncher;
    private Controller    controller;

    private int sizeSprites;
    private int sizeGeometries;

    private boolean debug;
    private boolean showFPS;


    private DisplayInfo mDisplayInfo;


    public AndroidGraphicBuilder(Context context) {
        this.context = context;
        this.sizeGeometries = DEFAULT_MAX_SIZE_GEOMETRIES;
        this.sizeSprites    = DEFAULT_MAX_SIZE_SPRITES;
        this.debug = false;
        this.showFPS = false;
    }

    public AndroidGraphicBuilder setGLGameViewDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public AndroidGraphicBuilder setGLGameView(GLGraphicView mGLGraphicView){
        this.mGLGraphicView = mGLGraphicView;
        return this;
    }

    public AndroidGraphicBuilder setSceneLauncher(SceneLauncher sceneLauncher) {
        this.sceneLauncher = sceneLauncher;
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
        this.controller = controller;
        return this;
    }

    /**
     * Build the AndroidGraphic
     */
    public AndroidGraphic build(){

        // Create a instance
        //setupDisplayInfo(androidGame);

        DisplayInfo displayInfo = mDisplayInfo!=null?mDisplayInfo:providerDisplayInfo(context);

        GraphicView graphicView = mGLGraphicView!=null?mGLGraphicView:
                providerGraphicView(context,displayInfo,debug);



        //setupObservables(androidGame,androidGame.getGLGameView());

        //setupTextureManager(androidGame);

        //setupMonitorEngine(androidGame);

        //Set controller an buffer sizes
        //setupController(androidGame);

        AndroidGraphic androidGame = new AndroidGraphic();

       //
        androidGame.getObservableRenderer().start();

        /*if(sceneLauncher!=null)
            androidGame.setStartScene(sceneLauncher.onLaunchScene())*/

        return  androidGame;
    }

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
     * @param debug       GL debug
     */
    private GraphicView providerGraphicView(Context context, DisplayInfo displayInfo, boolean debug){
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
    private ObservableRenderer providerObservableRenderer(GraphicContext graphicContext,
                                                          GLGraphicView glGraphicView){
        return new AndroidRenderer(graphicContext, glGraphicView);
    }

}
