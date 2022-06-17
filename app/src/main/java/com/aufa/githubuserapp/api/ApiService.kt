package com.aufa.githubuserapp.api

import com.aufa.githubuserapp.data.DetailUserResponse
import com.aufa.githubuserapp.data.ItemsItem
import com.aufa.githubuserapp.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchResult(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{login}")
    fun getUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("users/{login}/followers")
    fun getListFollowers(
        @Path("login") login: String
    ): Call<List<ItemsItem>>

    @GET("users/{login}/following")
    fun getListFollowing(
        @Path("login") login: String
    ): Call<List<ItemsItem>>
}