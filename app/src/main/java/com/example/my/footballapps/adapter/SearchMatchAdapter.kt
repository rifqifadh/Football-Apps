package com.example.my.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.my.footballapps.R
import com.example.my.footballapps.Utils.changeFormatDate
import com.example.my.footballapps.Utils.strTodate
import com.example.my.footballapps.Utils.toGMTFormat
import com.example.my.footballapps.model.MatchEvent
import com.example.my.footballapps.view.matches.MatchUI
import org.jetbrains.anko.AnkoContext
import java.text.SimpleDateFormat

class SearchMatchAdapter (
    private val event: MutableList<MatchEvent>,
    private val listener: (MatchEvent) -> Unit)
    :RecyclerView.Adapter<SearchMatchAdapter.SearchViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMatchAdapter.SearchViewHolder {
        return SearchViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = event.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(event[position], listener)
    }

    class SearchViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        private val eventHomeTeam: TextView = view.findViewById(R.id.homeTeam)
        private val eventHomeScore: TextView = view.findViewById(R.id.homeScore)
        private val eventAwayTeam: TextView = view.findViewById(R.id.awayTeam)
        private val eventAwayScore: TextView = view.findViewById(R.id.awayScore)
        private val eventDate: TextView = view.findViewById(R.id.dateId)
        private val eventTime: TextView = view.findViewById(R.id.timeId)



        fun bind(event: MatchEvent, listener: (MatchEvent) -> Unit) {

            try {
                val date = strTodate(event.dateEvent)
                val dateTime = toGMTFormat(event.dateEvent, event.timeEvent)
                eventDate.text = changeFormatDate(date)
                eventTime.text = SimpleDateFormat("HH:mm").format(dateTime)
            }catch (e: Exception){
            }


            eventHomeTeam.text = event.teamHome
            eventHomeScore.text = event.scoreHome
            eventAwayTeam.text = event.teamAway
            eventAwayScore.text = event.scoreAway



            itemView.setOnClickListener { listener(event) }
        }
    }
}