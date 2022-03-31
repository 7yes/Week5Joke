package com.sevenyes.w5cn07.views

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.sevenyes.w5cn07.adapter.JokeAdapter
import com.sevenyes.w5cn07.databinding.FragmentInfBinding
import com.sevenyes.w5cn07.netrrestapi.JokesAPI
import com.sevenyes.w5cn07.viewmodels.JokesViewModel
import com.sevenyes.w5cn07.views.state.JokeUIState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.getScopeId

class InfFragment : Fragment() {

    private val adapterJoke by lazy {
        JokeAdapter()
    }

    private val viewModel: JokesViewModel by sharedViewModel()
    private var _binding: FragmentInfBinding? = null
    private val binding get() = _binding!!
    private var requestinMore = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfBinding.inflate(inflater, container, false)


        binding.jokesRV.apply {
            val linearLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            layoutManager = linearLayoutManager
            adapter = adapterJoke
            var pos: Int

            addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    pos = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    // check pos of RV vs posicion of the list
                    //Log.d("TAG","pos: $pos --- adapter pos: ${adapterJoke.itemCount}")

                    if (adapterJoke.itemCount - pos == 2 && !requestinMore) {
                        requestinMore = true
                        // I am checking  when to ask for new request,
                        Log.d("TAG", "pos: $pos --- adapter pos: ${adapterJoke.itemCount}")
                        viewModel.getBigList()
                    }
                }
            })
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

    private fun loadJokes(jokesUIState: JokeUIState) {
        when (jokesUIState) {
            is JokeUIState.SUCCESS -> {
                requestinMore = false
                adapterJoke.appendJokes(jokesUIState.jokes)
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



