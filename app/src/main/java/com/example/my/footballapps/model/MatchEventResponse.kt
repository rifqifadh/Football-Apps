package com.example.my.footballapps.model

data class MatchEventResponse(

    val events: List<MatchEvent>
)
data class SearchMatchResponse(
    val event: List<MatchEvent>
)