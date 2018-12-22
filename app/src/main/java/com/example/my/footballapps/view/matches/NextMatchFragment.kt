package com.example.my.footballapps.view.matches


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner

import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.invisible
import com.example.my.footballapps.Utils.visible
import com.example.my.footballapps.adapter.NextMatchAdapter
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.model.MatchEvent
import com.example.my.footballapps.presenter.MatchPresenter
import com.example.my.footballapps.view.matches.detail.MatchDetailActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_next_match.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh


class NextMatchFragment : Fragment(), MatchView {

    private var events: MutableList<MatchEvent> = mutableListOf()


    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner: Spinner
    private lateinit var league: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)


        spinner = view.next_match_spinner
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        league = spinner.selectedItem.toString().replace("English Premier League", "4328")
                        presenter.getNextMatch(league)
                    }
                    1 -> {
                        league = spinner.selectedItem.toString().replace("Spanish La Liga", "4335")
                        presenter.getNextMatch(league)
                    }
                    2 -> {
                        league = spinner.selectedItem.toString().replace("German Bundesliga", "4331")
                        presenter.getNextMatch(league)
                    }
                    3 -> {
                        league = spinner.selectedItem.toString().replace("Italian Serie A", "4332")
                        presenter.getNextMatch(league)
                    }
                    4 -> {
                        league = spinner.selectedItem.toString().replace("French Ligue 1", "4334")
                        presenter.getNextMatch(league)
                    }
                    5 -> {
                        league = spinner.selectedItem.toString().replace("Dutch Eredivisie", "4337")
                        presenter.getNextMatch(league)
                    }
                }
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_next_match)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NextMatchAdapter(events) {
            context?.startActivity<MatchDetailActivity>("id_home" to it.homeId, "id_away" to it.awayId,
                "id_event" to it.eventId)
        }
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.swipe_refresh_next
        progressBar = view.progress_bar_next

        swipeRefreshLayout.onRefresh {
            presenter.getNextMatch(league)
        }

        showLoading()
        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, apiRepository, gson)

        return view
    }

    override fun showMatchList(data: List<MatchEvent>) {
        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
        hideLoading()
    }


    companion object {

        @JvmStatic
        fun newInstance() = NextMatchFragment()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}
