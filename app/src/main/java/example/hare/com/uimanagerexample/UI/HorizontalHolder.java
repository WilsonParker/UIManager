package example.hare.com.uimanagerexample.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;

import developers.hare.com.uimanager.Ui.ConstraintArrayView;
import developers.hare.com.uimanager.VO.AttributeTable;
import example.hare.com.uimanagerexample.databinding.HolderHorizontalBinding;
import lombok.Data;

/**
 * @description
 * It is the information of ArrayItemHolder to enter inside ConstraintArrayView
 * ConstraintArrayView 안에 들어갈 item 의 정보이다
 *
 * @author Hare
 * @since 2018-08-19
 **/
public class HorizontalHolder extends ConstraintArrayView.ArrayItemHolder<HorizontalHolder.HorizontalItem> {
    private HolderHorizontalBinding binding;

    /*
    private View view;
    private TextView TV_text1, TV_text2;
    */

    public HorizontalHolder(Context context, AttributeTable table, HorizontalItem item) {
        super(context, table, item);
    }

    @Override
    public void init(Context context) {
        // Using DataBinding
        binding = HolderHorizontalBinding.inflate(LayoutInflater.from(context));
        addView(binding.getRoot());

        /*
        view = LayoutInflater.from(context).inflate(R.layout.holder_horizontal, this, false);
        addView(view);
        */
    }

    @Override
    public void applyItem(HorizontalItem item) {
        // Using DataBinding
        binding.setItem(item);

        /*
        TV_text1 = view.findViewById(R.id.TV_text1);
        TV_text2 = view.findViewById(R.id.TV_text2);
        */
    }

    @Override
    protected void setAttribute(AttributeTable table) {
        // int textColor1 = table.get("color_text1").getIntValue();
        // String textColor2 = table.get("color_text2").getStringValue();

        // Using DataBinding
        // binding.TVText1.setTextColor(textColor1);
        // binding.TVText2.setTextColor(Color.parseColor(textColor2));

        /*
        TV_text1.setTextColor(textColor1);
        TV_text2.setTextColor(Color.parseColor(textColor2));
        */

        /*
         * When you get the AttributeItem using key, if AttributeItem is not null, execute onBind method
         * key 를 이용하여 item 을 얻었을 때 item 이 null 이 아닐 경우 onBind 를 실행
         */
        table.onBind("color_text1", (item) -> binding.TVText1.setTextColor(item.getIntValue()));
        table.onBind("color_text2", (item) -> binding.TVText2.setTextColor(Color.parseColor(item.getStringValue())));
    }

    @Data
    public static class HorizontalItem implements ConstraintArrayView.ArrayItem {
        private String text1, text2;

        public HorizontalItem(String text1, String text2) {
            this.text1 = text1;
            this.text2 = text2;
        }

       /*
        * No need to write using Lombok
        * 롬복을 사용하여 작성할 필요가 없습니다
        */
       /*
        public String getText1() {
            return text1;
        }

        public void setText1(String text1) {
            this.text1 = text1;
        }

        public String getText2() {
            return text2;
        }

        public void setText2(String text2) {
            this.text2 = text2;
        }
       */
    }
}
