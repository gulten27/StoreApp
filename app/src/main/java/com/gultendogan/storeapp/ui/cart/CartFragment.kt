package com.gultendogan.storeapp.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gultendogan.storeapp.domain.mapper.ProductEntityMapper
import com.gultendogan.storeapp.data.entity.Products
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.gultendogan.storeapp.databinding.FragmentCartBinding
import com.gultendogan.storeapp.ui.adapter.CartAdapter
import com.gultendogan.storeapp.ui.adapter.CartItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var cartAdapter: CartAdapter
    private var _binding: FragmentCartBinding? = null
    private val viewModel : CartViewModel by viewModels()
    private val binding get() = _binding!!
    val mapper = ProductEntityMapper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getAllProductFromRoom()
        observe()
    }

    private fun initRecycler(){
        binding.cartRecycler.apply {
            cartAdapter = CartAdapter(object : CartItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(product: Products) {
                    viewModel.deleteProduct(product.id)
                    viewModel.getAllProductFromRoom()
                    observe()
                    cartAdapter.notifyDataSetChanged()
                }
            })

            this.layoutManager = GridLayoutManager(context,2)
            adapter = cartAdapter
        }
    }

    private fun observe(){
        viewModel.cartList.observe(viewLifecycleOwner){
            val productList: List<Products> = mapper.fromEntityList(it)
            cartAdapter.product = productList
        }
    }
}