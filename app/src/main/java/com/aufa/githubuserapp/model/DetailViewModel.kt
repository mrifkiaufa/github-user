package com.aufa.githubuserapp.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aufa.githubuserapp.api.ApiConfig
import com.aufa.githubuserapp.data.DetailUserResponse
import com.aufa.githubuserapp.data.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(username: String) : ViewModel() {

    private val _dataUser = MutableLiveData<DetailUserResponse>()
    val dataUser: LiveData<DetailUserResponse> = _dataUser

    private val _dataFollowers = MutableLiveData<List<ItemsItem>>()
    val dataFollowers: LiveData<List<ItemsItem>> = _dataFollowers

    private val _dataFollowing = MutableLiveData<List<ItemsItem>>()
    val dataFollowing: LiveData<List<ItemsItem>> = _dataFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        detailUser(username)
        listFollowers(username)
        listFollowing(username)
    }

    private fun detailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _dataUser.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun listFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _dataFollowers.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun listFollowing(username: String) {
        //showLoading(true)
        val client = ApiConfig.getApiService().getListFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                //showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _dataFollowing.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                //showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}