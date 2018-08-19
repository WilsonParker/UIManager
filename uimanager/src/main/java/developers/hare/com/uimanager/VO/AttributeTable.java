package developers.hare.com.uimanager.VO;

import android.util.AttributeSet;

import java.util.Hashtable;

import lombok.Data;

@Data
public class AttributeTable {
    private Hashtable<String, AttributeItem> table;

    public void parseData(AttributeSet attrs) {
        table = new Hashtable<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            AttributeItem item = new AttributeItem();
            item.booleanValue = attrs.getAttributeBooleanValue(i, false);
            item.intValue = attrs.getAttributeIntValue(i, 0);
            item.resourceValue = attrs.getAttributeResourceValue(i, 0);
            item.stringValue = attrs.getAttributeValue(i);
            table.put(attrs.getAttributeName(i), item);
        }
    }

    public AttributeItem get(String key) {
        return table.get(key);
    }

    public void onBind(String key, AttributeBindListener listener){
        AttributeItem item = get(key);
        if(item != null)
            listener.onBind(item);
    }

    public interface AttributeBindListener {
        void onBind(AttributeItem item);
    }

    @Data
    public class AttributeItem {
        private boolean booleanValue;
        private int intValue,
                resourceValue;
        private String id = "",
                stringValue = "";
    }
}
