package org.fossasia.susi.ai.skills.skilldetails

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.fossasia.susi.ai.rest.responses.susi.GetSkillRatingResponse
import org.fossasia.susi.ai.rest.responses.susi.Stars
import org.fossasia.susi.ai.rest.services.SusiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 *
 *  Created by arundhati24 on 10/06/18
 */
class GetSkillRatingRequest {

    companion object {
        lateinit var interceptor: HttpLoggingInterceptor
        lateinit var client: OkHttpClient
        lateinit var retrofit: Retrofit
        var starsObject: Stars = Stars()
        val BASE_URL: String = "https://api.susi.ai/"

        /**
         * Sends the user rating to the server via "cms/getSkillRating.json/" API
         *
         * @param model : e.g. general
         * @param group : Skill group e.g. Knowledge
         * @param language : Language directory in which the skill resides in the susi_skill_data repo e.g. en
         * @param skillTag : Tells which skill has been rated inside the skills object
         *
         */
        fun getSkillRating(model: String, group: String, language: String, skillTag: String) {
            interceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            client = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
            }.build()

            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

            val service: SusiService = retrofit.create(SusiService::class.java)
            service.getSkillRating(model, group, language, skillTag)
                    .enqueue(object : Callback<GetSkillRatingResponse> {
                        override fun onResponse(call: Call<GetSkillRatingResponse>?,
                                                response: Response<GetSkillRatingResponse>?) {
                            if (response!!.isSuccessful) {
                                Timber.d("Request successful")
                                    starsObject = response.body().skillRating?.stars!!
                                    Timber.d("total_star = %s", starsObject.totalStar)
                            }
                        }

                        override fun onFailure(call: Call<GetSkillRatingResponse>?, t: Throwable?) {
                            Timber.d("Request failed")
                        }
                    })
        }

        fun getRating(model: String, group: String, language: String, skillTag: String): Stars{
            getSkillRating(model, group, language, skillTag)
            return this.starsObject
        }
    }
}