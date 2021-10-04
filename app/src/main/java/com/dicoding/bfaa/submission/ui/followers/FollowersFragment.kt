package com.dicoding.bfaa.submission.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.submission.databinding.FragmentFollowBinding
import com.dicoding.bfaa.submission.helper.OnItemClickCallback
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.ui.adapter.UserAdapter
import com.dicoding.bfaa.submission.ui.detail.DetailFragment

class FollowersFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    private lateinit var binding: FragmentFollowBinding

    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailFragment.EXTRA_USERNAME).toString()

        adapter = UserAdapter()

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: User) {

            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }

        followersViewModel.setListFollowers(username)
        followersViewModel.listFollowers.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                adapter.setList(user)
                binding.tvNotFound.visibility = if (user.isNotEmpty()) View.GONE else View.VISIBLE
            }
        })

        followersViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}