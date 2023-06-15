package com.example.batikkita.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.batikkita.api.ApiConfig
import com.example.batikkita.response.BatikDetailResponse
import com.example.batikkita.response.Batik
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel() : ViewModel() {

    val batik = MutableLiveData<Batik>()

    fun setBatikDetail(name: String){
        ApiConfig.getApiService().findBatikDetail(name).enqueue(object : Callback<BatikDetailResponse> {
            override fun onResponse(
                call: Call<BatikDetailResponse>,
                response: Response<BatikDetailResponse>
            ) {
                if (response.isSuccessful){
                    batik.postValue(response.body()?.data)
                }
            }

            override fun onFailure(call: Call<BatikDetailResponse>, t: Throwable) {
                Log.e(TAG,"onFailure: ${t.message}")
            }

        })
    }

    fun getBatikDetail(): LiveData<Batik>{
        return batik
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}