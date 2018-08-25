package developers.hare.com.uimanager.Util;

import android.os.Handler;

/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-24
 **/
public class HandlerManager {
    private static final HandlerManager ourInstance = new HandlerManager();
    private static final Handler handler = new Handler();

    public static HandlerManager getInstance() {
        return ourInstance;
    }

    public Handler getHandler() {
        return handler;
    }

    public void post(Runnable runnable){
        handler.post(runnable);
    }
}
