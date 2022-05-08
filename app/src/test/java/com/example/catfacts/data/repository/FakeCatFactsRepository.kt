package com.example.catfacts.data.repository

import com.example.catfacts.data.remote.dto.*
import com.example.catfacts.domain.repository.CatFactsRepository

class FakeCatFactsRepository : CatFactsRepository {

    override suspend fun getRandomCatFacts(amountOfFacts: Int): List<CatFactRandomDto> {
        val catFactsDtoList = mutableListOf<CatFactRandomDto>()
        for (i in 0 until amountOfFacts) {
            catFactsDtoList.add(
                CatFactRandomDto(
                    createdAt = "createdAt$i",
                    deleted = false,
                    id = "id$i",
                    source = "source$i",
                    status = StatusX(i, false),
                    text = "catFact $i",
                    type = "type$i",
                    updatedAt = "updatedAt$i",
                    used = false,
                    user = "user$i",
                    v = 0
                )
            )
        }
        return catFactsDtoList
    }

    override suspend fun getCatFactById(factId: String): CatFactDetailsDto {
        return CatFactDetailsDto(
            createdAt = "createdAt$factId",
            deleted = false,
            id = factId,
            source = "source$factId",
            status = Status(1, false),
            text = "catFact $factId",
            type = "type$factId",
            updatedAt = "updatedAt$factId",
            used = false,
            user = User(factId, Name("firstName", "secondName"), factId),
            v = 0
        )
    }
}