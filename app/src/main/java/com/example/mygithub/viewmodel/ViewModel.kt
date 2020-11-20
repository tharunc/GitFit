package com.example.mygithub.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mygithub.model.UserProfileResponse
import com.example.mygithub.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val getUserProfileResponse: MutableLiveData<Response<UserProfileResponse>> = MutableLiveData()

    fun setUserProfile(userName: String) {
        viewModelScope.launch {
            val userProfileResponse = repository.getUserProfile(userName)
            getUserProfileResponse.value = userProfileResponse
        }
    }

    private val userNameQuery = MutableLiveData<String>()

    fun setUserNameQuery(query: String) {
        userNameQuery.value = query
    }

    val getUserFollowers =
        userNameQuery.switchMap {
            repository.getUserFollowers(it).cachedIn(viewModelScope)
        }

    val getUserFollowings =
        userNameQuery.switchMap {
            repository.getUserFollowings(it).cachedIn(viewModelScope)
        }

    val getUserRepo =
        userNameQuery.switchMap {
            repository.getUserRepo(it).cachedIn(viewModelScope)
        }
}