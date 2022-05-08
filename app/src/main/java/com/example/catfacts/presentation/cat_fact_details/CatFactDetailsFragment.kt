package com.example.catfacts.presentation.cat_fact_details

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.color
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.catfacts.R
import com.example.catfacts.common.Constants
import com.example.catfacts.databinding.FragmentCatFactDetailsBinding
import com.example.catfacts.presentation.cat_facts_list.CatFactsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CatFactDetailsFragment : Fragment() {
    private var _binding: FragmentCatFactDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by hiltNavGraphViewModels<CatFactDetailsViewModel>(R.id.nav_graph)
    private var catFactId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            catFactId = it.getString("catFactId", "")
        }
        _binding = FragmentCatFactDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        fetchCatFactById(catFactId)
        collectCatFactById()
    }

    private fun collectCatFactById() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.catFactDetailsState.collect { state ->
                    Log.d("CatFactsListFragment", "collectState: ${state.toString()}")
                    if (state.isLoading) {
                        showProgressBar()
                    }
                    if (state.error.isNotBlank()) {
                        Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                    } else if (state != CatFactDetailState()) {
                        setTexts(state)
                        delay(500)
                        hideProgressBar()
                    }
                }
            }
        }
    }

    private fun setTexts(catFactDetails: CatFactDetailState) {
        val catFact = SpannableStringBuilder()
            .bold { append(getString(R.string.cats_fact)) }
            .append("\n${catFactDetails.catFacts.text}")

        val catFactUpdated = SpannableStringBuilder()
            .bold { append(getString(R.string.cats_fact_updated)) }
            .append("\n ${catFactDetails.catFacts.updatedAt}")

        binding.catFactDetailTv.text = catFact
        binding.catFactDetailUpdatedTv.text = catFactUpdated
    }

    private fun fetchCatFactById(catFactId: String) {
        Log.d("CatFactDetailsFragment", "fetchCatFact id: $catFactId")
        if (catFactId.isNotBlank()) {
            viewModel.getCatFactById(catFactId)
        } else {
            Toast.makeText(context, "Incorrect CatFact Id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar() {
        binding.catFactDetailsProgressBar.visibility = View.VISIBLE
        binding.scrollView.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.catFactDetailsProgressBar.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}