package com.example.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFinishedGameFragmentBinding
import com.example.myapplication.databinding.FragmentGameFragmentBinding
import com.example.myapplication.domain.entity.GameResult
import java.lang.RuntimeException

class finished_game_fragment : Fragment() {

    val args by navArgs<finished_game_fragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        if (args.gameResults.winner){
            binding.emojiResult.setBackgroundResource(R.drawable.ic_smile)
        } else  {
            binding.emojiResult.setBackgroundResource(R.drawable.ic_sad)
        }
        binding.tvRequiredAnswers.text = String.format(
                    getString(R.string.required_score),
            args.gameResults.gameSettings.minCountOfRightAnswers.toString()
        )
        binding.tvScoreAnswers.text = String.format(
            getString(R.string.score_answers),
            args.gameResults.countOfRightAnswers.toString()
        )
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            args.gameResults.gameSettings.minPercentOfRightAnswers.toString()
        )
        binding.tvScorePercentage.text = String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())

    }

    private fun getPercentOfRightAnswers(): Int{
        if (args.gameResults.countOfQuestions == 0){
            return 0
        }
        return (args.gameResults.countOfRightAnswers / args.gameResults.countOfQuestions.toDouble() *100 ).toInt()
    }

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
    private fun retryGame(){
            findNavController().popBackStack()

    }

}


