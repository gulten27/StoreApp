package com.gultendogan.storeapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gultendogan.storeapp.data.entity.Products
import com.gultendogan.storeapp.databinding.CartRecyclerItemBinding

class CartAdapter(private val listener: CartItemClickListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    class CartViewHolder(val binding: CartRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    object CartDiffCallback: DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

    }

    private val diffList = AsyncListDiffer(this,CartDiffCallback)
    var product: List<Products>
        get()=diffList.currentList
        set(value)=diffList.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        with(holder){
            binding.tvTitle.text = product[position].title
            binding.tvPrice.text = "$"+product[position].price.toString()
            Glide.with(binding.ivCart)
                .load(product[position].image)
                .into(binding.ivCart)


            binding.deleteButton.setOnClickListener {
                listener.onItemClick(product[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

}