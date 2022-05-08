package com.example.catfacts.presentation.cat_fact_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.common.DefaultDispatchers
import com.example.catfacts.common.Resource
import com.example.catfacts.domain.model.CatFactModel
import com.example.catfacts.domain.use_case.GetCatFactByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CatFactDetailsViewModel @Inject constructor(
    private val getCatFactByIdUseCase: GetCatFactByIdUseCase,
    private val dispatchers: DefaultDispatchers
) : ViewModel() {

    private val _catFactDetailsState = MutableStateFlow(CatFactDetailState())
    val catFactDetailsState: StateFlow<CatFactDetailState> = _catFactDetailsState

    fun getCatFactById(factId: String) {
        getCatFactByIdUseCase(factId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _catFactDetailsState.value = CatFactDetailState(
                        catFacts = result.data ?: CatFactModel("", "", "")
                    )
                }
                is Resource.Error -> {
                    _catFactDetailsState.value = CatFactDetailState(
                        error = result.message ?: "Some error occurred."
                    )
                }
                is Resource.Loading -> {
                    _catFactDetailsState.value = CatFactDetailState(
                        isLoading = true
                    )
                }
            }
        }.flowOn(dispatchers.main).launchIn(viewModelScope)
    }
}