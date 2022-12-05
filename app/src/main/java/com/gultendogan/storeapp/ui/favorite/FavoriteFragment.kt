package com.gultendogan.storeapp.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.gultendogan.storeapp.domain.mapper.ProductEntityMapper
import com.gultendogan.storeapp.data.entity.Products
import com.gultendogan.storeapp.databinding.FragmentFavoriteBinding
import com.gultendogan.storeapp.ui.adapter.FavoriteAdapter
import com.gultendogan.storeapp.ui.adapter.FavoriteItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var favAdapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val viewModel : FavoriteViewModel by viewModels()
    private val binding get() = _binding!!

    val mapper = ProductEntityMapper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getAllFavoriteFromRoom()
        observe()
    }

    private fun initRecycler(){
        binding.favRecycler.apply {
            favAdapter = FavoriteAdapter(object : FavoriteItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(products: Products) {
                    viewModel.deleteFavorite(products.id)
                    viewModel.getAllFavoriteFromRoom()
                    observe()
                    favAdapter.notifyDataSetChanged()
                }
            })

            this.layoutManager = GridLayoutManager(context,2)
            adapter = favAdapter
        }
    }

    private fun observe(){
        lifecycleScope.launchWhenCreated {
            viewModel.favList.observe(viewLifecycleOwner){
                viewModel.progressBar.postValue(true)
                val productList: List<Products> = mapper.fromEntityList(it)
                favAdapter.product = productList
                viewModel.progressBar.postValue(false)

            }

            viewModel.progressBar.observe(viewLifecycleOwner){
                if (it){
                    binding.favRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.favRecycler.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}