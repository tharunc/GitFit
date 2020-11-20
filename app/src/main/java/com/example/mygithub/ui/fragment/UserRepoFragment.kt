package com.example.mygithub.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.example.mygithub.R
import com.example.mygithub.adapter.GithubLoadStateAdapter
import com.example.mygithub.adapter.UserRepoAdapter
import com.example.mygithub.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_repo.*

@AndroidEntryPoint
class UserRepoFragment : Fragment(R.layout.fragment_user_repo) {

    companion object {
        const val TAG = "UserRepoFragment"
    }

    private val args: UserRepoFragmentArgs by navArgs()
    private val viewModel by viewModels<ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d(UserFollowersFragment.TAG, "userName : ${args.userName.toString()}")

        val adapter = UserRepoAdapter()
        recycler_view_repo.setHasFixedSize(true)
        recycler_view_repo.itemAnimator = null
        recycler_view_repo.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GithubLoadStateAdapter { adapter.retry() },
            footer = GithubLoadStateAdapter { adapter.retry() },
        )

        button_retry.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener { loadState ->

            progress_bar.isVisible = loadState.source.refresh is LoadState.Loading
            recycler_view_repo.isVisible = loadState.source.refresh is LoadState.NotLoading
            button_retry.isVisible = loadState.source.refresh is LoadState.Error
            text_view_error.isVisible = loadState.source.refresh is LoadState.Error

            // empty view
            if (loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached &&
                adapter.itemCount < 1
            ) {
                recycler_view_repo.isVisible = false
                text_view_empty.isVisible = true
            } else {
                text_view_empty.isVisible = false
            }
        }

        viewModel.setUserNameQuery(args.userName)
        viewModel.getUserRepo.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}