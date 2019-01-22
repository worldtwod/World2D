package com.titicolab.puppeteer;

import android.content.Context;
import com.titicolab.nanux.core.BaseGame;
import com.titicolab.nanux.core.Puppeteer;
import com.titicolab.nanux.core.RunnerTask;
import com.titicolab.nanux.core.SceneLauncher;
import com.titicolab.opengl.shader.AndroidDrawToolsBuilder;
import com.titicolab.opengl.shader.AndroidTextureManager;
import com.titicolab.puppeteer.view.GLGameView;

public class AndroidGameBuilder {

    private static final int DEFAULT_MAX_SIZE_SPRITES = 1000;
    private static final int DEFAULT_MAX_SIZE_GEOMETRIES = 200;
    
    private final Context context;

    private GLGameView    mGLGameView;
    private SceneLauncher sceneLauncher;

    private int sizeSprites;
    private int sizeGeometries;

    private boolean debug;
    private boolean showFPS;

    public AndroidGameBuilder(Context context) {
        this.context = context;
        this.sizeGeometries = DEFAULT_MAX_SIZE_GEOMETRIES;
        this.sizeSprites    = DEFAULT_MAX_SIZE_SPRITES;
        this.debug = false;
        this.showFPS = false;
    }


    public AndroidGameBuilder setGLGameViewDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public AndroidGameBuilder setGLGameView(GLGameView mGLGameView){
        this.mGLGameView    = mGLGameView;
        return this;
    }

    public AndroidGameBuilder setSceneLauncher(SceneLauncher sceneLauncher) {
        this.sceneLauncher = sceneLauncher;
        return this;
    }

    public AndroidGameBuilder setSizeSprites(int sizeSprites) {
        this.sizeSprites = sizeSprites;
        return this;
    }

    public AndroidGameBuilder setSizeGeometries(int sizeGeometries) {
        this.sizeGeometries = sizeGeometries;
        return this;
    }

    /**
     * Build the AndroidGame
     */
    public AndroidGame build(){

        // Create a instance
        AndroidGame androidGame = new AndroidGame();

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

    private void setupDisplayInfo(AndroidGame androidGame) {
        androidGame.setDisplayInfo(new AndroidDisplayMetrics(context));
    }

    private void setupMonitorEngine(BaseGame androidGame) {
        androidGame.setMonitorEngineCreated(GameActivityTestTools.getMonitor());
    }

    private void setupTextureManager(BaseGame androidGame) {
        androidGame.setTextureManager(new AndroidTextureManager(
                context, new RunnerTask(), androidGame.getDisplayInfo()));
    }

    private void setupGLGameView(AndroidGame androidGame, boolean debug){
        GLGameView glGameView = mGLGameView!= null? mGLGameView: new GLGameView(context);
        androidGame.setGLGameView(glGameView);

        glGameView.setUpConfiguration();
        glGameView.setDebug(debug);
        glGameView.getHolder().setFixedSize(
                androidGame.getDisplayInfo().getFixWidth(),
                androidGame.getDisplayInfo().getFixHeight());
    }

    private void setupObservables(AndroidGame androidGame, GLGameView glGameView){
        androidGame.setObservableInput(new AndroidInput(glGameView));
        androidGame.setObservableLifeCycle(new AndroidLifeCycle());
        androidGame.setObservableRenderer(new AndroidRenderer(androidGame,glGameView));
    }

    private void setupController(BaseGame androidGame) {
        Puppeteer controller = new Puppeteer(
                new AndroidDrawToolsBuilder(context, sizeSprites, sizeGeometries));
        androidGame.connectObserver(controller);
    }

}
