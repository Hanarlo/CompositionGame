package com.example.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFinishedGameFragmentBinding
import com.example.myapplication.databinding.FragmentGameFragmentBinding
import java.lang.RuntimeException

class finished_game_fragment : Fragment() {
    private var _binding: FragmentFinishedGameFragmentBinding? = null
    private val binding: FragmentFinishedGameFragmentBinding
        get() = _binding ?: throw RuntimeException("FragmentWElcomeBinding == null")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishedGameFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}