package org.fossasia.susi.ai.skills.skillsearch.adapters.recycleradapters

import android.content.Context
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import org.fossasia.susi.ai.R
import org.fossasia.susi.ai.dataclasses.FoundSkills
import org.fossasia.susi.ai.rest.responses.susi.SkillData
import org.fossasia.susi.ai.skills.SkillFragmentCallback
import org.fossasia.susi.ai.skills.groupwiseskills.adapters.viewholders.SkillViewHolder

/**
 *
 * Created by arundhati24 on 29/07/2018.
 */
class FoundSkillsAdapter(val context: Context, private val skillDetails: FoundSkills, val skillCallback: SkillFragmentCallback) :
        RecyclerView.Adapter<SkillViewHolder>(), SkillViewHolder.ClickListener {

    private val imageLink = "https://raw.githubusercontent.com/fossasia/susi_skill_data/master/models/general/"
    private val clickListener: SkillViewHolder.ClickListener = this

    @NonNull
    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        if (skillDetails != null) {
            val skillData = skillDetails.foundSkills.get(position)

            if (skillData.skillName == null || skillData.skillName.isEmpty()) {
                holder.skillName?.text = context.getString(R.string.no_skill_name)
            } else {
                holder.skillName?.text = skillData.skillName
            }

            if (skillData.author == null || skillData.author.isEmpty()) {
                holder.skillAuthorName?.text = context.getString(R.string.no_skill_author)
            } else {
                holder.skillAuthorName?.text = skillData.author
            }

            if (skillData.examples == null || skillData.examples.isEmpty())
                holder.skillExample?.text = StringBuilder("\"").append("\"")
            else
                holder.skillExample?.text = StringBuilder("\"").append(skillData.examples[0]).append("\"")

            if (skillData.image == null || skillData.image.isEmpty()) {
                holder.skillImage?.setImageResource(R.drawable.ic_susi)
            } else {
                val imageUrl: String = skillDetails.foundSkills.get(position).group.replace(" ", "%20") + "/en/" + skillData.image
                Picasso.with(context.applicationContext).load(StringBuilder(imageLink)
                        .append(imageUrl).toString())
                        .fit().centerCrop()
                        .error(R.drawable.ic_susi)
                        .into(holder.skillImage)
            }

            if (skillData.skillRating != null) {
                if (skillData.skillRating?.stars != null) {
                    holder.skillRating?.rating = skillData.skillRating?.stars?.averageStar as Float
                    holder.skillTotalRatings?.text = skillData.skillRating?.stars?.totalStar.toString()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return skillDetails.foundSkills.size
    }

    override fun onItemClicked(position: Int) {
        var skillTag: String? = ""
        if (skillDetails.foundSkills[position].skillTag != null) {
            skillTag = skillDetails.foundSkills.toTypedArray().get(position).skillTag
        } else {
            skillTag = ""
        }
        val skillData = skillDetails.foundSkills.get(position)
        val skillGroup = skillDetails.foundSkills.get(position).group.replace(" ", "%20")
        showSkillDetailFragment(skillData, skillGroup, skillTag)
    }

    private fun showSkillDetailFragment(skillData: SkillData, skillGroup: String, skillTag: String) {
        skillCallback.loadDetailFragment(skillData, skillGroup, skillTag)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_group_wise_skill, parent, false)
        return SkillViewHolder(itemView, clickListener)
    }
}