package com.gultendogan.storeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gultendogan.storeapp.R
import com.gultendogan.storeapp.data.entity.Products
import com.gultendogan.storeapp.databinding.HomeRecyclerItemBinding

class HomeAdapter(private val listener: ItemClickListener) :RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){
    class MyViewHolder(val binding: HomeRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){
    }
    object HomeDiffCallback: DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

    }

    private val diffList = AsyncListDiffer(this,HomeDiffCallback)
    var product: List<Products>
        get()=diffList.currentList
        set(value)=diffList.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(product){
                binding.tvTitle.text = product[position].title
                binding.tvPrice.text = product[position].price.toString()
                Glide.with(binding.ivImage)
                    .load(product[position].image)
                    .into(binding.ivImage)
                if(product[position].isFav==true){
                    binding.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                }else{
                    binding.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
            binding.addButton.setOnClickListener {
                listener.onItemClick(product[position])
            }
            binding.favButton.setOnClickListener {
                listener.favOnItemClick(product[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return product.size
    }
}