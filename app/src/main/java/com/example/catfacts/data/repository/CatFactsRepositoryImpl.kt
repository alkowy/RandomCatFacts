package com.example.catfacts.data.repository

import com.example.catfacts.data.remote.CatFactsApi
import com.example.catfacts.data.remote.dto.CatFactDetailsDto
import com.example.catfacts.data.remote.dto.CatFactRandomDto
import com.example.catfacts.domain.repository.CatFactsRepository
import javax.inject.Inject

class CatFactsRepositoryImpl @Inject constructor(
    private val api: CatFactsApi
) : CatFactsRepository {

    override suspend fun getRandomCatFacts(amountOfFacts: Int): List<CatFactRandomDto> {
        return api.getRandomCatFacts(amountOfFacts)
    }

    override suspend fun getCatFactById(factId: String): CatFactDetailsDto {
        return api.getCatFactById(factId)
    }
}