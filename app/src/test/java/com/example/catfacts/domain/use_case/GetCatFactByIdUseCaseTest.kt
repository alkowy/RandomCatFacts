package com.example.catfacts.domain.use_case

import app.cash.turbine.test
import com.example.catfacts.common.Resource
import com.example.catfacts.data.repository.FakeCatFactsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetCatFactByIdUseCaseTest {

    private lateinit var getCatFactByIdUseCase: GetCatFactByIdUseCase
    private lateinit var fakeRepository: FakeCatFactsRepository

    @Before
    fun setUp() {
        fakeRepository = FakeCatFactsRepository()
        getCatFactByIdUseCase = GetCatFactByIdUseCase(fakeRepository)
    }

    @Test
    fun `getCatFactById returns a single CatFactModel with the correct id`() {
        runBlocking {
            getCatFactByIdUseCase.invoke("fakeId").test {
                assertEquals(true, awaitItem() is Resource.Loading)
                assertEquals("fakeId", awaitItem().data?.id)
                awaitComplete()
            }
        }
    }
}