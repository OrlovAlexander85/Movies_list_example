package com.orlov.myapplication.model

import java.io.Serializable

data class Actor(
    val id: Int,
    var name: String,
    var imageUrl: String
) : Serializable