package com.gk.emon.hadith.ui.list.hadiths

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gk.emon.core_features.base_framework_ui.BaseFragment
import com.gk.emon.core_features.extensions.*
import com.gk.emon.hadith.R
import com.gk.emon.hadith.databinding.FragmentHadithsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HadithsFragment : BaseFragment() {
    private val viewModelHadiths: HadithsViewModel by viewModels()
    private lateinit var hadithsAdapter: HadithsAdapter
    private lateinit var viewDataBinding: FragmentHadithsBinding

    companion object {
        fun newInstance() =
            HadithsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentHadithsBinding.inflate(inflater, container, false).apply {
            viewModel = viewModelHadiths
            lifecycleOwner = this@HadithsFragment.viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        setupLoading()
        setupNavigation()
        viewModelHadiths.loadHadiths(true, "", "")
    }

    private fun setupLoading() {
        viewModelHadiths.dataLoading.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                viewDataBinding.tvEmpty.invisible()
                showLoadingPopup(activity)
            } else {
                hideLoadingPopup(activity)
            }
        })
    }

    private fun setupNavigation() {
        viewModelHadiths.openHadithEvent.observe(this.viewLifecycleOwner, EventObserver {
            /* val action = HaD.actionCollectionsToBooks(it.name,it.getProperCollectionEnglishName())
             findNavController().navigate(action)*/
        })
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            hadithsAdapter = HadithsAdapter(R.layout.item_hadith_books, viewModel)
            viewDataBinding.rvMedicineList.adapter = hadithsAdapter
            viewModelHadiths.hadiths.observe(this.viewLifecycleOwner, Observer {
                if (it.isEmpty()) viewDataBinding.tvEmpty.visible()
                else viewDataBinding.tvEmpty.invisible()
                hadithsAdapter.setList(it)
            })
        }


    }

}