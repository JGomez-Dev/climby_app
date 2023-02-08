package com.jgomez.discover_presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jgomez.discover_presentation.databinding.FragmentDiscoverTestBinding
import com.jgomez.discover_presentation.viewmodel.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    private var _binding: FragmentDiscoverTestBinding? = null
    private val binding: FragmentDiscoverTestBinding
        get() = _binding!!

    private val viewModel by viewModels<DiscoverViewModel>()

    private var tripsAdapter = TripsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverTestBinding.inflate(layoutInflater)
        initView()
        tripsAdapter = TripsAdapter()
        return binding.root
    }

    private fun initView() {
        binding.RVTrips.adapter = tripsAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.trips.collectLatest { tripState ->
                if (tripState.isLoading) {
                    binding.PBDiscover.visibility = View.VISIBLE
                }
                if (tripState.error.isNotBlank()) {
                    binding.PBDiscover.visibility = View.GONE
                    Toast.makeText(context, tripState.error, Toast.LENGTH_LONG).show()
                }
                tripState.data?.let {
                    binding.PBDiscover.visibility = View.GONE
                    tripsAdapter.setData(it)
                }
            }
        }
    }
}


