package com.example.recipe_app_firebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe_app_firebase.databinding.ItemRowBinding

class RVAdapter (private val activity: MainActivity) :RecyclerView.Adapter<RVAdapter.ItemViewHolder>(){
    private var recipe = emptyList<Recipe>()
    class ItemViewHolder(val binding: ItemRowBinding ):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val re = recipe[position]

        holder.binding.apply {
            tvTitle.text= re.title
           tvAuthor.text=re.author
           tvIngredents.text=re.ingredents
           tvInstructions.text=re.instruction
        }
    }
    override fun getItemCount()=recipe.size

    fun update(recipe:List<Recipe>){
        this.recipe=recipe
        notifyDataSetChanged()
    }
}