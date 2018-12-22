package com.example.my.footballapps.view.matches.detail

import com.example.my.footballapps.model.MatchDetail
import com.example.my.footballapps.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamBadge(dataHomeTeam: List<Team>, dataAwayTeam: List<Team>)
    fun showMatchDetail(data: List<MatchDetail>)
}