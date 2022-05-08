package com.example.catfacts.domain.use_case

import app.cash.turbine.test
import com.example.catfacts.common.Resource
import com.example.catfacts.data.repository.FakeCatFactsRepository
import com.example.catfacts.domain.model.CatFactModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetRandomCatFactsUseCaseTest {

    private lateinit var getRandomCatFactsUseCase: GetRandomCatFactsUseCase
    private lateinit var fakeRepository: FakeCatFactsRepository

    @Before
    fun setUp() {
        fakeRepository = FakeCatFactsRepository()
        getRandomCatFactsUseCase = GetRandomCatFactsUseCase(fakeRepository)
    }

    @Test
    fun `getRandomCatFacts emits ResourceLoading first`() {
        runBlocking {
            getRandomCatFactsUseCase.invoke(5).test {
                assertEquals(true, awaitItem() is Resource.Loading)
            }
        }
    }

    @Test
    fun `getRandomCatFacts returns a list of 30 cat facts`() {
        runBlocking {
            getRandomCatFactsUseCase.invoke(30).test {
                assertEquals(true, awaitItem() is Resource.Loading)
                assertEquals(30, awaitItem().data?.size)
                awaitComplete()
            }
        }
    }

    @Test
    fun `getRandomCatFacts returns a list of CatFactModel`(){
        runBlocking {
            getRandomCatFactsUseCase.invoke(3).test {
                assertEquals(true, awaitItem() is Resource.Loading)
                assertEquals(true, awaitItem() is Resource.Success<List<CatFactModel>>)
                awaitComplete()
            }
        }
    }

}