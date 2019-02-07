package com.titicolab.mock.rule;

import com.titicolab.nanux.core.GraphicContext;
import com.titicolab.nanux.graphics.draw.DrawTools;
import com.titicolab.nanux.util.GPUInfo;

public interface ObserverGraphicContext {
    interface ContextCreated{
        void onGraphicContextCreated(GraphicContext game);
    }

    interface SurfaceCreated{
         void onSurfaceCreated(GraphicContext game, GPUInfo eglConfig);
    }

    interface SurfaceChanged {
        void onSurfaceChanged(int width, int height);
    }

    interface DrawFrame{
        void onDrawFrame(DrawTools drawTools);
    }
}
