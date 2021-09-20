package com.dicoding.bfaa.submission.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.bfaa.submission.databinding.ActivityDetailBinding
import com.dicoding.bfaa.submission.entity.User

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnShare.setOnClickListener {
            val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
            intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(
                    Intent.EXTRA_TEXT,
                    """
                        Profile Github
                        
                        Username: ${user.username}
                        Name: ${user.name}
                        Company: ${user.company}
                        Location: ${user.location}
                        Total Repository: ${user.repository}
                        Total Followers: ${user.followers}
                        Total Following: ${user.following}
                    """.trimIndent()
                )
                this.type = "text/plain"
            }
            startActivity(intent)
        }

        getDetailUser()
    }

    private fun getDetailUser() {
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.apply {
            tvUsername.text = user.username
            tvName.text = user.name
            tvLocation.text = user.location
            tvCompany.text = user.company
            tvTotalRepo.text = user.repository.toString()
            tvTotalFollowers.text = user.followers.toString()
            tvTotalFollowing.text = user.following.toString()
            Glide.with(this@DetailActivity)
                .load(user.avatar)
                .circleCrop()
                .into(ivPhoto)
        }
    }
}