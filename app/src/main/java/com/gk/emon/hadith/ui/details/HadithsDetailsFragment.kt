package com.gk.emon.hadith.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.gk.emon.core_features.base_ui_containers.BaseFragment
import com.gk.emon.core_features.extensions.hideLoadingPopup
import com.gk.emon.core_features.extensions.showLoadingPopup
import com.gk.emon.hadith.databinding.FragmentDetailsBinding
import com.gk.emon.lovelyLoading.LoadingPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HadithsDetailsFragment : BaseFragment() {
    private val viewModelHadithDetails: HadithDetailsViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentDetailsBinding
    private val args: HadithsDetailsFragmentArgs by navArgs()

    companion object {
        fun newInstance() =
            HadithsDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = viewModelHadithDetails
            lifecycleOwner = this@HadithsDetailsFragment.viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoading()
        viewModelHadithDetails.loadHadithDetails(false, args.collectionName, args.hadithNumber)
    }

    private fun setupLoading() {
        viewModelHadithDetails.dataLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                LoadingPopup.showLoadingPopUp()
            } else {
                LoadingPopup.hideLoadingPopUp()
            }
        })
    }
}