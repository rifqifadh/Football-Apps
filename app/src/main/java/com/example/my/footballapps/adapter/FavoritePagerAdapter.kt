package com.example.my.footballapps.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.my.footballapps.view.Favorite.ListFavoriteFragment

class FavoritePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ListFavoriteFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 2
    }
}
