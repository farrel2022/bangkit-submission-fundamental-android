package com.farrelfeno.submissionakhirbangkit.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farrelfeno.submissionakhirbangkit.databinding.FragmentFollowersFollowingBinding
import com.farrelfeno.submissionakhirbangkit.response.ItemsItem

class FollowersFollowingFragment : Fragment() {
    private lateinit var binding : FragmentFollowersFollowingBinding
    private var fragmentviewmodel = FollowersFollowingViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setUpFollow(users: ArrayList<ItemsItem>){
        val adapter = MainAdapter()
        binding.rvuser.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvuser.adapter = adapter
        adapter.submitList(users)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = requireArguments().getInt(ARG_ITEM)
        val usersname = requireArguments().getString(ARG_USERSNAME)

        fragmentviewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersFollowingViewModel::class.java]
        if (item == 0) {
            usersname?.let { it ->
                fragmentviewmodel.setListFollower(it)
                showLoading(true)
                fragmentviewmodel.listFollowers.observe(viewLifecycleOwner){
                    if(it != null){
                        showLoading(false)
                        setUpFollow(it)
                        Log.d("Failure", it.toString())
                    }
                }
                }
            } else {
            usersname?.let { it ->
                fragmentviewmodel.setListFollowing(it)
                showLoading(true)
                fragmentviewmodel.listFollowing.observe(viewLifecycleOwner){
                    if(it != null){
                        showLoading(false)
                        setUpFollow(it)
                        Log.d("Failure", it.toString())
                    }
                }
            }
            }
        fragmentviewmodel.isonLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    companion object {
        const val ARG_ITEM = "item"
        const val ARG_USERSNAME = "usersname"

        fun newInstance(item: Int, usersname: String?) : FollowersFollowingFragment {
            val fragment  = FollowersFollowingFragment()
            val argument = Bundle()
            argument.putInt(ARG_ITEM, item)
            argument.putString(ARG_USERSNAME, usersname)
            fragment.arguments = argument
            return fragment
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}
