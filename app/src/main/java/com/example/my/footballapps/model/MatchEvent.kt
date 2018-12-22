package com.example.my.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchEvent(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("idHomeTeam")
    var homeId: String,

    @SerializedName("idAwayTeam")
    var awayId: String,

    @SerializedName("idLeague")
    var idLeagues: String? = null,

    @SerializedName("strHomeTeam")
    var teamHome: String? = null,

    @SerializedName("strAwayTeam")
    var teamAway: String? = null,

    @SerializedName("intHomeScore")
    var scoreHome: String? = null,

    @SerializedName("intAwayScore")
    var scoreAway: String? = null,

    @SerializedName("dateEvent")
    var dateEvent: String? = null,

    @SerializedName("strTime")
    var timeEvent: String? = null

):Parcelable