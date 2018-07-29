package org.fossasia.susi.ai.skills.skillsearch

import org.fossasia.susi.ai.data.SkillsSearchModel
import org.fossasia.susi.ai.data.contract.ISkillsSearchModel
import org.fossasia.susi.ai.dataclasses.FoundSkills
import org.fossasia.susi.ai.helper.Constant
import org.fossasia.susi.ai.helper.PrefManager
import org.fossasia.susi.ai.rest.responses.susi.SkillData
import org.fossasia.susi.ai.rest.responses.susi.SkillsSearchResponse
import org.fossasia.susi.ai.skills.skillsearch.contract.ISkillSearchPresenter
import org.fossasia.susi.ai.skills.skillsearch.contract.ISkillsSearchView
import retrofit2.Response
import timber.log.Timber

/**
 *
 * Created by arundhati24 on 29/07/2018.
 */
class SkillsSearchPresenter(val skillsSearchFragment: SkillsSearchFragment) : ISkillSearchPresenter,
        ISkillsSearchModel.OnFetchSkillsFinishedListener {
    private var skillsSearchModel: ISkillsSearchModel = SkillsSearchModel()
    private var skillsSearchView: ISkillsSearchView? = null
    private var skills = FoundSkills(ArrayList())

    override fun onAttach(skillsSearchView: ISkillsSearchView) {
        this.skillsSearchView = skillsSearchView
    }

    override fun getSkills(q: String?) {
        if (!q.isNullOrEmpty()) {
            skillsSearchView?.visibilityProgressBar(true)
            skillsSearchModel.fetchSkills(q, this)
        }
    }

    override fun onSkillFetchSuccess(response: Response<SkillsSearchResponse>) {
        skillsSearchView?.visibilityProgressBar(false)
        if (response.isSuccessful && response.body() != null) {
            Timber.d("Searched skills fetched")
            val responseSkillList = response.body().foundSkills
            if (responseSkillList != null && responseSkillList.isNotEmpty()) {
                skills.foundSkills.clear()
                skills.foundSkills = responseSkillList as MutableList<SkillData>
                skillsSearchFragment.visibilityProgressBar(false)
                skillsSearchView?.updateAdapter(skills)
            } else {
                skillsSearchView?.showEmptySkillsListMessage()
            }
        } else {
            Timber.d("Searched skills not fetched")
            skillsSearchView?.visibilityProgressBar(false)
            skillsSearchView?.displayError()
        }
    }

    override fun onSkillFetchFailure(t: Throwable) {
        skillsSearchView?.visibilityProgressBar(false)
        skillsSearchView?.displayError()
    }

    override fun onDetach() {
        skillsSearchModel.cancelFetch()
        skillsSearchView = null
    }
}