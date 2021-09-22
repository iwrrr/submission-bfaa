package com.dicoding.bfaa.submission.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.submission.adapter.UserAdapter
import com.dicoding.bfaa.submission.databinding.ActivityMainBinding
import com.dicoding.bfaa.submission.entity.User
import com.dicoding.bfaa.submission.util.OnItemClickCallback
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUsers: RecyclerView
    private lateinit var userAdapter: UserAdapter

    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        parseJSON()

        rvUsers = binding.rvUsers
        rvUsers.setHasFixedSize(true)

        showList()
    }

    private fun showList() {
        rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(list)
        rvUsers.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(detailIntent)
            }
        })
    }

    private fun loadJSONFromAssets(): String? {
        val json: String?

        try {
            val inputStream: InputStream = assets.open("githubuser.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return json
    }

    private fun parseJSON() {
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

                val resourceId = resources.getIdentifier(avatar, "drawable", this.packageName)

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