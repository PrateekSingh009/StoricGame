package com.projects.thestoricgame.main.api

import com.projects.thestoricgame.model.UserItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("inventory/v1")
    suspend fun getInventory() : Response<List<UserItem>>
}