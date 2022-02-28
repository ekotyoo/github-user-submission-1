package com.example.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentListUserBinding


class ListUserFragment : Fragment() {
    private var binding: FragmentListUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvUsers?.setHasFixedSize(true)
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
            dataAvatar.recycle()

            return listUsers
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showRecyclerList() {
        binding?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
        val listUserAdapter = ListUserAdapter(listUsers)
        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val mBundle = Bundle().apply {
                    putParcelable(UserDetailFragment.EXTRA_USER, user)
                }

                val mFragmentManager = parentFragmentManager
                val mUserDetailFragment = UserDetailFragment().apply {
                    arguments = mBundle
                }

                mFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .replace(
                        R.id.fragment_container,
                        mUserDetailFragment,
                        UserDetailFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding?.rvUsers?.adapter = listUserAdapter
    }
}