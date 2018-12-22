package com.example.my.footballapps.view.teams.detail

import com.example.my.footballapps.model.Team

interface DetailTeamView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Team>)
}