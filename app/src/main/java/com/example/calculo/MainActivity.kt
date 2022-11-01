package com.example.calculo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculo.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calcularPropinaBoton.setOnClickListener { calcularPropina() }
    }


    private fun calcularPropina(){
        val stringInTextCoste = binding.costeDelServicio.text.toString()
        val coste = stringInTextCoste.toDoubleOrNull()
        val botonSeleccionado = binding.seleccion.checkedRadioButtonId

        if(coste == null){
            binding.costePropina.text = ""
            return
        }

        var porcentajePropina = when (botonSeleccionado) {
            R.id.seleccion_espectacular -> 0.20
            R.id.seleccion_muy_bueno -> 0.18
            R.id.seleccion_bueno -> 0.15
            R.id.seleccion_otro -> binding.costeDelServicio.text.toString().toDoubleOrNull()
            else -> 0.0
        }

        if(porcentajePropina == null){
            binding.costePropina.text = ""
            return
        }

        if(botonSeleccionado == R.id.seleccion_otro){
            porcentajePropina /= 1000
        }

        var propina = porcentajePropina*coste
        val redondear = binding.switchParaRedondear.isChecked

        if(redondear){
            propina = ceil(propina)
        }

        val propinaFormateada = NumberFormat.getCurrencyInstance().format(propina)
        binding.costePropina.text = getString(R.string.cantidad_propina, propinaFormateada)

        val total = coste + propina
        val totalFormateado = NumberFormat.getCurrencyInstance().format(total)
        binding.costeTotal.text = getString(R.string.coste_total, totalFormateado)
    }

}