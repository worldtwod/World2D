package com.titicolab.puppeteer;

import android.content.Context;
import com.titicolab.nanux.core.BaseGraphic;
import com.titicolab.nanux.core.Puppeteer;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.core.SceneLauncher;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.puppeteer.view.GLGameView;

public class AndroidGraphicBuilder {

    private static final int DEFAULT_MAX_SIZE_SPRITES = 1000;
    private static final int DEFAULT_MAX_SIZE_GEOMETRIES = 200;
    
    private final Context context;

    private GLGameView    mGLGameView;
    private SceneLauncher sceneLauncher;

    private int sizeSprites;
    private int sizeGeometries;

    private boolean debug;
    private boolean showFPS;

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

    public AndroidGraphicBuilder setGLGameView(GLGameView mGLGameView){
        this.mGLGameView    = mGLGameView;
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

    /**
     * Build the AndroidGraphic
     */
    public AndroidGraphic build(){

        // Create a instance
        AndroidGraphic androidGame = new AndroidGraphic();

        setupDisplayInfo(androidGame);

        setupGLGameView(androidGame,debug);

        setupObservables(androidGame,androidGame.getGLGameView());

        setupTextureManager(androidGame);

        setupMonitorEngine(androidGame);

        //Set controller an buffer sizes
        setupController(androidGame);

       //
        androidGame.getObservableRenderer().start();

        if(sceneLauncher!=null)
            androidGame.setStartScene(sceneLauncher.onLaunchScene());


        return  androidGame;
    }

    private void setupDisplayInfo(AndroidGraphic androidGame) {
        androidGame.setDisplayInfo(new AndroidDisplayMetrics(context));
    }

    private void setupMonitorEngine(BaseGraphic androidGame) {
        androidGame.setMonitorEngineCreated(GameActivityTestTools.getMonitor());
    }

    private void setupTextureManager(BaseGraphic androidGame) {
        androidGame.setTextureManager(new AndroidTextureManager(
                context, new RunnerTask(), androidGame.getDisplayInfo()));
    }

    private void setupGLGameView(AndroidGraphic androidGame, boolean debug){
        GLGameView glGameView = mGLGameView!= null? mGLGameView: new GLGameView(context);
        androidGame.setGLGameView(glGameView);

        glGameView.setUpConfiguration();
        glGameView.setDebug(debug);
        glGameView.getHolder().setFixedSize(
                androidGame.getDisplayInfo().getFixWidth(),
                androidGame.getDisplayInfo().getFixHeight());
    }

    private void setupObservables(AndroidGraphic androidGame, GLGameView glGameView){
        androidGame.setObservableInput(new AndroidInput(glGameView));
        androidGame.setObservableLifeCycle(new AndroidLifeCycle());
        androidGame.setObservableRenderer(new AndroidRenderer(androidGame,glGameView));
    }

    private void setupController(BaseGraphic androidGame) {
        Puppeteer controller = new Puppeteer(
                new AndroidDrawToolsBuilder(context, sizeSprites, sizeGeometries));
        androidGame.connectObserver(controller);
    }

}
