package com.example.catfacts.presentation.cat_fact_details

import com.example.catfacts.domain.model.CatFactModel

data class CatFactDetailState(
    val isLoading :Boolean = false,
    val catFacts : CatFactModel = CatFactModel("","",""),
    val error: String = ""
)
