package com.example.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWelcomeFragmentBinding
import java.lang.RuntimeException

class welcome_fragment : Fragment() {

    private var _binding: FragmentWelcomeFragmentBinding? = null
    private val binding: FragmentWelcomeFragmentBinding
    get() = _binding ?: throw RuntimeException("FragmentWElcomeBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
        launchChooseLevelFragment()
        }
    }

    private fun launchChooseLevelFragment(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment_level_choose.newInstance())
            .addToBackStack(fragment_level_choose.NAME)
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}