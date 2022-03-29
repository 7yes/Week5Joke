package com.sevenyes.w5cn07.views

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenyes.w5cn07.adapter.JokeAdapter
import com.sevenyes.w5cn07.databinding.FragmentInfBinding
import com.sevenyes.w5cn07.netrrestapi.JokesAPI
import com.sevenyes.w5cn07.viewmodels.JokesViewModel
import com.sevenyes.w5cn07.views.state.JokeUIState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InfFragment : Fragment() {

    private val adapterJoke by lazy {
        JokeAdapter()
    }

    private val viewModel: JokesViewModel by sharedViewModel()
    private var _binding: FragmentInfBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfBinding.inflate(inflater, container, false)

        binding.jokesRV.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterJoke
        }

        viewModel.singleJokeState.observe(viewLifecycleOwner, ::loadJokes)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBigList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadJokes(jokesUIState: JokeUIState){
        when (jokesUIState) {
            is JokeUIState.SUCCESS -> {

                adapterJoke.appendJokes(jokesUIState.jokes.jokes)
            }
            is JokeUIState.LOADING -> {
                /// progres bar
            }
            is JokeUIState.ERROR -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(jokesUIState.e.localizedMessage)
                    .show()
            }
        }
    }
}

