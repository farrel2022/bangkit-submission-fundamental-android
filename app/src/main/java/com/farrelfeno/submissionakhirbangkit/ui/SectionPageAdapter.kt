package com.farrelfeno.submissionakhirbangkit.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPageAdapter (activity: FragmentActivity, private val usersname: String?) : FragmentStateAdapter(activity){
    override fun createFragment(position: Int): Fragment {
    return FollowersFollowingFragment.newInstance(position, usersname)
    }

    override fun getItemCount(): Int {
        return 2
    }
}