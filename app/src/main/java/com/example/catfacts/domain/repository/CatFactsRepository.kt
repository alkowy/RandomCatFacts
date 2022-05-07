package com.example.catfacts.domain.repository

import com.example.catfacts.data.remote.dto.CatFactDetailsDto
import com.example.catfacts.data.remote.dto.CatFactRandomDto

interface CatFactsRepository {

    suspend fun getRandomCatFacts(amountOfFacts: Int): List<CatFactRandomDto>

    suspend fun getCatFactById(factId: String): CatFactDetailsDto
}