package com.rc.android.homework.ui.fragment.habitListsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rc.android.homework.R
import com.rc.android.homework.databinding.FragmentHabitListsBinding

class HabitListsFragment : Fragment() {

    private var _binding: FragmentHabitListsBinding? = null
    private val binding get() = _binding!!

    private lateinit var habitListsFragmentAdapter: HabitListsFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        _binding = FragmentHabitListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitListsFragmentAdapter = HabitListsFragmentAdapter(this)
        binding.pager.adapter = habitListsFragmentAdapter

        habitListsFragmentAdapter.notifyDataSetChanged()

        val tabNames: Array<String> = arrayOf(
            getString(R.string.usefull_habit),
            getString(R.string.harmfull_habit),
        )

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        binding.addHabitFAB.setOnClickListener { addHabitFABClicked() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addHabitFABClicked(){

        findNavController().navigate(R.id.action_habitListsFragment_to_habitEditingFragment)
    }

}