package com.aufa.githubuserapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aufa.githubuserapp.adapter.ListUserAdapter
import com.aufa.githubuserapp.data.ItemsItem
import com.aufa.githubuserapp.databinding.FragmentFollowingBinding
import com.aufa.githubuserapp.model.DetailViewModel

class FollowingFragment : Fragment() {
    private val detailViewModel: DetailViewModel by activityViewModels()

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.dataFollowing.observe(viewLifecycleOwner) { listFollowing ->
            binding.followingList.layoutManager = LinearLayoutManager(requireActivity())
            setListFollowers(listFollowing)
        }
    }

    private fun setListFollowers(dataFollowers: List<ItemsItem>) {
        binding.followingList.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ListUserAdapter(dataFollowers)
        binding.followingList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}