package com.example.catfacts.domain.use_case

import com.example.catfacts.common.Resource
import com.example.catfacts.data.remote.dto.toCatFact
import com.example.catfacts.domain.model.CatFactModel
import com.example.catfacts.domain.repository.CatFactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCatFactByIdUseCase @Inject constructor(
    private val repository: CatFactsRepository
) {
    operator fun invoke(factId: String): Flow<Resource<CatFactModel>> = flow {
        try {
            emit(Resource.Loading())
            val catFacts = repository.getCatFactById(factId).toCatFact()
            emit(Resource.Success(catFacts))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Some Http error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Check your Internet connection"))
        }
    }
}