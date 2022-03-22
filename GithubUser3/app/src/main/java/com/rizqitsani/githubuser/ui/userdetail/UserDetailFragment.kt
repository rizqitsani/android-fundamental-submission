package com.rizqitsani.githubuser.ui.userdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rizqitsani.githubuser.R
import com.rizqitsani.githubuser.data.database.Favorite
import com.rizqitsani.githubuser.databinding.FragmentUserDetailBinding
import com.rizqitsani.githubuser.domain.models.User
import com.rizqitsani.githubuser.domain.models.UserDetail
import com.rizqitsani.githubuser.ui.userdetail.adapter.SectionsPagerAdapter
import de.hdodenhof.circleimageview.CircleImageView

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding
    private val viewModel: UserDetailViewModel by activityViewModels {
        UserDetailViewModelFactory(
            requireActivity().application
        )
    }

    private lateinit var user: User
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val view = binding?.root

        setHasOptionsMenu(true)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        val viewPager: ViewPager2 = binding?.viewPager as ViewPager2
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding?.tabLayout as TabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = UserDetailFragmentArgs.fromBundle(arguments as Bundle).user

        viewModel.getUserDetail(user.login)
        viewModel.getFollowers(user.login)
        viewModel.getFollowing(user.login)

        viewModel.userDetail.observe(viewLifecycleOwner) {
            setUserDetailData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.status.observe(viewLifecycleOwner) {
            showToast(it)
        }

        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(binding?.imgAvatar as CircleImageView)

        binding?.tvUsername?.text = user.login
    }

    private fun setUserDetailData(userData: UserDetail) {
        Glide.with(this)
            .load(userData.avatarUrl)
            .apply(RequestOptions().override(550, 550))
            .into(binding?.imgAvatar as CircleImageView)

        val details = listOfNotNull(
            userData.name,
            userData.company,
            userData.location
        ).joinToString(separator = " | ")

        binding?.tvUsername?.text = userData.login
        binding?.tvDetails?.text = resources.getString(
            R.string.dummy_details,
            details
        )
        binding?.tvFollowerCount?.text = userData.followers.toString()
        binding?.tvFollowingCount?.text = userData.following.toString()
        binding?.tvRepoCount?.text = userData.publicRepos.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.imgAvatar?.visibility = View.GONE
            binding?.tvUsername?.visibility = View.GONE
            binding?.tvDetails?.visibility = View.GONE
            binding?.layoutFollowers?.visibility = View.GONE
            binding?.tabLayout?.visibility = View.GONE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.imgAvatar?.visibility = View.VISIBLE
            binding?.tvUsername?.visibility = View.VISIBLE
            binding?.tvDetails?.visibility = View.VISIBLE
            binding?.layoutFollowers?.visibility = View.VISIBLE
            binding?.tabLayout?.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_detail_menu, menu)

        viewModel.checkIfExist(user.login).observe(viewLifecycleOwner) { isExist ->
            isFavorite = if (isExist != null) {
                menu.findItem(R.id.menu_favorite).setIcon(R.drawable.ic_baseline_favorite_red_24)
                true
            } else {
                menu.findItem(R.id.menu_favorite).setIcon(R.drawable.ic_baseline_favorite_border_24)
                false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                if (isFavorite) {
                    viewModel.deleteFromFavorites(user.login)
                    showToast(getString(R.string.removed))
                } else {
                    viewModel.insertToFavorites(
                        Favorite(
                            login = user.login,
                            avatarUrl = user.avatarUrl
                        )
                    )
                    showToast(getString(R.string.added))
                }

                true
            }

            else -> true
        }
    }


    private fun showToast(message: String) {
        if (message != "") {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
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