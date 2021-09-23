package com.dicoding.bfaa.submission.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.submission.adapter.UserAdapter
import com.dicoding.bfaa.submission.databinding.FragmentHomeBinding
import com.dicoding.bfaa.submission.entity.User
import com.dicoding.bfaa.submission.util.OnItemClickCallback
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvUsers: RecyclerView
    private lateinit var userAdapter: UserAdapter

    private val list = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        list.clear()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUser()
        showList()
    }

    private fun showList() {
        rvUsers = binding.rvUsers
        rvUsers.setHasFixedSize(true)
        userAdapter = UserAdapter(list)

        rvUsers.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = userAdapter
        }

        userAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data)
                view?.findNavController()?.navigate(action)
            }
        })
    }

    private fun loadJSONFromAssets(): String {
        val json: String?

        try {
            val inputStream: InputStream = requireActivity().assets.open("githubuser.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }

        return json
    }

    private fun setUser() {
        try {
            val obj = JSONObject(loadJSONFromAssets())
            val usersArray = obj.getJSONArray("users")

            for (i in 0 until usersArray.length()) {
                val user = usersArray.getJSONObject(i)
                val username = user.getString("username")
                val name = user.getString("name")
                val avatar = user.getString("avatar")
                val company = user.getString("company")
                val location = user.getString("location")
                val repository = user.getInt("repository")
                val followers = user.getInt("followers")
                val following = user.getInt("following")

                val resourceId = resources.getIdentifier(avatar, "drawable", this.requireActivity().packageName)

                val users = User(
                    username = username,
                    name = name,
                    avatar = resourceId,
                    company = company,
                    location = location,
                    repository = repository,
                    followers = followers,
                    following = following
                )

                list.add(users)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}