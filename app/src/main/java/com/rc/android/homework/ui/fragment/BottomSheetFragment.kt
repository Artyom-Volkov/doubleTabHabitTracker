package com.rc.android.homework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rc.android.homework.HabitTrackerApplication
import com.rc.android.homework.databinding.FragmentBottomSheetBinding
import com.rc.android.homework.domain.HabitTracker
import com.rc.android.homework.ui.fragment.habitListsFragment.HabitListsFragment
import com.rc.android.homework.ui.viewmodels.HabitListsViewModel
import com.rc.android.homework.ui.viewmodels.HabitListsViewModelFactory
import javax.inject.Inject

class BottomSheetFragment : Fragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HabitListsViewModel

    @Inject
    lateinit var habitTracker: HabitTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

        (requireActivity().application as HabitTrackerApplication).appComponent.inject(this)

        val parentFragment = requireParentFragment()
        if (parentFragment is HabitListsFragment){
            viewModel = ViewModelProvider(parentFragment, HabitListsViewModelFactory(habitTracker))
                .get(HabitListsViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchHabitEditText.addTextChangedListener{
            viewModel.habitNameFiltering(it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}