package com.example.my.footballapps.presenter

import com.example.my.footballapps.Utils.CoroutineContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.MatchDetailResponse
import com.example.my.footballapps.model.TeamResponse
import com.example.my.footballapps.view.matches.detail.MatchDetailView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(homeId: String, awayId: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val dataHomeTeam = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetail(homeId)).await(),
                TeamResponse::class.java
            )

            val dataAwayTeam = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetail(awayId)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showTeamBadge(dataHomeTeam.teams, dataAwayTeam.teams)
        }
    }

    fun getMatchDetail(match: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventDetail(match)).await(),
                MatchDetailResponse::class.java
            )

            view.hideLoading()
            view.showMatchDetail(data.events)
        }
    }
}