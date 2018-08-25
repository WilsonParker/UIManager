package developers.hare.com.uimanager.Util;

import android.content.res.Resources;

/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-24
 **/
public class ResourceManager {
    private static final ResourceManager ourInstance = new ResourceManager();
    private Resources resources;

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public static ResourceManager getInstance() {
        return ourInstance;
    }

    public String getResourceString(int id) {
        return resources.getString(id);
    }
}
