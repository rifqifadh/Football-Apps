package com.example.my.footballapps.view.teams

import com.example.my.footballapps.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}