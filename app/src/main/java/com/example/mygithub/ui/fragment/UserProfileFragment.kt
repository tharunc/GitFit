package com.example.mygithub.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_profile.*


@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val viewModel by viewModels<ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserProfileResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                progressBar.visibility = View.GONE
                card_view_dp.visibility = View.VISIBLE
                card_view_count.visibility = View.VISIBLE
                scroll_view.visibility = View.VISIBLE

                //Set all data
                Glide.with(requireContext()).load(response.body()?.avatar_url).fitCenter().into(dp)
                user_followers.text = response.body()?.followers.toString()
                user_following.text = response.body()?.following.toString()
                user_repo.text = response.body()?.public_repos.toString()
                user_name.text = response.body()?.name.toString()
                user_bio.text = response.body()?.bio.toString()
                user_email.text = response.body()?.email.toString()
                user_location.text = response.body()?.location.toString()
                user_hireable.text = response.body()?.hireable.toString()
                user_created.text = response.body()?.created_at.toString()
                user_updated.text = response.body()?.updated_at.toString()

                //Details
                followers_box.setOnClickListener {
                    val action =
                        UserProfileFragmentDirections.actionUserProfileFragmentToUserFollowersFragment(
                            response.body()?.login.toString()
                        )
                    findNavController().navigate(action)
                }

                following_box.setOnClickListener {
                    val action =
                        UserProfileFragmentDirections.actionUserProfileFragmentToUserFollowingFragment(
                            response.body()?.login.toString()
                        )
                    findNavController().navigate(action)
                }

                repo_box.setOnClickListener {
                    val action =
                        UserProfileFragmentDirections.actionUserProfileFragmentToUserRepoFragment(
                            response.body()?.login.toString()
                        )
                    findNavController().navigate(action)
                }


            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "UserName does not exist", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {

                    if (hasNetworkAvailable(requireContext())) {
                        progressBar.visibility = View.VISIBLE
                        card_view_dp.visibility = View.GONE
                        card_view_count.visibility = View.GONE
                        scroll_view.visibility = View.GONE

                        searchView.clearFocus()
                        viewModel.setUserProfile(query)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Check your network connection!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(requireContext(), "Enter User Name", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network != null)
    }
}