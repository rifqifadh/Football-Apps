package com.example.my.footballapps.presenter

import com.example.my.footballapps.TestContextProvider
import com.example.my.footballapps.api.ApiRepository
import com.example.my.footballapps.api.TheSportDBApi
import com.example.my.footballapps.model.Player
import com.example.my.footballapps.model.PlayerDetailResponse
import com.example.my.footballapps.view.teams.detail.player.DetailPlayerView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class DetailPlayerPresenterTest {

    @Mock
    private lateinit var view: DetailPlayerView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenter: DetailPlayerPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailPlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetDetailPlayer(){
        val player: MutableList<Player> = mutableListOf()
        val response = PlayerDetailResponse(player)
        val playerId = "34146370"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayerDetail(playerId)).await(),
                    PlayerDetailResponse::class.java)
            ).thenReturn(response)

            presenter.getPlayerDetail(playerId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPlayerDetail(player)
            Mockito.verify(view).hideLoading()
        }

    }

}