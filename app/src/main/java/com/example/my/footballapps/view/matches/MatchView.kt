package com.example.my.footballapps.view.matches

import com.example.my.footballapps.model.MatchEvent

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<MatchEvent>)
}