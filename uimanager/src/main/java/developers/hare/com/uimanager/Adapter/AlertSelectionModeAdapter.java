package developers.hare.com.uimanager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import developers.hare.com.uimanager.Model.AlertSelectionItemModel;
import developers.hare.com.uimanager.R;
import developers.hare.com.uimanager.UI.UIFactory;
import developers.hare.com.uimanager.Util.Image.ImageManager;

/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-25
 **/
public class AlertSelectionModeAdapter extends RecyclerView.Adapter<AlertSelectionModeAdapter.ViewHolder> {
    private ArrayList<AlertSelectionItemModel> items;

    public AlertSelectionModeAdapter(ArrayList<AlertSelectionItemModel> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alert_default_selection_mode, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.toBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView IV_icon;
        private TextView TV_text;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
        }

        public void toBind(AlertSelectionItemModel model) {
            ImageManager.getInstance().loadImage(context, model.getImageId(), IV_icon, ImageManager.BASIC_TYPE);
            IV_icon.setOnClickListener(model.getOnClickListener());
            TV_text.setText(model.getText());
        }
    }
}
