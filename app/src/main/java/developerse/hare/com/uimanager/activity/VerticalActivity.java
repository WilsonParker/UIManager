package developerse.hare.com.uimanager.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import developers.hare.com.uimanager.Ui.ConstraintArrayView;
import developerse.hare.com.uimanager.R;
import developerse.hare.com.uimanager.UI.HorizontalHolder;
import developerse.hare.com.uimanager.UI.VerticalHolder;
import developerse.hare.com.uimanager.databinding.ActivityVerticalBinding;

public class VerticalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra("type", ConstraintArrayView.VERTICAL);
        boolean reverse = getIntent().getBooleanExtra("reverse", false);

        ActivityVerticalBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vertical);
        List<ConstraintArrayView.ArrayItem> items = new ArrayList<>();
        items.add(new VerticalHolder.VerticalItem("Name", "Hare"));
        items.add(new VerticalHolder.VerticalItem("Age", "??"));
        items.add(new VerticalHolder.VerticalItem("Gender", "Mail"));
        items.add(new VerticalHolder.VerticalItem("Hobby", "Programming"));
        binding.arrayView.setType(type).setHolderCreateListener((ConstraintArrayView.HolderCreateListener<VerticalHolder, VerticalHolder.VerticalItem>) (attrs, item) -> new VerticalHolder(this, attrs, item)).setReverse(reverse).createView(items);
    }

}