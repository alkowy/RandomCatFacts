package com.example.catfacts.presentation.cat_facts_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.R
import com.example.catfacts.databinding.CatFactsListItemRvBinding
import com.example.catfacts.domain.model.CatFactModel

class CatFactsListAdapter  : RecyclerView.Adapter<CatFactsListAdapter.CatFactsListHolder>(){

    private var catFactsList : List<CatFactModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatFactsListHolder {
        val itemBinding = CatFactsListItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatFactsListHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CatFactsListHolder, position: Int) {
        val catFact = catFactsList[position]
        holder.bind(catFact)
    }

    override fun getItemCount(): Int {
       return catFactsList.size
    }

    fun updateCatFactsList(newCatFactsList : List<CatFactModel>){
        catFactsList = newCatFactsList
        notifyDataSetChanged()
    }

    class CatFactsListHolder(private val itemBinding : CatFactsListItemRvBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(catFact: CatFactModel){
            itemBinding.catFactIdTv.text = catFact.id
            itemBinding.root.setOnClickListener {
                Navigation.findNavController(itemBinding.root).navigate(R.id.action_catFactsListFragment_to_catFactDetailsFragment, bundleOf("catFactId" to catFact.id))
            }
        }
    }
}

