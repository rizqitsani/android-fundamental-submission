package com.rizqitsani.githubuser.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rizqitsani.githubuser.R
import com.rizqitsani.githubuser.databinding.FragmentUserDetailBinding
import com.rizqitsani.githubuser.domain.models.UserDetail
import com.rizqitsani.githubuser.ui.userdetail.adapter.SectionsPagerAdapter

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataUser = UserDetailFragmentArgs.fromBundle(arguments as Bundle).user

        viewModel.getUserDetail(dataUser.login)
        viewModel.getFollowers(dataUser.login)
        viewModel.getFollowing(dataUser.login)

        viewModel.userDetail.observe(viewLifecycleOwner) {
            setUserDetailData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        Glide.with(this)
            .load(dataUser.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(binding.imgAvatar)

        binding.tvUsername.text = dataUser.login
    }

    private fun setUserDetailData(userData: UserDetail) {
        Glide.with(this)
            .load(userData.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(binding.imgAvatar)

        binding.tvUsername.text = userData.login
        binding.tvFollowerCount.text = userData.followers.toString()
        binding.tvFollowingCount.text = userData.following.toString()
        binding.tvRepoCount.text = userData.publicRepos.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.imgAvatar.visibility = View.GONE
            binding.tvUsername.visibility = View.GONE
            binding.layoutFollowers.visibility = View.GONE
            binding.tabLayout.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.imgAvatar.visibility = View.VISIBLE
            binding.tvUsername.visibility = View.VISIBLE
            binding.layoutFollowers.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}