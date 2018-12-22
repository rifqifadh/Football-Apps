package com.example.my.footballapps.view.matches.detail

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.example.my.footballapps.R
import com.example.my.footballapps.R.drawable.ic_add_to_favorites
import com.example.my.footballapps.R.drawable.ic_added_to_favorite
import com.example.my.footballapps.R.id.add_to_favorite
import com.example.my.footballapps.R.menu.detail_menu
import com.example.my.footballapps.R.string.label_added_to_favorite
import com.example.my.footballapps.R.string.label_removed_from_favorite
import com.example.my.footballapps.Utils.changeFormatDate
import com.example.my.footballapps.Utils.strTodate
import com.example.my.footballapps.Utils.toGMTFormat
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.db.FavoriteMatch
import com.example.my.footballapps.db.database
import com.example.my.footballapps.model.MatchDetail
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.presenter.MatchDetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    private var eventId: String = ""
    private var homeId: String = ""
    private var awayId: String = ""
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var presenter: MatchDetailPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var match: MatchDetail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        eventId = intent.getStringExtra("id_event")
        homeId = intent.getStringExtra("id_home")
        awayId = intent.getStringExtra("id_away")

        progressBar = progressbar

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, request, gson)
        presenter.getMatchDetail(eventId)
        presenter.getTeamDetail(homeId, awayId)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs("(ID_EVENT = {id})",
                    "id" to eventId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
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

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.ID_EVENT to eventId,
                    FavoriteMatch.HOME_TEAM to match.homeName,
                    FavoriteMatch.HOME_SCORE to match.scoreHome,
                    FavoriteMatch.AWAY_TEAM to match.awayName,
                    FavoriteMatch.AWAY_SCORE to match.scoreAway,
                    FavoriteMatch.DATE to match.dateEvent,
                    FavoriteMatch.TIME to match.timeEvent,
                    FavoriteMatch.HOME_ID to homeId,
                    FavoriteMatch.AWAY_ID to awayId
                )
            }
            Snackbar.make(root_layout, label_added_to_favorite, Snackbar.LENGTH_SHORT).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(root_layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(ID_EVENT = {id})",
                    "id" to eventId)
            }
            Snackbar.make(root_layout, label_removed_from_favorite, Snackbar.LENGTH_SHORT).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(root_layout, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite(){
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showTeamBadge(dataHomeTeam: List<Team>, dataAwayTeam: List<Team>) {

        Picasso.get().load(dataHomeTeam.get(0).teamBadge).into(imageHome)
        Picasso.get().load(dataAwayTeam.get(0).teamBadge).into(imageAway)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun showMatchDetail(data: List<MatchDetail>) {
        match = data[0]

        @SuppressLint("SimpleDateFormat")
        val date = strTodate(data[0].dateEvent)
        val dateTime = toGMTFormat(data[0].dateEvent, data[0].timeEvent)
        txtDate.text = changeFormatDate(date)
        txtTime.text = java.text.SimpleDateFormat("HH:mm").format(dateTime)

        txtteamHome.text = data[0].homeName
        txtscoreHome.text = data[0].scoreHome
        txtGoalsHome.text = data[0].homeGoal
        txtShotsHome.text = data[0].homeShots
        txtHomeGoalKeeper.text = data[0].homeGoalkeeper
        txtHomeDefense.text = data[0].homeDefense
        txtHomeMidfield.text = data[0].homeMidfield
        txtHomeForward.text = data[0].homeForward
        txtHomeSubtitutes.text = data[0].homeSubstitutes
        txtHomeFormation.text = data[0].homeFormation

        txtteamAway.text = data[0].awayName
        txtscoreAway.text = data[0].scoreAway
        txtGoalsAway.text = data[0].awayGoal
        txtShotsAway.text = data[0].awayShots
        txtAwayGoalKeeper.text = data[0].awayGoalkeeper
        txtAwayDefense.text = data[0].awayDefense
        txtAwayMidfield.text = data[0].awayMidfield
        txtAwayForward.text = data[0].awayForward
        txtAwaySubtitutes.text = data[0].awaySubstitutes
        txtAwayFormation.text = data[0].awayFormation

    }
}
