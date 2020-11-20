package com.example.mygithub.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import com.example.mygithub.api.MyGitHubApi
import com.example.mygithub.model.UserFollowResponse
import retrofit2.HttpException
import java.io.IOException

private const val FOLLOWERS_STARTING_PAGE_INDEX = 1

class FollowersPagingSource(
    private val myGitHubApi: MyGitHubApi,
    private val query: String
) : PagingSource<Int, UserFollowResponse.UserFollowResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserFollowResponse.UserFollowResponseItem> {
        val position = params.key ?: FOLLOWERS_STARTING_PAGE_INDEX

        return try {
            val response = myGitHubApi.getUserFollowers(query, perPage = 30, page = position)
            Log.d("fuck", response.size.toString())
            LoadResult.Page(
                data = response,
                prevKey = if (position == FOLLOWERS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}