package code.namanbir.gitfit.app.data.repository

import code.namanbir.gitfit.app.data.network.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUser(user : String) = apiHelper.getUser(user)

    suspend fun getRepo(user: String) = apiHelper.getRepo(user)
}