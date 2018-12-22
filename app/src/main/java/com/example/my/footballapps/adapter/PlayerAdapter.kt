package com.example.my.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.my.footballapps.R
import com.example.my.footballapps.R.id.*
import com.example.my.footballapps.model.Player
import com.example.my.footballapps.view.teams.detail.player.PlayerUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class PlayerAdapter(private val players: List<Player>,
                    private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position], listener)
    }

    class PlayerViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val playerBadge: ImageView = view.find(player_photo)
        private val playerName: TextView = view.find(player_name)
        private val playerPosition: TextView = view.find(player_position)

        fun bind(players: Player, listener: (Player) -> Unit) {

            Picasso.get().load(players.playerBadge).placeholder(R.drawable.default_player).into(playerBadge)

            playerName.text = players.playerName
            playerPosition.text = players.playerPosition

            itemView.setOnClickListener { listener(players) }
        }
    }
}
