package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.githubuser.databinding.FragmentUserDetailBinding
import com.squareup.picasso.Picasso

class UserDetailFragment : Fragment(), View.OnClickListener {
    private var user: User? = null
    private var binding: FragmentUserDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            user = arguments?.getParcelable(EXTRA_USER)
            binding?.apply {
                tvName.text = user?.name
                tvUsername.text = getString(R.string.username, user?.username)
                user?.avatar?.let { image ->
                    Picasso.get().load(image).into(ivAvatar)
                }
                tvRepositoryCount.text = user?.repository.toString()
                tvFollowersCount.text = user?.followers.toString()
                tvFollowingCount.text = user?.following.toString()
                tvLocation.text = user?.location
                tvCompany.text = user?.company
                btnBack.setOnClickListener(this@UserDetailFragment)
                btnShare.setOnClickListener(this@UserDetailFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_back -> activity?.onBackPressed()
            R.id.btn_share -> shareGithub(user?.username)
        }
    }

    private fun shareGithub(username: String?) {
        val intent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://github.com/$username")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}