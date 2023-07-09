package com.foodsoul.test.network.image_search

import com.foodsoul.test.data.image_search.model.ImageResponseModel
import io.reactivex.Observable
import retrofit2.http.*

interface ImageSearchApi {
    @GET(ApiConstants.GET)
    @Headers(
        ApiConstants.CONTENT_TYPE,
        ApiConstants.X_RAPID_API_HOST,
        ApiConstants.X_RAPID_API_KEY
    )
    fun searchImages(@Query(ApiConstants.QUERY) query:String) : Observable<ImageResponseModel>
}