package com.rizqitsani.githubuser.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqitsani.githubuser.databinding.FragmentFollowingBinding
import com.rizqitsani.githubuser.domain.models.User
import com.rizqitsani.githubuser.ui.userdetail.adapter.ListFollowingAdapter

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private val viewModel: UserDetailViewModel by activityViewModels()

    private lateinit var listFollowingAdapter: ListFollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listFollowingAdapter = ListFollowingAdapter()
        val layoutManager = LinearLayoutManager(activity)
        binding?.rvFollowing?.layoutManager = layoutManager
        binding?.rvFollowing?.adapter = listFollowingAdapter

        viewModel.listFollowing.observe(viewLifecycleOwner) {
            setFollowingData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowingData(following: List<User>) {
        listFollowingAdapter.setListFollowing(following)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.rvFollowing?.visibility = View.GONE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.rvFollowing?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}