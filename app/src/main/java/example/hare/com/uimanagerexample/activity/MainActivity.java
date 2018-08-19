package example.hare.com.uimanagerexample.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import developers.hare.com.uimanager.Ui.ConstraintArrayView;
import example.hare.com.uimanagerexample.R;
import example.hare.com.uimanagerexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Intent horizontal = new Intent(MainActivity.this, HorizontalActivity.class);
        Intent vertical = new Intent(MainActivity.this, VerticalActivity.class);

        binding.BTHorizontal.setOnClickListener((v) -> {
            horizontal.putExtra("type", ConstraintArrayView.HORIZONTAL);
            horizontal.putExtra("reverse", binding.CBReverse.isChecked());
            startActivity(horizontal);
        });
        binding.BTInnerHorizontal.setOnClickListener((v) -> {
            horizontal.putExtra("type", ConstraintArrayView.INNER_LINKED_HORIZONTAL);
            horizontal.putExtra("reverse", binding.CBReverse.isChecked());
            startActivity(horizontal);
        });
        binding.BTLinkedHorizontal.setOnClickListener((v) -> {
            horizontal.putExtra("type", ConstraintArrayView.LINKED_HORIZONTAL);
            horizontal.putExtra("reverse", binding.CBReverse.isChecked());
            startActivity(horizontal);
        });

        binding.BTVertical.setOnClickListener((v) -> {
            vertical.putExtra("type", ConstraintArrayView.VERTICAL);
            vertical.putExtra("reverse", binding.CBReverse.isChecked());
            startActivity(vertical);
        });
        binding.BTInnerVertical.setOnClickListener((v) -> {
            vertical.putExtra("type", ConstraintArrayView.INNER_LINKED_VERTICAL);
            vertical.putExtra("reverse", binding.CBReverse.isChecked());
            startActivity(vertical);
        });
        binding.BTLinkedVertical.setOnClickListener((v) -> {
            vertical.putExtra("type", ConstraintArrayView.LINKED_VERTICAL);
            vertical.putExtra("reverse", binding.CBReverse.isChecked());
            startActivity(vertical);
        });
    }
}
