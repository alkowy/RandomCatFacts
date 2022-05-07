package com.example.catfacts.data.remote

import com.example.catfacts.data.remote.dto.CatFactDetailsDto
import com.example.catfacts.data.remote.dto.CatFactRandomDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatFactsApi {

    @GET("/facts/random")
    suspend fun getRandomCatFacts(@Query("amount") amountOfFacts : Int) : List<CatFactRandomDto>

    @GET("/facts/{factId}")
    suspend fun getCatFactById(@Path("factId")factId : String) : CatFactDetailsDto

}