package com.example.my.footballapps.view.teams.detail.player

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.invisible
import com.example.my.footballapps.Utils.visible
import com.example.my.footballapps.adapter.PlayerAdapter
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.model.Player
import com.example.my.footballapps.presenter.PlayerPresenter
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayersFragment: Fragment(), AnkoComponent<Context>, PlayerView {

    companion object {
        private const val ARG_TEAM_NAME = "team_name"

        fun newInstance(teamName: String?): PlayersFragment {
            val fragment = PlayersFragment()
            val args = Bundle()
            args.putString(ARG_TEAM_NAME, teamName)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var listPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var presenter: PlayerPresenter
    private lateinit var teamName: String

    private var players: MutableList<Player> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        teamName = arguments?.getString(PlayersFragment.ARG_TEAM_NAME).toString().replace(" ","_")

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)

        playerAdapter = PlayerAdapter(players) {
            context?.startActivity<PlayerDetails>("id_player" to it.playerId)
        }
        listPlayer.adapter = playerAdapter

        presenter.getPlayerTeam(teamName)

        swipeRefresh.setOnRefreshListener {
            presenter.getPlayerTeam(teamName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerlist(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        playerAdapter.notifyDataSetChanged()
    }

    override fun createView(ui: AnkoContext<Context>)= with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
                        id = R.id.list_player
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }
}