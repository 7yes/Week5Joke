package com.sevenyes.w5cn07.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sevenyes.w5cn07.databinding.FragmentCustomBinding
import com.sevenyes.w5cn07.viewmodels.JokesViewModel
import com.sevenyes.w5cn07.views.state.JokeUIState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CustomFragment : Fragment() {

    private var _binding: FragmentCustomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JokesViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomBinding.inflate(inflater, container, false)

        viewModel.singleJokeState.observe(viewLifecycleOwner) {
            when (it) {
                is JokeUIState.SUCCESS -> {
                    binding.textCustom.text = it.jokes.jokes[0].joke
                    viewModel.reset()
                }
                is JokeUIState.LOADING -> {
                    /// progres bar
                }
                is JokeUIState.ERROR -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage(it.e.localizedMessage)
                        .show()
                }
            }
        }

        binding.customBtn.setOnClickListener() {
            // check if the customer put first and last name

            if (binding.friendName.text.length > 5) {

                val names = binding.friendName.text.toString().split(" ")
                // check that customer put just 2 words
                if (names.size == 2) {
                    // call coroutines and threat
                    viewModel.getCustomJoke(names[0], names[1])

                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}