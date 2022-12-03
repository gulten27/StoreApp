package com.gultendogan.storeapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.gultendogan.storeapp.data.entity.ProductEntity
import com.gultendogan.storeapp.databinding.FragmentDashboardBinding
import com.gultendogan.storeapp.ui.adapter.FavoriteAdapter
import com.gultendogan.storeapp.ui.adapter.FavoriteItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    lateinit var favAdapter: FavoriteAdapter
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel : DashboardViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
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
                override fun onItemClick(productEntity: ProductEntity) {
                    viewModel.deleteFavorite(productEntity.uid)
                    viewModel.getAllFavoriteFromRoom()
                    observe()
                }
            })

            this.layoutManager = GridLayoutManager(context,2)
            adapter = favAdapter
        }
    }

    private fun observe(){
        viewModel.favList.observe(viewLifecycleOwner){
            favAdapter.product = it
            it.forEach {
                println(it.price)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}