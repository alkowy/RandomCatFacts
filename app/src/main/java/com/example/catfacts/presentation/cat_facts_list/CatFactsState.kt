package com.example.catfacts.presentation.cat_facts_list

import com.example.catfacts.domain.model.CatFactModel

data class CatFactsState(
    val isLoading :Boolean = false,
    val catFacts : List<CatFactModel> = emptyList(),
    val error: String = ""
)
