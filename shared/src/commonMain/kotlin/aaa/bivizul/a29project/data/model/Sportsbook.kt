package aaa.bivizul.a29project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Sportsbook(
    val id: Int,
    val title: String,
    val description: String,
)