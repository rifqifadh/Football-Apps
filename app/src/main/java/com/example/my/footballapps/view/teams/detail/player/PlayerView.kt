package com.example.my.footballapps.view.teams.detail.player

import com.example.my.footballapps.model.Player

interface PlayerView {

    fun showLoading()
    fun hideLoading()
    fun showPlayerlist(data: List<Player>)
}