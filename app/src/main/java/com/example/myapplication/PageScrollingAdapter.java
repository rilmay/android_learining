package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageScrollingAdapter extends FragmentStateAdapter {
    public PageScrollingAdapter(MainActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return(PageFragment.newInstance(position));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
