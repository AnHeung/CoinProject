package kuma.coinproject.data.api.model

data class CoinItem(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val is_new: Boolean,
    val is_active: Boolean,
    val type: String
)