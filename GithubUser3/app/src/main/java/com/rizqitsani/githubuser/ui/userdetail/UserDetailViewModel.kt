package com.rizqitsani.githubuser.ui.userdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqitsani.githubuser.data.database.Favorite
import com.rizqitsani.githubuser.data.network.ApiConfig
import com.rizqitsani.githubuser.data.network.response.FollowerResponse
import com.rizqitsani.githubuser.data.network.response.FollowingResponse
import com.rizqitsani.githubuser.data.network.response.UserDetailResponse
import com.rizqitsani.githubuser.data.repository.FavoriteRepository
import com.rizqitsani.githubuser.domain.models.User
import com.rizqitsani.githubuser.domain.models.UserDetail
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _listFollower = MutableLiveData<List<User>>()
    val listFollower: LiveData<List<User>> = _listFollower

    private val _listFollowing = MutableLiveData<List<User>>()
    val listFollowing: LiveData<List<User>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun getUserDetail(username: String) {
        _isLoading.value = true
        _status.value = ""
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseUserDetail = response.body() as UserDetailResponse
                    val convertedUserDetail = UserDetail(
                        responseUserDetail.login,
                        responseUserDetail.avatarUrl,
                        responseUserDetail.name,
                        responseUserDetail.company,
                        responseUserDetail.location,
                        responseUserDetail.publicRepos,
                        responseUserDetail.followers,
                        responseUserDetail.following
                    )

                    _status.value = ""
                    _userDetail.value = convertedUserDetail
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                _status.value = "Terjadi kesalahan. Mohon coba beberapa saat lagi"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        _status.value = ""
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowerResponse>> {
            override fun onResponse(
                call: Call<List<FollowerResponse>>,
                response: Response<List<FollowerResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseFollowers = response.body()
                    val convertedFollowers = ArrayList<User>()

                    responseFollowers?.forEach {
                        convertedFollowers.add(User(it.login, it.avatarUrl))
                    }

                    _status.value = ""
                    _listFollower.postValue(convertedFollowers)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerResponse>>, t: Throwable) {
                _isLoading.value = false
                _status.value = "Terjadi kesalahan. Mohon coba beberapa saat lagi"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        _status.value = ""
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponse>> {
            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseFollowing = response.body()
                    val convertedFollowing = ArrayList<User>()

                    responseFollowing?.forEach {
                        convertedFollowing.add(User(it.login, it.avatarUrl))
                    }

                    _status.value = ""
                    _listFollowing.postValue(convertedFollowing)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                _isLoading.value = false
                _status.value = "Terjadi kesalahan. Mohon coba beberapa saat lagi"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insertToFavorites(favorite: Favorite) {
        viewModelScope.launch {
            mFavoriteRepository.insert(favorite)
        }
    }

    fun deleteFromFavorites(login: String) {
        viewModelScope.launch {
            mFavoriteRepository.delete(login)
        }
    }

    fun checkIfExist(login: String): LiveData<Favorite> =
        mFavoriteRepository.getFavoriteByLogin(login)

    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}