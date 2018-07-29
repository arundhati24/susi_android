package org.fossasia.susi.ai.dataclasses

/**
 *
 * Created by arundhati24 on 29/07/2018.
 */
data class SkillsSearchQuery(
        val q: String? = "",
        val applyFilter: String = "",
        val filterName: String = "",
        val filterType: String = ""
)