package code.namanbir.gitfit.app.data.network

import code.namanbir.gitfit.app.data.model.RepoCountResponse
import code.namanbir.gitfit.app.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("users/{user}/repos")
    suspend fun getRepos(
        @Path("user") user : String
    ) : RepoCountResponse

    @GET("users/{user}")
    suspend fun getUser(
        @Path("user") user : String
    ) : UserResponse

}