package com.dicoding.bfaa.submission.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.submission.databinding.FragmentFollowBinding
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.ui.adapter.UserAdapter
import com.dicoding.bfaa.submission.ui.detail.DetailFragment
import com.dicoding.bfaa.submission.util.OnItemClickCallback

class FollowingFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailFragment.EXTRA_USERNAME).toString()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: User) {

            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }

        followingViewModel.setListFollowing(username)
        followingViewModel.listFollowing.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                adapter.setList(user)
                binding.tvNotFound.visibility = if (user.isNotEmpty()) View.GONE else View.VISIBLE
            }
        })

        followingViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}