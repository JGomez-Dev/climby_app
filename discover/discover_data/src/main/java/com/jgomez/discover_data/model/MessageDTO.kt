package com.jgomez.discover_data.model

import com.google.gson.annotations.SerializedName

data class MessageDTO(
    @SerializedName("id_message") val id_message: Int,
    @SerializedName("Boolean") val read: Boolean,
    @SerializedName("text_message") val text_message: String
)