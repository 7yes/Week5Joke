package com.sevenyes.w5cn07.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sevenyes.w5cn07.databinding.FragmentJokeBinding
import com.sevenyes.w5cn07.viewmodels.JokesViewModel
import com.sevenyes.w5cn07.views.state.JokeUIState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class JokeFragment : Fragment() {

    private var _binding: FragmentJokeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JokesViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
          _binding = FragmentJokeBinding.inflate(inflater, container, false)

        viewModel.singleJokeState
            // el it es el 2do paramewtro, kotlin le gusta afuera
            .observe(viewLifecycleOwner) {
                when(it) {
                    is JokeUIState.SUCCESS -> {
                        binding.textJoke.text = it.jokes.jokes[0].joke
                        viewModel.reset()
                    }
                    is JokeUIState.ERROR -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage(it.e.localizedMessage)
                            .show()
                    }
                }
            }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSingleJoke()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}