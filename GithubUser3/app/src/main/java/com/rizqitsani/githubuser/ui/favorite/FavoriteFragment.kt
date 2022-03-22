package com.rizqitsani.githubuser.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqitsani.githubuser.databinding.FragmentFavoriteBinding
import com.rizqitsani.githubuser.domain.models.User
import com.rizqitsani.githubuser.ui.favorite.adapter.ListFavoriteAdapter


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val viewModel: FavoriteViewModel by activityViewModels {
        FavoriteViewModelFactory(
            requireActivity().application
        )
    }

    private lateinit var adapter: ListFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)

        adapter = ListFavoriteAdapter()
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(activity)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllFavorites().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding?.tvPlaceholder?.visibility = View.GONE
                adapter.setListFavorites(it)
                adapter.setOnItemClickCallback(object : ListFavoriteAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        val toUserDetailFragment =
                            FavoriteFragmentDirections.actionFavoriteFragmentToUserDetailFragment(
                                data
                            )
                        view.findNavController().navigate(toUserDetailFragment)
                    }
                })
            } else {
                binding?.tvPlaceholder?.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}