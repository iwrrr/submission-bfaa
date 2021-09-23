package com.dicoding.bfaa.submission.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dicoding.bfaa.submission.databinding.FragmentDetailBinding
import com.dicoding.bfaa.submission.util.loadImage

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetailUser()

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val user = args.user
        binding.btnShare.setOnClickListener {
            val intent = Intent().apply {
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
    }

    private fun getDetailUser() {
        val user = args.user
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
}