package org.fossasia.susi.ai.skills.skillsearch.contract

import org.fossasia.susi.ai.dataclasses.FoundSkills

interface ISkillsSearchView {

    fun visibilityProgressBar(boolean: Boolean)

    fun updateAdapter(skills: FoundSkills)

    fun showEmptySkillsListMessage()

    fun displayError()
}