package com.example.my.footballapps.view.teams

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.example.my.footballapps.R
import com.example.my.footballapps.R.color.colorAccent
import com.example.my.footballapps.R.id.button_search
import com.example.my.footballapps.Utils.invisible
import com.example.my.footballapps.Utils.visible
import com.example.my.footballapps.adapter.TeamAdapter
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.presenter.TeamPresenter
import com.example.my.footballapps.view.matches.search.TeamSearch
import com.example.my.footballapps.view.teams.detail.DetailTeamActivity
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsFragment : Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private var menuItem: Menu? = null

    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var league: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, apiRepository, gson)


        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        league = spinner.selectedItem.toString().replace("English Premier League", "English%20Premier%20League")
                        presenter.getTeamList(league)
                    }
                    1 -> {
                        league = spinner.selectedItem.toString().replace("Spanish La Liga", "Spanish%20La%20Liga")
                        presenter.getTeamList(league)
                    }
                    2 -> {
                        league = spinner.selectedItem.toString().replace("German Bundesliga", "German%20Bundesliga")
                        presenter.getTeamList(league)
                    }
                    3 -> {
                        league = spinner.selectedItem.toString().replace("Italian Serie A", "Italian%20Serie%20A")
                        presenter.getTeamList(league)
                    }
                    4 -> {
                        league = spinner.selectedItem.toString().replace("French Ligue 1", "French%20Ligue%201")
                        presenter.getTeamList(league)
                    }
                    5 -> {
                        league = spinner.selectedItem.toString().replace("Dutch Eredivisie", "Dutch%20Eredivisie")
                        presenter.getTeamList(league)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        adapter = TeamAdapter(teams) {
            context?.startActivity<DetailTeamActivity>("id_team" to it.teamId, "team_name" to it.teamName,
                "team_desc" to it.teamDescription)
        }
        listTeam.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            presenter.getTeamList(league)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        setHasOptionsMenu(true)
        return createView(AnkoContext.create(requireContext()))
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)
        menuItem = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            button_search -> {
                context?.startActivity<TeamSearch>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)


            spinner = spinner {
                id = R.id.spinner_teams
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = R.id.listTeam
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
