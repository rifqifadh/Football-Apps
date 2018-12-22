package com.example.my.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.changeFormatDate
import com.example.my.footballapps.Utils.strTodate
import com.example.my.footballapps.Utils.toGMTFormat
import com.example.my.footballapps.db.FavoriteMatch
import com.example.my.footballapps.view.matches.MatchUI
import org.jetbrains.anko.AnkoContext
import java.text.SimpleDateFormat

class FavoriteMatchAdapter(
    private val favorite: MutableList<FavoriteMatch>,
    private val listener: (FavoriteMatch) -> Unit)
    :RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorite[position], listener)
    }

    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val eventHomeTeam: TextView = view.findViewById(R.id.homeTeam)
        private val eventHomeScore: TextView = view.findViewById(R.id.homeScore)
        private val eventAwayTeam: TextView = view.findViewById(R.id.awayTeam)
        private val eventAwayScore: TextView = view.findViewById(R.id.awayScore)
        private val eventDate: TextView = view.findViewById(R.id.dateId)
        private val eventTime: TextView = view.findViewById(R.id.timeId)

        fun bind(favorite: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {
            val date = strTodate(favorite.dateEvent)
            val dateTime = toGMTFormat(favorite.dateEvent, favorite.timeEvent)
            eventDate.text = changeFormatDate(date)
            eventTime.text = SimpleDateFormat("HH:mm").format(dateTime)

            eventHomeTeam.text = favorite.teamHome
            eventHomeScore.text = favorite.scoreHome
            eventAwayTeam.text = favorite.teamAway
            eventAwayScore.text = favorite.scoreAway

            itemView.setOnClickListener { listener(favorite) }
        }
    }
}
