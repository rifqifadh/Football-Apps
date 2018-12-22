package com.example.my.footballapps.view.matches.search

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.invisible
import com.example.my.footballapps.Utils.visible
import com.example.my.footballapps.adapter.TeamAdapter
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.presenter.SearchTeamPresenter
import com.example.my.footballapps.view.teams.detail.DetailTeamActivity
import com.google.gson.Gson
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class TeamSearch: AppCompatActivity(), SearchTeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: SearchTeamPresenter
    private lateinit var listAdapter: TeamAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_search)

        recyclerView = find(R.id.rv_search_team)

        progressBar = find(R.id.progress_bar_search)
        swipeRefresh = find(R.id.swipe_refresh_search)

        supportActionBar?.title = "Search Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val apiRepository = ApiRepository()
        val gson = Gson()

        presenter = SearchTeamPresenter(this, apiRepository, gson)

        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = TeamAdapter(teams){
            this.startActivity<DetailTeamActivity>("id_team" to it.teamId, "team_name" to it.teamName,
                "team_desc" to it.teamDescription)
        }
        recyclerView.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)
        val searchMenu = menu.findItem(R.id.action_search)
        searchMenu.expandActionView()
        searchView = searchMenu.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getSearchTeam(query.toString().replace(" ", "%20"))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.getSearchTeam(newText.toString().replace(" ", "%20"))
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showSearchTeam(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        listAdapter.notifyDataSetChanged()
    }
}