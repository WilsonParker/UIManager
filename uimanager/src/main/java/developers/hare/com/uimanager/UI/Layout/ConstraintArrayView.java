package developers.hare.com.uimanager.UI.Layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.View;

import java.util.Iterator;
import java.util.List;

import developers.hare.com.uimanager.VO.AttributeTable;

/**
 * description :
 * Deploy view using properties of ConstraintLayout
 * ConstraintLayout 의 특성을 이용해서 View 를 배치한다
 *
 * Horizontal : |ㅁㅁㅁㅁㅁ  |
 * InnerHorizontal : |ㅁ  ㅁ ㅁ ㅁ  ㅁ|
 * LinkedHorizontal : | ㅁ ㅁ ㅁ ㅁ ㅁ |
 * vertical changed only upward
 * vertical 은 위에서 방향만 바뀐다
 *
 * If the Reverse value is set to true, the orientation is reversed;
 * Reverse 값을 true 로 설정할 경우 배치되는 방향이 반대가 된다
 *
 * @author Hare
 * @since 2018-08-19
 **/
public class ConstraintArrayView extends ConstraintLayout {
    private static final int LINKED = 0x00001000, INNER = 0x00000100;
    public static final int HORIZONTAL = 0x00000001,
                            LINKED_HORIZONTAL = LINKED | HORIZONTAL,
                            INNER_LINKED_HORIZONTAL = INNER | HORIZONTAL,
                            VERTICAL = 0x00000010,
                            LINKED_VERTICAL = LINKED | VERTICAL,
                            INNER_LINKED_VERTICAL = INNER | VERTICAL;


    private AttributeTable table;
    private HolderCreateListener holderCreateListener;
    private int type = HORIZONTAL,
                beforeId = ConstraintSet.PARENT_ID,
                myId = -1,
                nextId,
                constraintWidth,
                constraintHeight,
                bt,
                tb,
                lr,
                rl;

    {
        initOrientation();
        setReverse(false);
    }

    public ConstraintArrayView(Context context) {
        super(context);
    }

    public ConstraintArrayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        table = new AttributeTable();
        table.parseData(attrs);
    }

    /**
     * description :
     * Create and arrange ArrayItemHolder using List$lt;? extends ArrayItem$gt;
     * List$lt;? extends ArrayItem$gt; 를 이용하여 ArrayItemHolder 를 만들어 배치한다
     *
     * @param items
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createView(List<? extends ArrayItem> items) {
        ConstraintSet set = new ConstraintSet();
        set.clone(set);
        if ((type & (LINKED | INNER)) > 0)
            createLinkedView(items, set);
        else
            createBasicView(items, set);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createBasicView(List<? extends ArrayItem>items, ConstraintSet set) {
        for (Iterator<? extends ArrayItem> iterator = items.iterator(); iterator.hasNext(); ) {
            myId = View.generateViewId();
            initHolder(holderCreateListener.createHolder(table, iterator.next()), myId);
            if ((type & HORIZONTAL) > 0)
                horizontalConnect(set);
            else
                verticalConnect(set);
            afterConnect(set);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createLinkedView(List<? extends ArrayItem> items, ConstraintSet set) {
        for (int i = 0; i < items.size(); i++) {
            if (myId == -1) {
                myId = View.generateViewId();
                initHolder(holderCreateListener.createHolder(table, items.get(i)), myId);
            } else {
                myId = nextId;
            }
            if (i + 1 < items.size()) {
                nextId = View.generateViewId();
                initHolder(holderCreateListener.createHolder(table, items.get(i + 1)), nextId);
            } else {
                nextId = ConstraintSet.PARENT_ID;
            }

            if ((type & HORIZONTAL) > 0)
                horizontalLinkedConnect(set);
            else
                verticalLinkedConnect(set);
            afterConnect(set);
        }
    }

    private void initHolder(ArrayItemHolder holder, int id) {
        holder.setId(id);
        addView(holder);
    }

    private void afterConnect(ConstraintSet set) {
        beforeId = myId;
        set.applyTo(this);
    }

    private void horizontalInit(ConstraintSet set) {
        set.connect(myId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        set.connect(myId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        set.constrainWidth(myId, constraintWidth);
        set.constrainHeight(myId, constraintHeight);
    }

    private void horizontalConnect(ConstraintSet set) {
        horizontalInit(set);
        if (beforeId == ConstraintSet.PARENT_ID)
            set.connect(myId, rl, beforeId, rl, 0);
        else
            set.connect(myId, rl, beforeId, lr, 0);
    }

    private void horizontalLinkedConnect(ConstraintSet set) {
        horizontalInit(set);
        if (beforeId == ConstraintSet.PARENT_ID) {
            if ((type & LINKED) > 0)
                set.connect(myId, lr, nextId, rl, 0);
            set.connect(myId, rl, beforeId, rl, 0);
        } else if (nextId == ConstraintSet.PARENT_ID) {
            if ((type & LINKED) > 0)
                set.connect(myId, rl, beforeId, lr, 0);
            set.connect(myId, lr, nextId, lr, 0);
        } else {
            set.connect(myId, rl, beforeId, lr, 0);
            set.connect(myId, lr, nextId, rl, 0);
        }
    }

    private void verticalInit(ConstraintSet set) {
        set.connect(myId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        set.connect(myId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        set.constrainWidth(myId, constraintWidth);
        set.constrainHeight(myId, constraintHeight);
    }

    private void verticalConnect(ConstraintSet set) {
        verticalInit(set);
        if (beforeId == ConstraintSet.PARENT_ID)
            set.connect(myId, bt, beforeId, bt, 0);
        else
            set.connect(myId, bt, beforeId, tb, 0);
    }

    private void verticalLinkedConnect(ConstraintSet set) {
        verticalInit(set);
        if (beforeId == ConstraintSet.PARENT_ID){
            if ((type & LINKED) > 0)
                set.connect(myId, tb, nextId, bt, 0);
            set.connect(myId, bt, beforeId, bt, 0);
        } else if (nextId == ConstraintSet.PARENT_ID){
            if ((type & LINKED) > 0)
                set.connect(myId, bt, beforeId, tb, 0);
            set.connect(myId, tb, nextId, tb, 0);
        } else {
            set.connect(myId, bt, beforeId, tb, 0);
            set.connect(myId, tb, nextId, bt, 0);
        }
    }

    private void initOrientation() {
        boolean b = (type & HORIZONTAL) > 0;
        constraintWidth = b ? ConstraintSet.WRAP_CONTENT : ConstraintSet.MATCH_CONSTRAINT;
        constraintHeight = b ? ConstraintSet.MATCH_CONSTRAINT : ConstraintSet.WRAP_CONTENT;
    }

    /**
     * description :
     * Determines the type in which to place the ArrayItemHolder
     * ArrayItemHolder 를 배치할 타입을 정한다
     *
     * @param type
     * @return
     */
    public ConstraintArrayView setType(int type) {
        this.type = type;
        initOrientation();
        return this;
    }

    /**
     * description :
     * Orientation to place ArrayItemHolder
     * ArrayItemHolder 를 배치할 방향 정한다
     *
     * Type : Horizontal
     * Basic :
     * Left to Right
     * 왼쪽에서 오른쪽으로
     * Reverse :
     * Right to Left
     * 오른쪽에서 왼쪽으로
     *
     * Type : Vertical
     * Basic :
     * Top to Bottom
     * 위에서 아래로
     * Reverse :
     * Bottom to Top
     * 아래에서 위로
     *
     * @param reverse
     * @return
     */
    public ConstraintArrayView setReverse(boolean reverse) {
        bt = reverse ? ConstraintSet.BOTTOM : ConstraintSet.TOP;
        tb = reverse ? ConstraintSet.TOP : ConstraintSet.BOTTOM;
        lr = reverse ? ConstraintSet.LEFT : ConstraintSet.RIGHT;
        rl = reverse ? ConstraintSet.RIGHT : ConstraintSet.LEFT;
        return this;
    }

    public ConstraintArrayView setConstraintHeight(int constraintHeight) {
        this.constraintHeight = constraintHeight;
        return this;
    }

    public ConstraintArrayView setConstraintWidth(int constraintWidth) {
        this.constraintWidth = constraintWidth;
        return this;
    }

    public ConstraintArrayView setHolderCreateListener(HolderCreateListener holderCreateListener) {
        this.holderCreateListener = holderCreateListener;
        return this;
    }

    public interface HolderCreateListener<H extends ArrayItemHolder, I extends ArrayItem> {
        /**
         * description :
         * Create ArrayItemHolder using ArrayItem
         * H and I can be defined by implementing this interface
         *
         * ArrayItem 을 이용하여 ArrayItemHolder 를 생성해준다
         * H와 i는 인터페이스를 구현하면서 정의해줄 수 있다
         *
         * example : 
         * lambda : (ConstraintArrayView.HolderCreateListener$lt;HorizontalHolder, HorizontalHolder.HorizontalItem$gt;) (item) -> new HorizontalHolder(this, item)
         *
         * Need a parameter that inherits ArrayItem
         * ArrayItem 을 상속한 파라미터가 필요하다
         *
         * @param table
         * @param i
         *
         * Return object that inherits ArrayItemHolder
         * ArrayItemHolder 를 상속한 객체를 반환합니다
         * @return H
         */
        H createHolder(AttributeTable table,I i);
    }

    /**
     * description :
     * A class with data for decorating ArrayItemHolder
     * ArrayItemHolder 를 꾸미기 위해 필요한 데이터를 가지고 있는 클래스
     */
    public interface ArrayItem {
    }

    public static abstract class ArrayItemHolder<I extends ArrayItem> extends ConstraintLayout {

        public ArrayItemHolder(Context context, I item) {
            super(context);
            init(context);
            applyItem(item);
        }

        public ArrayItemHolder(Context context, AttributeTable table, I item) {
            super(context);
            init(context);
            applyItem(item);
            setAttribute(table);
        }

        /**
         * description : 
         * Returns the styleable id value to apply
         * 적용할 styleable id 값을 반환한다
         *
         * example : 
         * return R.styleable.TextViewAttrs;
         *
         * res/values/attrs.xml
         * &lt;declare-styleable name="TextViewAttrs"&gt;
         *      &lt;attr name="text_color" format="reference|integer"/&gt;
         * &lt;/declare-styleable&gt;
         *
         * @return
         */
        @Deprecated
        protected int[] getStyleResId(){
            return new int[0];
        }

        /**
         * description :
         * Decorate view with TypedArray
         * TypedArray 를 이용해 View 를 꾸며준다
         *
         * example : 
         * int textColor = typedArray.getColor(R.styleable.TextViewAttrs_text_color, 0);
         * textView.setTextColor(textColor);
         *
         * @param typedArray
         */
        @Deprecated
        protected void setTypedArray(TypedArray typedArray){
            // Nothing to do
        }

        /**
         * description :
         * Assign data to View using AttributeTable
         * AttributeTable 을 이용하여 View 에 데이터를 대입한다
         *
         * @param table
         */
        protected abstract void setAttribute(AttributeTable table);

        /**
         * description :
         * Initialize Holder
         * Holder 를 초기화 한다
         *
         * @param context
         */
        public abstract void init(Context context);

        /**
         * description :
         * Assign data to View using ArrayItem
         * ArrayItem 을 이용하여 View 에 데이터를 대입한다
         *
         * @param item
         */
        public abstract void applyItem(I item);

        /**
         * Convert the AttributeSet to TypedArray and then call the setTypedArray method
         * AttributeSet 을 TypedArray 로 변환 한후 setTypedArray 메소드를 호출한다
         *
         * @param attrs
         */
        @Deprecated
        protected void initAttrs(AttributeSet attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, getStyleResId());
            setTypedArray(typedArray);
        }

    }

}
