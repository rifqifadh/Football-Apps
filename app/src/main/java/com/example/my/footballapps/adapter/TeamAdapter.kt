package com.example.my.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.my.footballapps.R
import com.example.my.footballapps.model.Team
import com.example.my.footballapps.view.teams.TeamUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext

class TeamAdapter(
    private val teams: List<Team>,
    private val listener: (Team) -> Unit)
    :RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position], listener)
    }

    class TeamViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val teamBadge: ImageView = view.findViewById(R.id.team_badge)
        private val teamName: TextView = view.findViewById(R.id.team_name)

        fun bind(teams: Team, listener: (Team) -> Unit){
            teamName.text = teams.teamName
            Picasso.get().load(teams.teamBadge).into(teamBadge)

            itemView.setOnClickListener { listener(teams) }
        }
    }
}