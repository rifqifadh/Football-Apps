package com.example.my.footballapps.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.my.footballapps.view.matches.LastMatchFragment
import com.example.my.footballapps.view.matches.NextMatchFragment

class MatchPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {



    override fun getItem(position: Int): Fragment? = when(position){
        0 -> LastMatchFragment()
        1 -> NextMatchFragment()
        else -> null
    }

    override fun getCount(): Int {
        return 2
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Last Match"
            1 -> "Next Match"
            else -> null
        }
    }

}