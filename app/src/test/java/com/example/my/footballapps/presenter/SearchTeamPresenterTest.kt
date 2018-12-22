package com.example.my.footballapps.presenter

import com.example.my.footballapps.TestContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.model.TeamResponse
import com.example.my.footballapps.view.matches.search.SearchTeamView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SearchTeamPresenterTest{

    @Mock
    lateinit var view: SearchTeamView

    @Mock
    lateinit var apiRepository: ApiRepository

    @Mock
    lateinit var gson: Gson

    private lateinit var presenter: SearchTeamPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = SearchTeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getSearchTeam(){
        val teams: MutableList<Team> = mutableListOf()
        val response =  TeamResponse(teams)
        val teamName = "Arsenal"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamSearch(teamName)).await(),
                TeamResponse::class.java)).thenReturn(response)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showSearchTeam(teams)
            Mockito.verify(view).hideLoading()
        }
    }

}