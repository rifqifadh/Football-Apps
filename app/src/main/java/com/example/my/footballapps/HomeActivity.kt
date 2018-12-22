package com.example.my.footballapps

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.my.footballapps.R.id.*
import com.example.my.footballapps.view.Favorite.FavoriteFragment
import com.example.my.footballapps.view.matches.ParentMatchFragment
import com.example.my.footballapps.view.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                matches -> {
                    loadMatchFragment(savedInstanceState)
                }
                teams -> {
                    loadTeamFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoriteFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = matches
    }

    private fun loadMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, ParentMatchFragment(), ParentMatchFragment::class.java.simpleName)
                .commit()
        }
    }
    private fun loadTeamFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoriteFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                .commit()
        }
    }

}
