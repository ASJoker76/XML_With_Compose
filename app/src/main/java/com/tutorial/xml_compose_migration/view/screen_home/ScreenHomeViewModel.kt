package com.tutorial.xml_compose_migration.view.screen_home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.xml_compose_migration.connection.ApiService
import com.tutorial.xml_compose_migration.model.ResMovie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ScreenHomeViewModel : ViewModel() {

    private val _title: MutableSharedFlow<String> = MutableStateFlow("Screen 2")
    val title: Flow<String> = _title

    var movieListResponse:List<ResMovie> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    init {
        viewModelScope.launch {
            for (i in 1..5) {
                _title.emit("Screen 2: title $i")
                delay(2000)
            }
        }
    }

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