package com.rizqitsani.githubuser.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqitsani.githubuser.R
import com.rizqitsani.githubuser.databinding.FragmentHomeBinding
import com.rizqitsani.githubuser.domain.models.User
import com.rizqitsani.githubuser.ui.home.adapter.ListUserAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listUser.observe(viewLifecycleOwner) {
            setUserListData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUserListData(userList: List<User>) {
        if (userList.isNotEmpty()) {
            binding.tvPlaceholder.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        } else {
            binding.tvPlaceholder.text = resources.getString(R.string.not_found)
            binding.tvPlaceholder.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        }

        val listUserAdapter = ListUserAdapter(userList)
        binding.rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val toUserDetailFragment =
                    HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(data)
                view?.findNavController()?.navigate(toUserDetailFragment)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun searchUser(username: String?) {
        if (username != null) {
            viewModel.searchUser(username)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tvPlaceholder.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}