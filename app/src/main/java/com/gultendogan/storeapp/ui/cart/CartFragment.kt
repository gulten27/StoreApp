package com.gultendogan.storeapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.gultendogan.storeapp.data.entity.ProductEntity
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
                override fun onItemClick(productEntity: ProductEntity) {
                    viewModel.deleteProduct(productEntity.uid)
                    viewModel.getAllProductFromRoom()
                    observe()
                }
            })

            this.layoutManager = GridLayoutManager(context,2)
            adapter = cartAdapter
        }
    }

    private fun observe(){
        viewModel.cartList.observe(viewLifecycleOwner){
            cartAdapter.product = it
            it.forEach {
                println(it.price)
            }
        }
    }
}