package com.example.imc_calculator

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.imc_calculator.databinding.FragmentImcBinding
import android.view.inputmethod.InputMethodManager

class IMCFragment : Fragment() {

    private var binding: FragmentImcBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImcBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.btnCalcular?.setOnClickListener {
            if (binding?.etAltura?.text.toString().isBlank() || binding?.etPeso?.text.toString().isBlank()) {
                Toast.makeText(
                    requireContext().applicationContext,
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val altura = binding?.etAltura?.text.toString().toDouble() / 100
                val peso = binding?.etPeso?.text.toString().toDouble()
                val imc = peso / (altura * altura).toString().toDouble()
                binding?.imc?.text = imc.format(0)

                val textResult = when {
                    imc < 18.5 -> "(Abaixo do peso)"
                    imc > 18.5 && imc < 24.9 -> "(Peso normal)"
                    imc > 25 && imc < 29.9 -> "(Sobrepeso)"
                    imc > 30 && imc < 40 -> "(Obesidade)"
                    imc > 40 -> "(Obesidade mórbida)"
                    else -> ""
                }
                binding?.tvResultado?.text = textResult
                binding?.cvResult?.isVisible = true
                context?.hideKeyboard()


            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun Double.format(digits: Int) = "%.${digits}f".format(this)
    fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}