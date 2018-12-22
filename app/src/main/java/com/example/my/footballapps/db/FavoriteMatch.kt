package com.example.my.footballapps.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMatch(val id: Long?, val eventId: String?,
                    val teamHome: String?,
                    val scoreHome: String?,
                    val teamAway: String?,
                    val scoreAway: String?,
                    val dateEvent: String?,
                    val timeEvent: String?,
                    val homeId: String?,
                    val awayId: String?):Parcelable {

    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_ID: String = "HOME_ID"
        const val AWAY_ID: String = "AWAY_ID"
        const val DATE: String = "DATE"
        const val TIME: String = "TIME"
    }
}