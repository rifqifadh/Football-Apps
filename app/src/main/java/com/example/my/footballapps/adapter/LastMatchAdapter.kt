package com.example.my.footballapps.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.my.footballapps.R.id.*
import com.example.my.footballapps.Utils.changeFormatDate
import com.example.my.footballapps.Utils.strTodate
import com.example.my.footballapps.Utils.toGMTFormat
import com.example.my.footballapps.model.MatchEvent
import com.example.my.footballapps.view.matches.MatchUI
import org.jetbrains.anko.AnkoContext
import java.text.SimpleDateFormat

class LastMatchAdapter(
    private val events: MutableList<MatchEvent>,
    private val listener: (MatchEvent) -> Unit)
    :RecyclerView.Adapter<LastMatchAdapter.EventViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMatchAdapter.EventViewHolder {
        return EventViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

            holder.bind(events[position], listener)

    }

    class EventViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val eventHomeTeam: TextView = view.findViewById(homeTeam)
        private val eventHomeScore: TextView = view.findViewById(homeScore)
        private val eventAwayTeam: TextView = view.findViewById(awayTeam)
        private val eventAwayScore: TextView = view.findViewById(awayScore)
        private val eventDate: TextView = view.findViewById(dateId)
        private val eventTime: TextView = view.findViewById(timeId)

        @SuppressLint("SimpleDateFormat")
        fun bind(events: MatchEvent, listener: (MatchEvent) -> Unit) {
            val date = strTodate(events.dateEvent)
            val dateTime = toGMTFormat(events.dateEvent, events.timeEvent)
            eventDate.text = changeFormatDate(date)
            eventTime.text = SimpleDateFormat("HH:mm").format(dateTime)

            eventHomeTeam.text = events.teamHome
            eventHomeScore.text = events.scoreHome
            eventAwayTeam.text = events.teamAway
            eventAwayScore.text = events.scoreAway

            itemView.setOnClickListener { listener(events) }
        }
    }
}