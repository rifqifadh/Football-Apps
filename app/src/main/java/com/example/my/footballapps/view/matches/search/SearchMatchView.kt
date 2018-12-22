package com.example.my.footballapps.view.matches.search

import com.example.my.footballapps.model.MatchEvent

interface SearchMatchView {

    fun showLoading()
    fun hideLoading()
    fun showListSearch(data: List<MatchEvent>)
}