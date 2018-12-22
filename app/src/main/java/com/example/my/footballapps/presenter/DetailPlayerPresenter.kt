package com.example.my.footballapps.presenter

import com.example.my.footballapps.Utils.CoroutineContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.PlayerDetailResponse
import com.example.my.footballapps.view.teams.detail.player.DetailPlayerView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPlayerPresenter(private val view: DetailPlayerView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getPlayerDetail(id: String?){
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerDetail(id)).await(),
            PlayerDetailResponse::class.java)

            view.showPlayerDetail(data.players)
            view.hideLoading()
        }
    }
}