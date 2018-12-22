package com.example.my.footballapps.presenter

import com.example.my.footballapps.Utils.CoroutineContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.PlayerResponse
import com.example.my.footballapps.view.teams.detail.player.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(
    private val view: PlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerTeam(teamName: String?) {

        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayer(teamName)).await(),
                PlayerResponse::class.java)

            view.showPlayerlist(data.player)
            view.hideLoading()
        }
    }
}