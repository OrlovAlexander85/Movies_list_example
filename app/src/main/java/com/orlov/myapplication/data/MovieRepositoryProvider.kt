package com.orlov.myapplication.data

interface MovieRepositoryProvider {
    fun provideMovieRepository():MovieRepository
}