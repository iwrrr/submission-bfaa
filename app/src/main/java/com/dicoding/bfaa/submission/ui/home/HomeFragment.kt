package com.dicoding.bfaa.submission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.submission.databinding.FragmentHomeBinding
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.ui.adapter.UserAdapter
import com.dicoding.bfaa.submission.util.OnItemClickCallback

class HomeFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var searchView: SearchView

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchView

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data)
                view.findNavController().navigate(action)
            }
        })

        userViewModel.user.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding.lottie.visibility = View.GONE
                adapter.setList(user)
            } else {
                binding.lottie.visibility = View.VISIBLE
            }
        })

        userViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        searchUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun searchUser() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.isEmpty()) {
                    true
                } else {
                    userViewModel.setSearchUsers(query)
                    true
                }
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            lottie.visibility = View.GONE
        }
    }
}