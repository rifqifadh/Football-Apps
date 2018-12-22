package com.example.my.footballmatchschedule.presenter

import com.example.my.footballapps.TestContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.MatchDetail
import com.example.my.footballapps.model.MatchDetailResponse
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.model.TeamResponse
import com.example.my.footballapps.presenter.MatchDetailPresenter
import com.example.my.footballapps.view.matches.detail.MatchDetailView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {

    @Mock
    private
    lateinit var view: MatchDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val awayId = "133604"
        val homeId = "133623"

        GlobalScope.launch {
            Mockito.`when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(awayId)).await(),
                TeamResponse::class.java)).thenReturn(response)

            Mockito.`when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(homeId)).await(),
                TeamResponse::class.java)).thenReturn(response)

            presenter.getTeamDetail(awayId, homeId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeamBadge(response.teams, response.teams)
            Mockito.verify(view).hideLoading()

        }
    }

    @Test
    fun getMatchDetail() {
        val events: MutableList<MatchDetail> = mutableListOf()
        val response = MatchDetailResponse(events)
        val id = "1234"

        GlobalScope.launch {
            Mockito.`when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventDetail(id)).await(),
                MatchDetailResponse::class.java)).thenReturn(response)

            presenter.getMatchDetail(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchDetail(events)
            Mockito.verify(view).hideLoading()
        }
    }
}