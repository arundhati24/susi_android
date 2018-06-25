package org.fossasia.susi.ai.skills.feedback

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feedback.*
import org.fossasia.susi.ai.R
import org.fossasia.susi.ai.rest.responses.susi.GetSkillFeedbackResponse
import org.fossasia.susi.ai.skills.feedback.adapters.recycleradapters.AllReviewsAdapter

/**
 *
 * Created by arundhati24 on 27/06/2018
 */
class FeedbackActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val feedbackResponse = intent.extras.get("feedbackResponse") as GetSkillFeedbackResponse
        if (feedbackResponse != null) {
            if (feedbackResponse.feedbackList != null) {
                val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                rv_all_feedback.setHasFixedSize(true)
                rv_all_feedback.layoutManager = mLayoutManager
                rv_all_feedback.adapter = AllReviewsAdapter(this, feedbackResponse.feedbackList)
            }
        }
    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        finish()
    }
}