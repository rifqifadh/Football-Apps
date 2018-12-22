package com.example.my.footballapps.presenter

import com.example.my.footballapps.Utils.CoroutineContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.MatchEventResponse
import com.example.my.footballapps.view.matches.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLastMatch(id: String?){
        view.showLoading()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLastMatch(id)).await(),
                MatchEventResponse::class.java)

            view.hideLoading()
            view.showMatchList(data.events)

        }
    }

    fun getNextMatch(id: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextMatch(id)).await(),
                MatchEventResponse::class.java)

            view.hideLoading()
            view.showMatchList(data.events)

        }
    }
}