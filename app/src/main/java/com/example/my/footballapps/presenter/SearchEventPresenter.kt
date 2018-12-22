package com.example.my.footballapps.presenter

import com.example.my.footballapps.Utils.CoroutineContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.SearchMatchResponse
import com.example.my.footballapps.view.matches.search.SearchMatchView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchEventPresenter (private val view: SearchMatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getSearchMatch(query: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = withContext(Dispatchers.IO)
            {gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventSearch(query)).await(),
                SearchMatchResponse::class.java)}

            view.hideLoading()
            view.showListSearch(data.event)


        }

    }
}