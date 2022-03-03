package com.rizqitsani.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rizqitsani.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.imgAvatar.setImageResource(user.avatar)
        binding.tvName.text = user.name
        binding.tvUsername.text = "u/${user.username}, ${user.location}"
        binding.tvCompany.text = "Company: ${user.company}"
        binding.tvDetail.text =
            "Repo Count: ${user.repository}\nFollowers: ${user.follower}\nFollowing: ${user.following}"

        supportActionBar?.title = user.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}