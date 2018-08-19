package example.hare.com.uimanagerexample.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import developers.hare.com.uimanager.Ui.ConstraintArrayView;
import example.hare.com.uimanagerexample.R;
import example.hare.com.uimanagerexample.UI.HorizontalHolder;
import example.hare.com.uimanagerexample.databinding.ActivityHorizontalBinding;

public class HorizontalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Get the type to place
         * 배치할 type 을 가져온다
         */
        int type = getIntent().getIntExtra("type", ConstraintArrayView.HORIZONTAL);
        /*
         * Obtains whether to reverse the placement direction
         * 배치 방향을 반전 시킬지 여부를 얻는다
         */
        boolean reverse = getIntent().getBooleanExtra("reverse", false);

        // Initialize using DataBinding
        ActivityHorizontalBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_horizontal);

        /*
         * Create a List<ArrayItem> to decorate ArrayItemHolder
         * ArrayItemHolder 를 꾸밀 List<ArrayItem> 를 만든다
         */
        List<HorizontalHolder.HorizontalItem> items = new ArrayList<>();
        items.add(new HorizontalHolder.HorizontalItem("Name", "Hare"));
        items.add(new HorizontalHolder.HorizontalItem("Age", "??"));
        items.add(new HorizontalHolder.HorizontalItem("Gender", "Mail"));
        items.add(new HorizontalHolder.HorizontalItem("Hobby", "Programming"));

        // Set type horizontal or vertical
        binding.arrayView.setType(type)
                // Set Listener to create ArrayItemHolder
                .setHolderCreateListener(
                        (ConstraintArrayView.HolderCreateListener<HorizontalHolder, HorizontalHolder.HorizontalItem>) (attrs, item)
                                -> new HorizontalHolder(this, attrs, item)
                )
                // Set reverse
                .setReverse(reverse)
                // Create View
                .createView(items);
    }
}
