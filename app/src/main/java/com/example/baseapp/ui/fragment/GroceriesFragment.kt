package com.example.baseapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.baseapp.R
import com.example.baseapp.databinding.FragmentGroceriesBinding
import com.example.baseapp.ui.adapter.GroceryAdapter
import com.example.baseapp.util.InjectorUtils
import com.example.baseapp.viewmodel.GroceryViewModel
import com.example.baseapp.vo.Resource.Status

class GroceriesFragment : Fragment(R.layout.fragment_groceries) {
    private val viewModel: GroceryViewModel by viewModels {
        InjectorUtils.provideGroceriesViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGroceriesBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        binding.recyclerViewRepos.adapter = GroceryAdapter()

        viewModel.groceries.observe(viewLifecycleOwner, Observer {
            if (it.state.status == Status.SUCCESS) {
                (binding.recyclerViewRepos.adapter as GroceryAdapter).submitList(it.data!!)
            }
        })

        return binding.root
    }
}
