package com.example.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String?,
    val name: String?,
    val avatar: Int?,
    val company: String?,
    val location: String?,
    val repository: Int?,
    val followers: Int?,
    val following: Int?,
) : Parcelable
