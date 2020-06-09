package com.example.urbandict.model.network

import com.example.urbandict.model.UrbanDictionaryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(
        "x-rapidapi-host: mashape-community-urban-dictionary.p.rapidapi.com",
        "x-rapidapi-key: 8d7db00e28msh4957bac26556084p1742bejsna55814557c22"
    )
    @GET("define")
    fun getDefinitions(@Query("term") term: String): Single<UrbanDictionaryResponse>

}