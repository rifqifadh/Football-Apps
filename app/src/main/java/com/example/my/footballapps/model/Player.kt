package com.example.my.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Player (
    @SerializedName("idPlayer")
    var playerId: String? = null,

    @SerializedName("strPlayer")
    var playerName: String? = null,

    @SerializedName("strCutout")
    var playerBadge: String?,

    @SerializedName("strDescriptionEN")
    var playerDescription: String? = null,

    @SerializedName("strHeight")
    var playerHeight: String? = null,

    @SerializedName("strWeight")
    var playerWeight: String? = null,

    @SerializedName("strFanart1")
    var playerImage: String? = null,

    @SerializedName("strPosition")
    var playerPosition: String? = null
):Parcelable