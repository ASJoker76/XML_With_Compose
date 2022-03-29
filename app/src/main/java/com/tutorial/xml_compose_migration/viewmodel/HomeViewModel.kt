package com.tutorial.xml_compose_migration.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.xml_compose_migration.connection.ApiService
import com.tutorial.xml_compose_migration.model.ResMovie
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var movieListResponse:List<ResMovie> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getMovieList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val movieList = apiService.getMovies()
                movieListResponse = movieList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}