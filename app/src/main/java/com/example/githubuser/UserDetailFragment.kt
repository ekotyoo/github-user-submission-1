package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class UserDetailFragment : Fragment(R.layout.fragment_user_detail), View.OnClickListener {
    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var ivAvatar: ImageView
    private lateinit var tvRepositoryCount: TextView
    private lateinit var tvFollowersCount: TextView
    private lateinit var tvFollowingCount: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvCompany: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnShare: Button
    private var user: User? = null

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvName = view.findViewById(R.id.tv_name)
        tvUsername = view.findViewById(R.id.tv_username)
        ivAvatar = view.findViewById(R.id.iv_avatar)
        tvRepositoryCount = view.findViewById(R.id.tv_repository_count)
        tvFollowersCount = view.findViewById(R.id.tv_followers_count)
        tvFollowingCount = view.findViewById(R.id.tv_following_count)
        tvLocation = view.findViewById(R.id.tv_location)
        tvCompany = view.findViewById(R.id.tv_company)
        btnBack = view.findViewById(R.id.btn_back)
        btnShare = view.findViewById(R.id.btn_share)

        if (arguments != null) {
            user = arguments?.getParcelable(EXTRA_USER)
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
        }

        btnBack.setOnClickListener(this)
        btnShare.setOnClickListener(this)
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
}