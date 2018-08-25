package developers.hare.com.uimanager.Util;


/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-24
 **/
public class ScrollEndMethod {
    public static ScrollEndMethod scrollEndMethod = new ScrollEndMethod();

    public static ScrollEndMethod getInstance() {
        return scrollEndMethod;
    }

    public void actionAfterScrollEnd(int size, int position, OnListScrollListener onListScrollListener) {
        if (size - 1 == position)
            onListScrollListener.scrollEnd();
    }

    public interface OnListScrollListener {
        void scrollEnd();
    }
}
