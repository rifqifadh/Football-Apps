package com.example.my.footballapps.presenter

import com.example.my.footballapps.TestContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.MatchEvent
import com.example.my.footballapps.model.MatchEventResponse
import com.example.my.footballapps.view.matches.search.SearchMatchView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SearchEventPresenterTest {

    @Mock
    lateinit var view: SearchMatchView

    @Mock
    lateinit var apiRepository: ApiRepository

    @Mock
    lateinit var gson: Gson

    private lateinit var presenter: SearchEventPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = SearchEventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getSearchEvent(){
        val events: MutableList<MatchEvent> = mutableListOf()
        val response = MatchEventResponse(events)
        val eventName = "Arsenal_vs_Chelsea"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventSearch(eventName)).await(),
                MatchEventResponse::class.java)).thenReturn(response)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showListSearch(events)
            Mockito.verify(view).hideLoading()
        }
    }
}