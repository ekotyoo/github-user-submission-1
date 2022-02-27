package com.example.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ItemRowUserBinding
import com.squareup.picasso.Picasso

class ListUserAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = users[position].name
            tvUsername.text = root.context.getString(R.string.username, users[position].username)
            users[position].avatar?.let { image ->
                Picasso.get().load(image).into(ivAvatar)
            }
            root.setOnClickListener {
                onItemClickCallback.onItemClicked(users[position])
            }
        }
    }

    override fun getItemCount(): Int = users.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}