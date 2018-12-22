package com.example.my.footballapps.view.teams.detail.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.invisible
import com.example.my.footballapps.Utils.visible
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.model.Player
import com.example.my.footballapps.presenter.DetailPlayerPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

class PlayerDetails: AppCompatActivity(), DetailPlayerView {

    private lateinit var progressBar: ProgressBar
    private lateinit var toolbar: Toolbar
    private lateinit var position: TextView
    private lateinit var weigth: TextView
    private lateinit var height: TextView
    private lateinit var desc: TextView
    private lateinit var banner: ImageView
    private lateinit var name: TextView
    private lateinit var presenter: DetailPlayerPresenter

    private lateinit var idPlayer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerDetailUI().setContentView(this)

        idPlayer = intent.getStringExtra("id_player")

        toolbar = find(R.id.toolbar)
        position = find(R.id.player_position)
        weigth = find(R.id.player_weight)
        height = find(R.id.player_height)
        desc = find(R.id.player_desc)
//        name = find(R.id.player_name)
        banner = find(R.id.player_banner)
        progressBar = find(R.id.player_progressBar)

        //setSupportActionBar(toolbar)
        supportActionBar?.title = "Player Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPlayerPresenter(this, request, gson)

        presenter.getPlayerDetail(idPlayer)
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

    override fun showPlayerDetail(data: List<Player>) {

        val playerBanner: ImageView = find(R.id.player_banner)
        val playerWeigth: TextView = find(R.id.player_weight)
        val playerHeight: TextView = find(R.id.player_height)
        val playerDesc: TextView = find(R.id.player_desc)
        val playerPos: TextView = find(R.id.player_position)
        //val playerName: TextView = find(R.id.player_name)


        toolbar.title = data[0].playerName
        Picasso.get().load(data[0].playerImage).into(playerBanner)
        playerWeigth.text = data[0].playerWeight
        //playerName.text = data.playerName
        playerHeight.text = data[0].playerHeight
        playerDesc.text = data[0].playerDescription
        playerPos.text = data[0].playerPosition
    }

}