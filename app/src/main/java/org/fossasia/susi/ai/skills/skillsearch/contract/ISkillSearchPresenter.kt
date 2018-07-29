package org.fossasia.susi.ai.skills.skillsearch.contract

interface ISkillSearchPresenter {

    fun onAttach(skillsSearchView: ISkillsSearchView)

    fun getSkills(q: String?)

    fun onDetach()
}