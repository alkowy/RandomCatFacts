package com.example.catfacts.presentation.cat_facts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.common.Resource
import com.example.catfacts.domain.use_case.GetRandomCatFactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CatFactsListViewModel @Inject constructor(
    private val getRandomCatFactsUseCase: GetRandomCatFactsUseCase
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
        }.launchIn(viewModelScope)
    }
}