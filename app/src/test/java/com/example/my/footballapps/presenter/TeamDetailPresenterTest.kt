package com.example.my.footballapps.presenter

import com.example.my.footballapps.TestContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.model.TeamResponse
import com.example.my.footballapps.view.teams.detail.DetailTeamView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TeamDetailPresenterTest {

    @Mock
    private lateinit var view: DetailTeamView

    @Mock
    private lateinit var gson: Gson

    @Mock lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamDetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = TeamDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamDetail(){
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val id = "133604"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetail(id)).await(),
                TeamResponse::class.java)).thenReturn(response)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeamDetail(teams)
            Mockito.verify(view).hideLoading()
        }
    }
}