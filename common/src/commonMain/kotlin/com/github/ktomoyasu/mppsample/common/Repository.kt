package com.github.ktomoyasu.mppsample.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryList(@SerialName("total_count") val count: Int, val items: List<Repository>)

@Serializable
data class Repository(val name: String, val description: String, @SerialName("stargazers_count") val starCount: Int)
