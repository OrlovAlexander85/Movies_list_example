package com.orlov.myapplication.model

data class Movie(
    val id: Int,
    val title: String,
    val rating: Float,
    val imageUrl: String,
    val detailImageUrl: String,
    val pgAge: Int,
    val reviewCount: Int,
    val genres: List<Genre>,
    val duration: Int,
    val storyLine: String,
    val favourite: Boolean,
    val actors:List<Actor>
)
