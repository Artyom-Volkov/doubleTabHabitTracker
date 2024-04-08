package com.rc.android.homework.ui.fragment.habitListsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rc.android.homework.R
import kotlinx.android.synthetic.main.fragment_habit_list_pager.*
import kotlinx.android.synthetic.main.fragment_habit_lists.*

class HabitListsFragment : Fragment() {

    companion object {

        public const val TAG = "HABIT_LISTS_FRAGMENT_TAG"

        @JvmStatic
        fun newInstance() =
            HabitListsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private lateinit var habitListsFragmentAdapter: HabitListsFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        /*if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.bottomSheetContainer, BottomSheetFragment.newInstance(), "")
                .commit()
        }*/

        return inflater.inflate(R.layout.fragment_habit_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitListsFragmentAdapter = HabitListsFragmentAdapter(this)
        pager.adapter = habitListsFragmentAdapter

        habitListsFragmentAdapter.notifyDataSetChanged()

        val tabNames: Array<String> = arrayOf(
            getString(R.string.usefull_habit),
            getString(R.string.harmfull_habit),
        )

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        addHabitFAB.setOnClickListener { addHabitFABClicked() }


    }

    private fun addHabitFABClicked(){

        findNavController().navigate(R.id.action_habitListsFragment_to_habitEditingFragment)
    }

    public fun onHabitAdded(){

        habitRecyclerview?.adapter?.let {
            val habitCount = it.itemCount
            it.notifyItemInserted(habitCount - 1)
        }
    }

    public fun HabitEdited(position: Int){
        habitRecyclerview?.adapter?.notifyItemChanged(position)
    }

}