package com.example.my.footballapps.presenter

import com.example.my.footballapps.Utils.CoroutineContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.TeamResponse
import com.example.my.footballapps.view.matches.search.SearchTeamView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SearchTeamPresenter(private val view: SearchTeamView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getSearchTeam(query: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamSearch(query)).await(),
                    TeamResponse::class.java)


            view.showSearchTeam(data.teams)
            view.hideLoading()
        }
    }
}