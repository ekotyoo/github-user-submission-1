package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListUserFragment : Fragment(R.layout.fragment_list_user) {
    private lateinit var rvUsers: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUsers = view.findViewById(R.id.rv_users)
        rvUsers.setHasFixedSize(true)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        get() {
            val dataUsername = resources.getStringArray(R.array.username)
            val dataName = resources.getStringArray(R.array.name)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)

            val listUsers = ArrayList<User>()

            for (i in dataName.indices) {
                val user = User(
                    dataUsername[i],
                    dataName[i],
                    dataAvatar.getResourceId(i, -1),
                    dataCompany[i],
                    dataLocation[i],
                    dataRepository[i].toInt(),
                    dataFollowers[i].toInt(),
                    dataFollowing[i].toInt()
                )
                listUsers.add(user)
            }
            return listUsers
        }

    private fun showRecyclerList() {
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        val listHeroAdapter = ListUserAdapter(listUsers)
        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val mBundle = Bundle().apply {
                    putParcelable(UserDetailFragment.EXTRA_USER, user)
                }

                val mFragmentManager = parentFragmentManager
                val mUserDetailFragment = UserDetailFragment().apply {
                    arguments = mBundle
                }

                mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, mUserDetailFragment, UserDetailFragment::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            }

        })
        rvUsers.adapter = listHeroAdapter
    }
}