package org.fossasia.susi.ai.rest.responses.susi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SkillsSearchResponse(
        @SerializedName("filteredData")
        @Expose
        val foundSkills: List<SkillData> = ArrayList()
)