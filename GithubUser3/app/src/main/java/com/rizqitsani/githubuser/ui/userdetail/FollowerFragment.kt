package com.rizqitsani.githubuser.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqitsani.githubuser.databinding.FragmentFollowerBinding
import com.rizqitsani.githubuser.domain.models.User
import com.rizqitsani.githubuser.ui.userdetail.adapter.ListFollowerAdapter

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollower.layoutManager = layoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listFollower.observe(viewLifecycleOwner) {
            setFollowersData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowersData(followers: List<User>) {
        val listFollowerAdapter = ListFollowerAdapter(followers)
        binding.rvFollower.adapter = listFollowerAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvFollower.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvFollower.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}