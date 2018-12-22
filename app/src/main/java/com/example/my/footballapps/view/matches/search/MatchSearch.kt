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
import com.example.my.footballapps.adapter.SearchMatchAdapter
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.model.MatchEvent
import com.example.my.footballapps.presenter.SearchEventPresenter
import com.example.my.footballapps.view.matches.detail.MatchDetailActivity
import com.google.gson.Gson
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MatchSearch : AppCompatActivity(), SearchMatchView {

    private var event: MutableList<MatchEvent> = mutableListOf()
    private lateinit var presenter: SearchEventPresenter
    private lateinit var listAdapter: SearchMatchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swiperefresh: SwipeRefreshLayout
    private lateinit var searchView: SearchView

    //private var query: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)

        //toolbar = find(R.id.toolbar_search)
        recyclerView = find(R.id.rv_search)


        progressBar = find(R.id.progress_bar_search)
        swiperefresh = find(R.id.swipe_refresh_search)

        //setSupportActionBar(toolbar)
        supportActionBar?.title = "Search Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = SearchEventPresenter(this, apiRepository, gson)

        //val recyclerView = find(R.id.rv_search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = SearchMatchAdapter(event) {

            this.startActivity<MatchDetailActivity>("id_home" to it.homeId, "id_away" to it.awayId,
                "id_event" to it.eventId)
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
                presenter.getSearchMatch(query.toString().replace(" ", "_"))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.getSearchMatch(newText.toString().replace(" ", "_"))
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

    override fun showListSearch(data: List<MatchEvent>) {
        swiperefresh.isRefreshing = false
        event.clear()
        event.addAll(data)
        listAdapter.notifyDataSetChanged()
    }

}
