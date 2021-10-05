package br.com.mateus.yourtip

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.mateus.yourtip.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tipCalculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        //acessa o valor
        val cost: Double? = binding.costOfServiceEditText.text
            .toString()
            .toDoubleOrNull()

        if (cost == null) {
            binding.tipResult.text = displayTip(0.0)
            return Toast.makeText(this, "Informe o custo do serviço", Toast.LENGTH_SHORT).show()
        }

        //acessa o RadioButton selecionado, e define a porcentagem
        val tipPercentage: Double = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_fifteen_percent -> 0.15
            R.id.option_ten_percent -> 0.10
            else -> 0.08
        }

        //calcula o valor da gorjeta
        var tip: Double = cost * tipPercentage

        //arredonda valor se tiver marcado
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        //formata para moeda configurada no sistema
        val formattedTip = displayTip(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    fun displayTip(tip: Double): String {
        return NumberFormat.getCurrencyInstance().format(tip)
    }
}

