package com.example.catfacts.presentation.cat_facts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.common.DefaultDispatchers
import com.example.catfacts.common.Resource
import com.example.catfacts.domain.use_case.GetRandomCatFactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CatFactsListViewModel @Inject constructor(
    private val getRandomCatFactsUseCase: GetRandomCatFactsUseCase,
    private val dispatchers: DefaultDispatchers
) : ViewModel() {

    private val _catFactsListState = MutableStateFlow(CatFactsState())
    val catFactsListState: StateFlow<CatFactsState> = _catFactsListState

    fun getRandomCatFacts(amountOfFacts: Int) {
        getRandomCatFactsUseCase(amountOfFacts).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _catFactsListState.value = CatFactsState(
                        catFacts = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _catFactsListState.value = CatFactsState(
                        error = result.message ?: "Some error occurred."
                    )
                }
                is Resource.Loading -> {
                    _catFactsListState.value = CatFactsState(
                        isLoading = true
                    )
                }
            }
        }.flowOn(dispatchers.main).launchIn(viewModelScope)
    }
}