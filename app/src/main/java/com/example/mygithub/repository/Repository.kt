package com.example.mygithub.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.mygithub.api.MyGitHubApi
import com.example.mygithub.model.UserProfileResponse
import com.example.mygithub.pagingsource.FollowersPagingSource
import com.example.mygithub.pagingsource.FollowingsPagingSource
import com.example.mygithub.pagingsource.RepoPagingSource
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val myGitHubApi: MyGitHubApi) {

    suspend fun getUserProfile(userName: String): Response<UserProfileResponse> {
        return myGitHubApi.getUserProfile(userName)
    }

    fun getUserFollowers(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FollowersPagingSource(
                    myGitHubApi, query
                )
            }
        ).liveData

    fun getUserFollowings(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FollowingsPagingSource(
                    myGitHubApi, query
                )
            }
        ).liveData


    fun getUserRepo(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RepoPagingSource(
                    myGitHubApi, query
                )
            }
        ).liveData
}