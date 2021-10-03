package com.dicoding.bfaa.submission.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.submission.database.FavoriteUser
import com.dicoding.bfaa.submission.databinding.FragmentFavoriteBinding
import com.dicoding.bfaa.submission.helper.OnItemClickCallback
import com.dicoding.bfaa.submission.helper.ViewModelFactory
import com.dicoding.bfaa.submission.model.User
import com.dicoding.bfaa.submission.ui.adapter.UserAdapter

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        adapter = UserAdapter()

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val action = FavoriteFragmentDirections.actionNavFavoriteToNavDetail(data)
                view.findNavController().navigate(action)
            }
        })

        viewModel = obtainViewModel(activity as AppCompatActivity)

        viewModel.getFavoriteUser().observe(viewLifecycleOwner, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
                binding.tvNotFound.visibility = if (list.isNotEmpty()) View.GONE else View.VISIBLE
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.id,
                user.username,
                user.avatar,
                user.url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }
}