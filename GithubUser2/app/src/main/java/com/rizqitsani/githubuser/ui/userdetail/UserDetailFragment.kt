package com.rizqitsani.githubuser.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rizqitsani.githubuser.R
import com.rizqitsani.githubuser.databinding.FragmentUserDetailBinding
import com.rizqitsani.githubuser.ui.userdetail.adapter.SectionsPagerAdapter

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

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

        Glide.with(view)
            .load(dataUser.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(binding.imgAvatar)

        binding.tvUsername.text = dataUser.login
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}