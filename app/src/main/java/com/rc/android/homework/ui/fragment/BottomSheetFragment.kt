package com.rc.android.homework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rc.android.homework.R
import com.rc.android.homework.ui.fragment.habitListsFragment.HabitListsFragment
import com.rc.android.homework.ui.viewmodels.HabitListsViewModel
import com.rc.android.homework.ui.viewmodels.HabitListsViewModelFactory
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    private lateinit var viewModel: HabitListsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

        val parentFragment = requireParentFragment()
        if (parentFragment is HabitListsFragment){
            viewModel = ViewModelProvider(parentFragment, HabitListsViewModelFactory())
                .get(HabitListsViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchHabitEditText.addTextChangedListener{
            viewModel.HabitNameFiltering(it.toString())
        }
    }

}