package org.fossasia.susi.ai.skills.skillsearch

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_group_wise_skill_listing.*
import org.fossasia.susi.ai.R
import org.fossasia.susi.ai.dataclasses.FoundSkills
import org.fossasia.susi.ai.helper.SimpleDividerItemDecoration
import org.fossasia.susi.ai.helper.StartSnapHelper
import org.fossasia.susi.ai.skills.SkillFragmentCallback
import org.fossasia.susi.ai.skills.skillsearch.adapters.recycleradapters.FoundSkillsAdapter
import org.fossasia.susi.ai.skills.skillsearch.contract.ISkillSearchPresenter
import org.fossasia.susi.ai.skills.skillsearch.contract.ISkillsSearchView
import timber.log.Timber

/**
 *
 * Created by arundhati24 on 29/07/2018.
 */
class SkillsSearchFragment : Fragment(), ISkillsSearchView {
    private lateinit var skillAdapterSnapHelper: SnapHelper
    private lateinit var skillsSearchPresenter: ISkillSearchPresenter
    private var skills = FoundSkills(ArrayList())
    private var q: String? = null
    private lateinit var skillsAdapter: FoundSkillsAdapter
    private lateinit var skillCallback: SkillFragmentCallback

    companion object {
        const val SEARCH_QUERY = "q"
        fun newInstance(q: String?): SkillsSearchFragment {
            val fragment = SkillsSearchFragment()
            val bundle = Bundle()
            bundle.putString(SEARCH_QUERY, q)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val argument = arguments
        if (argument != null) {
            this.q = argument.getString(SEARCH_QUERY)
        }
        return inflater.inflate(R.layout.fragment_group_wise_skill_listing, container, false)
    }

    @NonNull
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = getString(R.string.skills_activity)
        skillsSearchPresenter = SkillsSearchPresenter(this)
        skillsSearchPresenter.onAttach(this)
        setUPAdapter()
        skillsSearchPresenter.getSkills(q)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUPAdapter() {
        skillAdapterSnapHelper = StartSnapHelper()
        val mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        groupWiseSkills.layoutManager = mLayoutManager
        skillsAdapter = FoundSkillsAdapter(requireContext(), skills, skillCallback)
        groupWiseSkills.adapter = skillsAdapter
        groupWiseSkills.onFlingListener = null
        skillAdapterSnapHelper.attachToRecyclerView(groupWiseSkills)
    }

    override fun visibilityProgressBar(boolean: Boolean) {
        progressSkillWait.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    override fun showEmptySkillsListMessage() {
        if (activity != null) {
            swipeRefreshLayout.isRefreshing = false
            groupWiseSkills.visibility = View.GONE
            messageNoSkillsFound.visibility = View.VISIBLE
        }
    }

    override fun displayError() {
        if (activity != null) {
            swipeRefreshLayout.isRefreshing = false
            groupWiseSkills.visibility = View.GONE
            errorSkillFetch.visibility = View.VISIBLE
        }
    }

    override fun updateAdapter(skills: FoundSkills) {
        if (errorSkillFetch.visibility == View.VISIBLE) {
            errorSkillFetch.visibility = View.GONE
        }
        groupWiseSkills.visibility = View.VISIBLE
        skills.foundSkills.clear()
        this.skills.foundSkills.addAll(skills.foundSkills)
        groupWiseSkills.addItemDecoration(SimpleDividerItemDecoration(context, this.skills.foundSkills.size))
        skillsAdapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context);
        if (context is SkillFragmentCallback) {
            skillCallback = context
        } else {
            Timber.e("context is not SkillFragmentCallback")
        }
    }

    override fun onDestroyView() {
        skillsSearchPresenter.onDetach()
        super.onDestroyView()
    }
}