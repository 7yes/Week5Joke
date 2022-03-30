package com.sevenyes.w5cn07

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import com.sevenyes.w5cn07.databinding.FragmentConfigBinding
import com.sevenyes.w5cn07.viewmodels.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ConfigFragment : Fragment() {

    private var _binding:FragmentConfigBinding? = null
    private  val binding get () = _binding!!
    private val viewModel: JokesViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConfigBinding.inflate(inflater, container, false)
        binding.explicitSwitch.splitTrack = binding.explicitSwitch.splitTrack

        binding.explicitSwitch.setOnCheckedChangeListener{
                _: CompoundButton, noExplicit: Boolean ->
            viewModel.explicit = noExplicit

            if(noExplicit)Toast.makeText(requireContext(), "Explicit have been disabled", Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(), "Explicit have been enabled", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}

