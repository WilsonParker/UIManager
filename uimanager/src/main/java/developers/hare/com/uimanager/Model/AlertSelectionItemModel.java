package developers.hare.com.uimanager.Model;

import android.view.View;

import lombok.Data;

/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-25
 **/
@Data
public class AlertSelectionItemModel {
    private String text;
    private int imageId;
    private View.OnClickListener onClickListener;

    public AlertSelectionItemModel(String text, int imageId, View.OnClickListener onClickListener) {
        this.text = text;
        this.imageId = imageId;
        this.onClickListener = onClickListener;
    }
}
