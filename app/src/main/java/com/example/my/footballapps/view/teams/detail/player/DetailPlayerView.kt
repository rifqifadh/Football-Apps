package com.example.my.footballapps.view.teams.detail.player

import com.example.my.footballapps.model.Player

interface DetailPlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(data: List<Player>)
}