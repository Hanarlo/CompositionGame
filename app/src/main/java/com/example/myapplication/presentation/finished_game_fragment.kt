package com.example.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFinishedGameFragmentBinding
import com.example.myapplication.databinding.FragmentGameFragmentBinding
import com.example.myapplication.domain.entity.GameResult
import java.lang.RuntimeException

class finished_game_fragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            }
        )
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        if (gameResult.winner){
            binding.emojiResult.setBackgroundResource(R.drawable.ic_smile)
        } else  {
            binding.emojiResult.setBackgroundResource(R.drawable.ic_sad)
        }
        binding.tvRequiredAnswers.text = String.format(
                    getString(R.string.required_score),
            gameResult.countOfRightAnswers.toString()
        )
        binding.tvScoreAnswers.text = String.format(
            getString(R.string.score_answers),
            gameResult.countOfQuestions.toString()
        )
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers.toString()
        )
        binding.tvScorePercentage.text = String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())

    }

    private fun getPercentOfRightAnswers(): Int{
        if (gameResult.countOfQuestions == 0){
            return 0
        }
        return (gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble() *100 ).toInt()
    }

    private lateinit var gameResult: GameResult
    private var _binding: FragmentFinishedGameFragmentBinding? = null
    private val binding: FragmentFinishedGameFragmentBinding
        get() = _binding ?: throw RuntimeException("FragmentWElcomeBinding == null")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
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

    private fun parseArguments(){
        requireArguments().getParcelable<GameResult>(KEY_RESULTS)?.let {
            gameResult = it
        }
    }
    private fun retryGame(){
            requireActivity().supportFragmentManager.popBackStack(game_fragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }
    companion object{

        const val NAME = "finishedGame"
        private const val KEY_RESULTS = "results";
        fun getInstance(gameResult: GameResult): finished_game_fragment{
            return finished_game_fragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_RESULTS,gameResult)
                }
            }
        }
    }
}


