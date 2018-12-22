package com.example.my.footballapps.presenter

import com.example.my.footballapps.TestContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.Player
import com.example.my.footballapps.model.PlayerResponse
import com.example.my.footballapps.view.teams.detail.player.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    @Mock
    private lateinit var view: PlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenter

    @Before
    fun  setUP(){
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPlayer(){
        val players: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(players)
        val id = "Arsenal"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayer(id)).await(),
                PlayerResponse::class.java)).thenReturn(response)

            presenter.getPlayerTeam(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPlayerlist(players)
            Mockito.verify(view).hideLoading()
        }
    }
}