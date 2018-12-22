package com.example.my.footballapps.presenter

import com.example.my.footballapps.TestContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.MatchEvent
import com.example.my.footballapps.model.MatchEventResponse
import com.example.my.footballapps.view.matches.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLastMatch() {
        val events: MutableList<MatchEvent> = mutableListOf()
        val response = MatchEventResponse(events)
        val id = "4328"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getLastMatch(id)).await(),
                MatchEventResponse::class.java)).thenReturn(response)

            presenter.getLastMatch(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(events)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getNextMatch() {
        val events: MutableList<MatchEvent> = mutableListOf()
        val response = MatchEventResponse(events)
        val id = "4328"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextMatch(id)).await(),
                MatchEventResponse::class.java)).thenReturn(response)

            presenter.getNextMatch(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(events)
            Mockito.verify(view).hideLoading()
        }
    }
}