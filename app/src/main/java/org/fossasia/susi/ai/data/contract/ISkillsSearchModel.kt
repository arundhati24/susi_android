package org.fossasia.susi.ai.data.contract

import org.fossasia.susi.ai.rest.responses.susi.SkillsSearchResponse
import retrofit2.Response

/**
 *
 * Created by arundhati24 on 29/07/2018.
 */
interface ISkillsSearchModel {
    interface OnFetchSkillsFinishedListener {
        fun onSkillFetchSuccess(response: Response<SkillsSearchResponse>)
        fun onSkillFetchFailure(t: Throwable)
    }

    fun fetchSkills(q: String?, listener: OnFetchSkillsFinishedListener)

    fun cancelFetch()
}