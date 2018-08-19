# UIManager

## @JavaDoc
http://jd.shared.kro.kr/

## @Description
I'm working on making the Android UI simple and easy to maintain.

Android UI 를 간편하고 유지보수에 용이하게 만들기 위해 개발 중 입니다


## @Setting
- Project:build.gradle
```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url  "https://dl.bintray.com/hare/UIManager"
        }
    }
}
```

- Module:build.gradle
```gradle
implementation 'developers.hare.com:uiamanger:1.0.0'
```

- Maven
```maven
<dependency>
  <groupId>developers.hare.com</groupId>
  <artifactId>uiamanger</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
## @Example
# ConstraintArrayView

@Activity
```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Get the type to place
         * 배치할 type 을 가져온다
         *
         * ConstraintArrayView.HORIZONTAL
         * ConstraintArrayView.LINKED_HORIZONTAL
         * ConstraintArrayView.INNER_LINKED_HORIZONTAL
         */
        int type = ConstraintArrayView.HORIZONTAL;
        /*
         * Obtains whether to reverse the placement direction
         * 배치 방향을 반전 시킬지 여부를 얻는다
         */
        boolean reverse = false;

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
```
@Holder
```java
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
```

@Main Layout
- activity_horizontal.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

    </data>

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".activity.HorizontalActivity">

        <TextView
            android:id="@+id/TV_text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Horizontal"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <developers.hare.com.uimanager.Ui.ConstraintArrayView
            android:id="@+id/arrayView"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_marginTop="15dp"
            app:color_text1="#113baf"
            app:color_text2="#000000"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/TV_text"/>

    </android.support.constraint.ConstraintLayout>
</layout>
```

@Item Layout
- holder_horizontal.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="item"
            type="developerse.hare.com.uimanager.UI.HorizontalHolder.HorizontalItem" />
    </data>

    <android.support.constraint.ConstraintLayout
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:padding="5dp"
        android:background="@drawable/bg">

        <TextView
            android:id="@+id/TV_text1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text='@{item.text1 ?? "text1"}'
            tools:text="text1" />

        <TextView
            android:id="@+id/TV_text2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="5dp"
            android:text='@{item.text2 ?? "text2"}'
            app:layout_constraintTop_toBottomOf="@+id/TV_text1"
            tools:text="text2" />
    </android.support.constraint.ConstraintLayout>
</layout>
```
