package com.example.my.footballapps.view.matches.search

import com.example.my.footballapps.model.Team

interface SearchTeamView {

    fun showLoading()
    fun hideLoading()
    fun showSearchTeam(data: List<Team>)
}