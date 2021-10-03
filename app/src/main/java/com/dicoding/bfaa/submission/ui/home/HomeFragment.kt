package com.dicoding.bfaa.submission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.submission.R
import com.dicoding.bfaa.submission.databinding.FragmentHomeBinding
import com.dicoding.bfaa.submission.helper.OnItemClickCallback
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.ui.adapter.UserAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: UserAdapter
    private lateinit var searchView: SearchView

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchView

        adapter = UserAdapter()

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

        showPopup()
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

    private fun showPopup() {
        val btnMenu = binding.btnMenu
        btnMenu.setOnClickListener {
            val popupMenu = PopupMenu(this.context, btnMenu)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favorite -> {
                        view?.findNavController()
                            ?.navigate(R.id.action_nav_home_to_favoriteFragment)
                        Toast.makeText(requireContext(), "Favorite", Toast.LENGTH_SHORT).show()
                    }
                    R.id.settings -> {
                        view?.findNavController()
                            ?.navigate(R.id.action_nav_home_to_settingsActivity)
                        Toast.makeText(requireContext(), "Settings", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            lottie.visibility = View.GONE
        }
    }
}