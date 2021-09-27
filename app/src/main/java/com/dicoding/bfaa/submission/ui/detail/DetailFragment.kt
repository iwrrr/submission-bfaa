package com.dicoding.bfaa.submission.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.bfaa.submission.R
import com.dicoding.bfaa.submission.databinding.FragmentDetailBinding
import com.dicoding.bfaa.submission.ui.adapter.SectionPagerAdapter
import com.dicoding.bfaa.submission.util.loadImage
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        getDetailUser()

        setViewPager()

        detailViewModel.user.observe(viewLifecycleOwner, { user ->
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
        })
    }

    private fun setViewPager() {
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, args.user.username)

        val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager, lifecycle, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getDetailUser() {
        val username = args.user.username

        detailViewModel.setDetailUser(username)
        detailViewModel.user.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding.apply {
                    tvUsername.text = user.username
                    tvName.text = user.name ?: getString(R.string.no_name)
                    tvLocation.text = user.location ?: getString(R.string.no_location)
                    tvCompany.text = user.company ?: getString(R.string.no_company)
                    tvTotalRepo.text = user.repository.toString()
                    tvTotalFollowers.text = user.followers.toString()
                    tvTotalFollowing.text = user.following.toString()
                    ivPhoto.loadImage(user.avatar)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvLocation.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvCompany.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvRepo.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
            tabs.visibility = if (isLoading) View.GONE else View.VISIBLE
            viewPager.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )

        const val EXTRA_USERNAME = "extra_username"
    }
}