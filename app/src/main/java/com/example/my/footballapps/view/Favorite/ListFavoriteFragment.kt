package com.example.my.footballapps.view.Favorite

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my.footballapps.R
import com.example.my.footballapps.adapter.FavoriteMatchAdapter
import com.example.my.footballapps.adapter.FavoriteTeamAdapter
import com.example.my.footballapps.db.FavoriteMatch
import com.example.my.footballapps.db.FavoriteTeam
import com.example.my.footballapps.db.database
import com.example.my.footballapps.db.databaseTeam
import com.example.my.footballapps.view.matches.detail.MatchDetailActivity
import com.example.my.footballapps.view.teams.detail.DetailTeamActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class ListFavoriteFragment:  Fragment(), AnkoComponent<Context> {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listFav: RecyclerView

    private lateinit var favoriteMatchAdapter: FavoriteMatchAdapter
    private lateinit var favoriteTeamAdapter: FavoriteTeamAdapter

    private var favoritesMatches: MutableList<FavoriteMatch> = mutableListOf()
    private var favoritesTeams: MutableList<FavoriteTeam> = mutableListOf()

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): ListFavoriteFragment {
            val fragment = ListFavoriteFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments?.getInt(ListFavoriteFragment.ARG_SECTION_NUMBER) == 0) {
            favoriteMatchAdapter = FavoriteMatchAdapter(favoritesMatches) {
                context?.startActivity<MatchDetailActivity>(
                    "id_home" to it.homeId, "id_away" to it.awayId,
                    "id_event" to it.eventId
                )
            }
            listFav.adapter = favoriteMatchAdapter
            showFavoriteMatch()
            swipeRefresh.setOnRefreshListener {
                favoritesMatches.clear()
                showFavoriteMatch()
            }
        }else{
            favoriteTeamAdapter = FavoriteTeamAdapter(favoritesTeams) {
                context?.startActivity<DetailTeamActivity>("id_team" to it.teamId, "team_name" to it.teamName,
                    "team_desc" to it.teamDescription)
            }
            listFav.adapter = favoriteTeamAdapter
            showFavoriteTeam()
            swipeRefresh.setOnRefreshListener {
                favoritesTeams.clear()
                showFavoriteTeam()
            }
        }
    }

    private fun showFavoriteMatch() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favoriteMatches = result.parseList(classParser<FavoriteMatch>())
            favoritesMatches.addAll(favoriteMatches)
            favoriteMatchAdapter.notifyDataSetChanged()
        }
    }

    private fun showFavoriteTeam(){
        context?.databaseTeam?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favoriteTeams = result.parseList(classParser<FavoriteTeam>())
            favoritesTeams.addAll(favoriteTeams)
            favoriteTeamAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(this.requireContext()))
    }

    @SuppressLint("ResourceType")
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {

        linearLayout {
            lparams(matchParent, wrapContent)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                listFav = recyclerView {
                    id = R.id.listFav
                    tag = if (arguments?.getInt(ListFavoriteFragment.ARG_SECTION_NUMBER) == 0) {
                        "fav_match"
                    } else {
                        "fav_team"
                    }
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}