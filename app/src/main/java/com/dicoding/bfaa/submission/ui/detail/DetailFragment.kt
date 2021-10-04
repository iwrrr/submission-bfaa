package com.dicoding.bfaa.submission.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.bfaa.submission.R
import com.dicoding.bfaa.submission.databinding.FragmentDetailBinding
import com.dicoding.bfaa.submission.helper.ViewModelFactory
import com.dicoding.bfaa.submission.helper.loadImage
import com.dicoding.bfaa.submission.ui.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

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

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel = obtainViewModel(activity as AppCompatActivity)

        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        getDetailUser()

        setViewPager()

        viewModel.user.observe(viewLifecycleOwner, { user ->
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

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun getDetailUser() {
        val id = args.user.id
        val username = args.user.username
        val avatar = args.user.avatar
        val url = args.user.url
        var isChecked = false

        viewModel.setDetailUser(username)
        viewModel.user.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding.apply {
                    tvUsername.text = user.username
                    tvName.text = user.name ?: getString(R.string.empty_data)
                    tvLocation.text = user.location ?: getString(R.string.empty_data)
                    tvCompany.text = user.company ?: getString(R.string.empty_data)
                    tvTotalRepo.text = user.repository.toString()
                    tvTotalFollowers.text = user.followers.toString()
                    tvTotalFollowing.text = user.following.toString()
                    ivPhoto.loadImage(user.avatar)
                }
            }
        })

        lifecycleScope.launch(Dispatchers.IO) {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count > 0) {
                    binding.toggleFav.isChecked = true
                    isChecked = true
                } else {
                    binding.toggleFav.isChecked = false
                    isChecked = false
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(id, username, avatar, url)
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFav.isChecked = isChecked
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        showProfile(isLoading)
        showTabs(isLoading)
    }

    private fun showProfile(isLoading: Boolean) {
        binding.apply {
            tvUsername.visibility = if (isLoading) View.GONE else View.VISIBLE
            toggleFav.visibility = if (isLoading) View.GONE else View.VISIBLE
            btnShare.visibility = if (isLoading) View.GONE else View.VISIBLE
            ivPhoto.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvName.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvLocation.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvCompany.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvTotalRepo.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvTotalFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvTotalFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvRepo.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun showTabs(isLoading: Boolean) {
        binding.apply {
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