package com.example.my.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.my.footballapps.R
import com.example.my.footballapps.db.FavoriteTeam
import com.example.my.footballapps.view.teams.TeamUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class FavoriteTeamAdapter(
    private val favorites: MutableList<FavoriteTeam>,
    private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamAdapter.FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position], listener)
    }


    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val teamBadge: ImageView = view.find(R.id.team_badge)
        private val teamName: TextView = view.find(R.id.team_name)

        fun bind(favorite: FavoriteTeam, listener: (FavoriteTeam) -> Unit){
            teamName.text = favorite.teamName
            Picasso.get().load(favorite.teamBadge).into(teamBadge)

            itemView.setOnClickListener { listener(favorite) }
        }
    }

}