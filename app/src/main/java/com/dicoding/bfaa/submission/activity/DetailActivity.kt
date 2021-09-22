package com.dicoding.bfaa.submission.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bfaa.submission.databinding.ActivityDetailBinding
import com.dicoding.bfaa.submission.entity.User
import com.dicoding.bfaa.submission.util.loadImage

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        binding.btnShare.setOnClickListener {
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
            ivPhoto.loadImage(user.avatar)
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}