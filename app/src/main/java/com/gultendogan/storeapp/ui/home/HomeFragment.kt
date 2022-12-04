package com.gultendogan.storeapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gultendogan.storeapp.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.GridLayoutManager
import com.gultendogan.storeapp.data.entity.Products
import com.gultendogan.storeapp.ui.adapter.ItemClickListener
import com.gultendogan.storeapp.ui.adapter.HomeAdapter
import com.gultendogan.storeapp.R
import com.gultendogan.storeapp.databinding.HomeRecyclerItemBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var homeAdapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getData()
        observe()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        viewModel.getData()
        initRecycler()
        observe()
        homeAdapter.notifyDataSetChanged()
        super.onResume()

    }

    private fun observe(){
        lifecycleScope.launchWhenCreated {
            viewModel.productList.observe(viewLifecycleOwner){
                homeAdapter.product = it
            }
        }

    }

    private fun initRecycler(){
        binding.homeRecycler.apply {
            homeAdapter = HomeAdapter(object : ItemClickListener{
                override fun onItemClick(product: Products) {
                    addRoom(product)
                    Toast.makeText(requireContext(),"Added to cart",Toast.LENGTH_LONG).show()
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun favOnItemClick(product: Products) {
                    if (product.isFav==true){
                        product.isFav=false
                        deleteFavorite(product)
                        homeAdapter.notifyDataSetChanged()
                    }else{
                        product.isFav=true
                        addFavorite(product)
                        homeAdapter.notifyDataSetChanged()
                    }
                }
            })
            this.layoutManager = GridLayoutManager(context,2)
            adapter = homeAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun addRoom(products: Products){
        viewModel.addProduct(products)
    }
    private fun addFavorite(products: Products){
        viewModel.addFavorite(products)
    }

    private fun deleteFavorite(products: Products){
        viewModel.deleteFavorite(products)
    }

}