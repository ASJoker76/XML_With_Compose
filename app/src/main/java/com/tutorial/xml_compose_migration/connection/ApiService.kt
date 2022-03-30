package com.tutorial.xml_compose_migration.connection

import com.tutorial.xml_compose_migration.model.ReqLogin
import com.tutorial.xml_compose_migration.model.ResLogin
import retrofit2.http.GET
import com.tutorial.xml_compose_migration.model.ResMovie
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @GET("movielist.json")
    suspend fun getMovies() : List<ResMovie>

    @POST("api_v1/login/loginAll")
    suspend fun getLogin(@Body requestBody: RequestBody): Call<ResLogin>?

    companion object {
        var apiService: ApiService? = null
        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://dportv2.development-big.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}