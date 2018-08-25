package developers.hare.com.uimanager.Util;

import android.util.Log;

import developers.hare.com.uimanager.Model.Response.ResponseArrayModel;
import developers.hare.com.uimanager.Model.Response.ResponseModel;
import retrofit2.Response;


/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-24
 **/
public class LogManager {

    private static final String TAG = "TAG";

    private static final String NOTI = "############################################################";
    private static final boolean isDebug = true;
    private static final boolean onError = true;

    public static final int LOG_INFO = 0x0001;
    public static final int LOG_DEBUG = 0x0010;
    public static final int LOG_ERROR = 0x0011;
    public static final int LOG_WARN = 0x0100;
    public static final int LOG_VERBOSE = 0x0101;

    public static void log(int type, String msg) {
        String m = setNotiMsg(msg);
        print(type, m);
    }

    private static void print(int type, String msg) {
        if (isDebug) {
            switch (type) {
                case LOG_DEBUG:
                    d(msg);
                    break;
                case LOG_ERROR:
                    e(msg);
                    break;
                case LOG_WARN:
                    w(msg);
                    break;
                case LOG_VERBOSE:
                    v(msg);
                    break;
                case LOG_INFO:
                default:
                    i(msg);
                    break;
            }
        }
    }

    private static void i(String msg) {
        Log.i(TAG, msg + "");
    }

    private static void d(String msg) {
        Log.d(TAG, msg + "");
    }

    private static void e(String msg) {
        Log.e(TAG, msg + "");
    }

    private static void w(String msg) {
        Log.w(TAG, msg + "");
    }

    private static void v(String msg) {
        Log.v(TAG, msg + "");
    }

    public static void log(int type, Class cls, String msg) {
        String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Message : " + msg);
        print(type, m);
    }

    public static void log(int type, Class cls, String mName, String msg) {
        String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Method : " + mName + "\n#Message : " + msg);
        print(type, m);
    }


    public static void log(Exception e) {
        if (onError) {
            String m = setNotiMsg(e.getMessage());
            Log.e(TAG, "#Error : " + m);
            e.printStackTrace();
        }
    }

    public static void log(String msg, Exception e) {
        if (onError) {
            String m = setNotiMsg(msg + " : " + e.getMessage());
            Log.e(TAG, "#Error : " + m);
            e.printStackTrace();
        }
    }

    public static void log(String cName, String mName, Exception e) {
        if (onError) {
            String m = setNotiMsg("#Class : " + cName + "\n#Method : " + mName + " \n#Message : " + e.getMessage());
            Log.e(TAG, "#Error : " + m);
            e.printStackTrace();
        }
    }

    public static void log(Class cls, String mName, Exception e) {
        if (onError) {
            String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Method : " + mName + "\n#Message : " + e.getMessage());
            Log.e(TAG, "#Error : " + m);
            e.printStackTrace();
        }
    }

    public static void log(Class cls, String mName, String msg, Exception e) {
        if (onError) {
            String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Method : " + mName + "\n#Message : " + msg + " - " + e.getMessage());
            Log.e(TAG, "#Error : " + m);
            e.printStackTrace();
        }
    }

    public static void log(Throwable t) {
        if (onError) {
            String m = setNotiMsg(t.getMessage());
            Log.e(TAG, "#Error : " + m);
            t.printStackTrace();
        }
    }

    public static void log(String msg, Throwable t) {
        if (onError) {
            String m = setNotiMsg(msg + " : " + t.getMessage());
            Log.e(TAG, "#Error : " + m);
            t.printStackTrace();
        }
    }

    public static void log(String cName, String mName, Throwable t) {
        if (onError) {
            String m = setNotiMsg("#Class : " + cName + "\n#Method : " + mName + " \n#Message : " + t.getMessage());
            Log.e(TAG, "#Error : " + m);
            t.printStackTrace();
        }
    }

    public static void log(Class cls, String mName, Throwable t) {
        if (onError) {
            String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Method : " + mName + "\n#Message : " + t.getMessage());
            Log.e(TAG, "#Error : " + m);
            t.printStackTrace();
        }
    }

    public static void log(Class cls, String mName, String msg, Throwable t) {
        if (onError) {
            String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Method : " + mName + "\n#Message : " + msg + " - " + t.getMessage());
            Log.e(TAG, "#Error : " + m);
            t.printStackTrace();
        }
    }

    private static String setNotiMsg(String msg) {
        return NOTI + "\n" + msg + "\n" + NOTI;
    }

    public static <T> void log(Class<?> cls, String mName, Response<ResponseModel<T>> response) {
        String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Method : " + mName + "" +
                "\n#Message : " + response.body().getSuccess()
                + "\n#Message : " + response.body().getMessage()
                + "\n#Message : " + response.body().getResult()
        );
        print(LOG_INFO, m);
    }

    public static <T> void logA(Class<?> cls, String mName, Response<ResponseArrayModel<T>> response) {
        String m = setNotiMsg("#Class : " + cls.getCanonicalName() + "\n#Method : " + mName + "" +
                "\n#Message : " + response.body().getSuccess()
                + "\n#Message : " + response.body().getMessage());
        if (response.body().getResult() != null) {
            for (T t : response.body().getResult())
                m += "\n#Message : " + t.toString();
        }
        print(LOG_INFO, m);
    }

}
