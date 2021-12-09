package com.example.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentGameFragmentBinding
import com.example.myapplication.databinding.FragmentLevelChooseBinding
import com.example.myapplication.domain.entity.level
import java.lang.RuntimeException

class fragment_level_choose : Fragment() {
    private var _binding: FragmentLevelChooseBinding? = null
    private val binding: FragmentLevelChooseBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLevelChooseBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            levelEasyButton.setOnClickListener {
                launchGameFragment(level.EASY)
            }
            levelMediumButton.setOnClickListener {
                launchGameFragment(level.NORMAL)
            }
            levelHardButton.setOnClickListener {
                launchGameFragment(level.HARD)
            }
            levelTestButton.setOnClickListener {
                launchGameFragment(level.TEST)
            }
        }

    }
    private fun launchGameFragment(level: level){
        findNavController().navigate(fragment_level_chooseDirections.actionFragmentLevelChoose2ToGameFragment(level))
        }

    }
