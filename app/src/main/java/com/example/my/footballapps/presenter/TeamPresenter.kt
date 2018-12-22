package com.example.my.footballapps.presenter

import com.example.my.footballapps.Utils.CoroutineContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.TeamResponse
import com.example.my.footballapps.view.teams.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TeamPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?){
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeam(league)).await(),
                TeamResponse::class.java)

            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }
}