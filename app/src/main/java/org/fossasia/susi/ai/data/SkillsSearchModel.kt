package org.fossasia.susi.ai.data

import org.fossasia.susi.ai.data.contract.ISkillsSearchModel
import org.fossasia.susi.ai.dataclasses.SkillsSearchQuery
import org.fossasia.susi.ai.rest.ClientBuilder
import org.fossasia.susi.ai.rest.responses.susi.SkillsSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SkillsSearchModel : ISkillsSearchModel {
    private lateinit var authResponseCallSkills: Call<SkillsSearchResponse>

    override fun fetchSkills(q: String?, listener: ISkillsSearchModel.OnFetchSkillsFinishedListener) {
        val queryObject = SkillsSearchQuery(q, "true", "descending", "rating")
        authResponseCallSkills = ClientBuilder.skillsSearchCall(queryObject)

        authResponseCallSkills.enqueue(object : Callback<SkillsSearchResponse> {
            override fun onResponse(call: Call<SkillsSearchResponse>, response: Response<SkillsSearchResponse>) {
                listener.onSkillFetchSuccess(response)
            }

            override fun onFailure(call: Call<SkillsSearchResponse>, t: Throwable) {
                Timber.e(t)
                listener.onSkillFetchFailure(t)
            }
        })
    }

    override fun cancelFetch() {
        try {
            authResponseCallSkills.cancel()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}