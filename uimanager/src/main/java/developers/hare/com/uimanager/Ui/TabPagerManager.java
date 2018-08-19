package developers.hare.com.uimanager.Ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public class TabPagerManager {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<TabPagerItem> items;
    private TabPagerAdapter tabPagerAdapter;

    private static final int DEFAULT_GRAVITY = TabLayout.GRAVITY_FILL;


    public TabPagerManager(FragmentManager fragmentManager, TabLayout tabLayout, ViewPager viewPager, TabLayout.OnTabSelectedListener onTabSelectedListener, ViewPager.OnPageChangeListener onPageChangeListener) {
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        items = new ArrayList<>();
        tabPagerAdapter = new TabPagerAdapter(fragmentManager);
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        tabLayout.setTabGravity(DEFAULT_GRAVITY);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void setTabLayoutGravity(int gravity) {
        tabLayout.setTabGravity(gravity);
    }

    public void complete() {
        viewPager.setAdapter(tabPagerAdapter);
    }

    public TabPagerItem createItem(Fragment fragment, String title) {
        return new TabPagerItem(fragment, title);
    }

    public TabPagerItem createItem(Fragment fragment, String title, OnTabCLickListener onTabCLickListener) {
        return new TabPagerItem(fragment, title, onTabCLickListener);
    }

    public void addItem(TabPagerItem item) {
        items.add(item);
        tabLayout.addTab(tabLayout.newTab().setText(item.title));
    }

    private class TabPagerAdapter extends FragmentStatePagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            TabPagerItem item = items.get(position);
            item.onClick();
            return item.fragment;
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    @Data
    private class TabPagerItem {
        private Fragment fragment;
        private String title;
        private OnTabCLickListener onTabCLickListener;

        public TabPagerItem(Fragment fragment, String title, OnTabCLickListener onTabCLickListener) {
            this.fragment = fragment;
            this.title = title;
            this.onTabCLickListener = onTabCLickListener;
        }

        public TabPagerItem(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public void onClick(){
            if(onTabCLickListener != null)
                onTabCLickListener.onClick();
        }
    }

    public interface OnTabCLickListener{
        void onClick();
    }
}
