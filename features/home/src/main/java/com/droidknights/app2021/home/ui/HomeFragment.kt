package com.droidknights.app2021.home.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import com.droidknights.app2021.core.ui.ActivityHelper
import com.droidknights.app2021.home.R
import com.droidknights.app2021.home.databinding.FragmentHomeBinding
import com.droidknights.app2021.home.ui.adapter.EventAdapter
import com.droidknights.app2021.home.ui.adapter.HeaderAdapter
import com.droidknights.app2021.home.ui.adapter.InfoAdapter
import com.droidknights.app2021.shared.model.Event
import com.droidknights.app2021.shared.model.Sponsor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.homeInfo.observe(viewLifecycleOwner) {
            val concatAdapter = ConcatAdapter(
                ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
                HeaderAdapter(),
                InfoAdapter(lifecycleScope, it.sponsors, object : InfoAdapter.ItemHandler {
                    override fun clickSponsor(sponsor: Sponsor) {
                        // TODO: androidx.browser:browser를 활용해 앱내에서 웹뷰 열기
                        ActivityHelper.startActionView(requireContext(), sponsor.homepage)
                    }
                }),
                EventAdapter(it.events, object : EventAdapter.ItemHandler {
                    override fun clickEvent(event: Event) {
                        // TODO: androidx.browser:browser를 활용해 앱내에서 웹뷰 열기
                        ActivityHelper.startActionView(requireContext(), event.url)
                    }
                })
            )
            binding.recyclerView.adapter = concatAdapter
            binding.recyclerView.addItemDecoration(EventItemDecoration(view.context))
        }
    }
}