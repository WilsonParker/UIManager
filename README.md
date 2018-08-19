# UIManager

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

Activitvy
'''java
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
'''
