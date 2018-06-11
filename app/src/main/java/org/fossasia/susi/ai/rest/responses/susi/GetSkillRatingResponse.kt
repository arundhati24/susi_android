package org.fossasia.susi.ai.rest.responses.susi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *
 * Created by arundhati24 on 10/06/2018
 */
class GetSkillRatingResponse {

    @SerializedName("session")
    @Expose
    val session: Session? = null

    @SerializedName("accepted")
    @Expose
    val accepted: Boolean = false

    @SerializedName("skill_name")
    @Expose
    val skillName: String = ""

    @SerializedName("message")
    @Expose
    val message: String = ""

    @SerializedName("skill_rating")
    @Expose
    val skillRating: SkillRating? = null

}