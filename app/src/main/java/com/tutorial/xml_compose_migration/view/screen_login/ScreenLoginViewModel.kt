package com.tutorial.xml_compose_migration.view.screen_login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.xml_compose_migration.connection.ApiService
import com.tutorial.xml_compose_migration.model.ResLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScreenLoginViewModel : ViewModel() {

    private val _title: MutableSharedFlow<String> = MutableStateFlow("Screen 2")
    val title: Flow<String> = _title

    //var ListResponse: ResLogin by mutableStateOf()
    var ListResponse: MutableList<ResLogin> by mutableStateOf(mutableListOf())
    var errorMessage: String by mutableStateOf("")
    var suksesMessage: String by mutableStateOf("")

    init {
        viewModelScope.launch {
            for (i in 1..5) {
                _title.emit("Screen 2: title $i")
                delay(2000)
            }
        }
    }

    fun getLogin(reqLogin: RequestBody) {
        viewModelScope.launch {
            try {
                val apiService = ApiService.getInstance()
                val call = apiService.getLogin(reqLogin)
                call?.enqueue(object : Callback<ResLogin> {
                    override fun onFailure(call: Call<ResLogin>, t: Throwable) {
                        errorMessage = t.message.toString()
                    }

                    override fun onResponse(call: Call<ResLogin>, response: Response<ResLogin>) {
                        if(response.isSuccessful){
                            Log.e("isi data", response.body().toString())
                            suksesMessage = "berhasil"
                        } else {
                            errorMessage = ""
                        }
                    }
                })
            }
            catch (e: Exception) {
                errorMessage =  e.message.toString()
            }
        }
    }
}