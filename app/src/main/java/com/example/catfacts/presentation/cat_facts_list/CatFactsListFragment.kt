package com.example.catfacts.presentation.cat_facts_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.R
import com.example.catfacts.common.Constants
import com.example.catfacts.databinding.FragmentCatFactsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatFactsListFragment : Fragment() {
    private var _binding: FragmentCatFactsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by hiltNavGraphViewModels<CatFactsListViewModel>(R.id.nav_graph)
    private lateinit var navController: NavController
    private lateinit var catFactsListAdapter: CatFactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentCatFactsListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        if(viewModel.catFactsListState.value == CatFactsState()){
            fetchRandomCatFacts()
        }
        collectRandomCatFacts()
        onRefreshBtnClick()
    }

    private fun onRefreshBtnClick() {
        binding.refreshBtn.setOnClickListener {
            viewModel.getRandomCatFacts(Constants.AMOUNT_OF_FACTS)
        }
    }

    private fun initViews() {
        val rvFactsList = binding.catFactsListRv
        catFactsListAdapter = CatFactsListAdapter()
        catFactsListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        rvFactsList.adapter = catFactsListAdapter
        rvFactsList.layoutManager = LinearLayoutManager(context)
    }

    private fun collectRandomCatFacts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.catFactsListState.collect { state ->
                    Log.d("CatFactsListFragment", "collectState: ${state.toString()}")
                    if (state.isLoading) {
                        showProgressBar()
                    }
                    if (state.error.isNotBlank()) {
                        Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                    } else if (state.catFacts.isNotEmpty()) {
                        catFactsListAdapter.updateCatFactsList(state.catFacts)
                        hideProgressBar()
                    }
                }
            }
        }
    }

    private fun fetchRandomCatFacts() {
        viewModel.getRandomCatFacts(Constants.AMOUNT_OF_FACTS)
    }

    private fun showProgressBar() {
        binding.catFactsListProgressBar.visibility = View.VISIBLE
        //    binding.catFactsListRv.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.catFactsListProgressBar.visibility = View.GONE
        //   binding.catFactsListRv.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}