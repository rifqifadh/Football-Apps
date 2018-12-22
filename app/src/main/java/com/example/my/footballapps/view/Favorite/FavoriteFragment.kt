package com.example.my.footballapps.view.Favorite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my.footballapps.R
import com.example.my.footballapps.adapter.FavoritePagerAdapter
import org.jetbrains.anko.find

class FavoriteFragment: Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    private lateinit var adapter: FavoritePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)

        viewPager = rootView.find(R.id.view_pager_favorite)
        tabs = rootView.find(R.id.tabs_favorite)

        adapter = FavoritePagerAdapter(childFragmentManager)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))

        return rootView
    }
}