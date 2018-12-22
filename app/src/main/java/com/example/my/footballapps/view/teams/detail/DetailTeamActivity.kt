package com.example.my.footballapps.view.teams.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.my.footballapps.R.drawable.ic_add_to_favorites
import com.example.my.footballapps.R.drawable.ic_added_to_favorite
import com.example.my.footballapps.R.id.*
import com.example.my.footballapps.R.menu.detail_menu
import com.example.my.footballapps.R.string.label_added_to_favorite
import com.example.my.footballapps.R.string.label_removed_from_favorite
import com.example.my.footballapps.Utils.invisible
import com.example.my.footballapps.Utils.visible
import com.example.my.footballapps.adapter.DetailTeamPagerAdapter
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.db.FavoriteTeam
import com.example.my.footballapps.db.databaseTeam
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.presenter.TeamDetailPresenter
import com.example.my.footballapps.view.teams.detail.player.PlayersFragment
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

class DetailTeamActivity: AppCompatActivity(), DetailTeamView {

    private lateinit var progressBar: ProgressBar
    private lateinit var linearLayout: LinearLayout
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    private lateinit var teamId: String
    private lateinit var teamName: String
    private lateinit var teamDesc: String


    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var teamBadge: ImageView
    private lateinit var txtTeamname: TextView
    private lateinit var txtTeamFormedYear: TextView
    private lateinit var txtTeamStadium: TextView
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var detailPagerAdapter: DetailTeamPagerAdapter
    private lateinit var teams: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailTeamUI().setContentView(this)

        val intent = intent
        teamId = intent.getStringExtra("id_team")
        teamName = intent.getStringExtra("team_name")
        teamDesc = intent.getStringExtra("team_desc")

        favoriteState()

        castingObjectFromUI()

        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(teamId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun castingObjectFromUI() {
        linearLayout = findViewById(detail_team_UI)
        progressBar = findViewById(progress_bar_detail)
        teamBadge = findViewById(team_badge_detail)
        txtTeamname = findViewById(team_name_detail)
        txtTeamFormedYear = findViewById(team_formedyear_detail)
        txtTeamStadium = findViewById(team_stadium_detail)

        tabLayout = find(tabs_detail_team)
        viewPager = find(viewpager_detail_team)

        tabLayout.addTab(tabLayout.newTab().setText("Overview"))
        tabLayout.addTab(tabLayout.newTab().setText("Players"))

        detailPagerAdapter = DetailTeamPagerAdapter(supportFragmentManager)
        detailPagerAdapter.addFrag(OverviewTeamFragment.newInstance(teamDesc), "Overview")
        detailPagerAdapter.addFrag(PlayersFragment.newInstance(teamName), "Player")

        viewPager.adapter = detailPagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun favoriteState(){
        databaseTeam.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            databaseTeam.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to teams.teamId,
                    FavoriteTeam.TEAM_NAME to teams.teamName,
                    FavoriteTeam.TEAM_BADGE to teams.teamBadge,
                    FavoriteTeam.TEAM_DESCRIPTION to teamDesc)
            }
            Snackbar.make(linearLayout, label_added_to_favorite, Snackbar.LENGTH_SHORT).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(linearLayout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            databaseTeam.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to teamId)
            }
            Snackbar.make(linearLayout, label_removed_from_favorite, Snackbar.LENGTH_SHORT).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(linearLayout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<Team>) {
        teams = Team(data[0].teamId,
            data[0].teamName,
            data[0].teamBadge,
            data[0].teamFormedYear,
            data[0].teamStadium)

        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        txtTeamname.text = data[0].teamName
        txtTeamFormedYear.text = data[0].teamFormedYear
        txtTeamStadium.text = data[0].teamStadium
    }

    private fun setFavorite(){
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}