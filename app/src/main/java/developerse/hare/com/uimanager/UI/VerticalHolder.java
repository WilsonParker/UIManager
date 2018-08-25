package developerse.hare.com.uimanager.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;

import developers.hare.com.uimanager.UI.Layout.ConstraintArrayView;
import developers.hare.com.uimanager.VO.AttributeTable;
import developerse.hare.com.uimanager.databinding.HolderVerticalBinding;
import lombok.Data;

public class VerticalHolder extends ConstraintArrayView.ArrayItemHolder<VerticalHolder.VerticalItem> {
    private HolderVerticalBinding binding;

    /*
    private View view;
    private TextView TV_text1, TV_text2;
    */

    public VerticalHolder(Context context, AttributeTable table, VerticalItem item) {
        super(context, table, item);
    }

    @Override
    public void init(Context context) {
        // Using DataBinding
        binding = HolderVerticalBinding.inflate(LayoutInflater.from(context));
        addView(binding.getRoot());

        /*
        view = LayoutInflater.from(context).inflate(R.layout.holder_horizontal, this, false);
        addView(view);
        */
    }

    @Override
    public void applyItem(VerticalItem item) {
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
         * key 를 이용하여 item 을 가져왔을 때 item 이 null 이 아닐 경우 onBind 를 실행
         */
        table.onBind("color_text1", (item) -> binding.TVText1.setTextColor(item.getIntValue()));
        table.onBind("color_text2", (item) -> binding.TVText2.setTextColor(Color.parseColor(item.getStringValue())));
    }

    @Data
    public static class VerticalItem implements ConstraintArrayView.ArrayItem {
        private String text1, text2;

        public VerticalItem(String text1, String text2) {
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
