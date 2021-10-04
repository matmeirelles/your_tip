package br.com.mateus.yourtip

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.mateus.yourtip.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tipCalculateButton.setOnClickListener {
            if (binding.costOfService.text != null) {
                calculateTip()
            } else {
                Toast.makeText(this, "Informar valor da conta", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun calculateTip() {
        //acessa o valor
        val costInString: String = binding.costOfService.text.toString()
        val cost: BigDecimal? = costInString.toBigDecimalOrNull()

        if (cost == null) {
            binding.tipResult.text = " "
            return Toast.makeText(this, "Informe o custo do serviço", Toast.LENGTH_SHORT).show()
        }

        //acessa o RadioButton selecionado, e define a porcentagem
        val selectedId: Int = binding.tipOptions.checkedRadioButtonId
        val tipPercentage: BigDecimal = when (selectedId) {
            R.id.option_fifteen_percent -> "0.15".toBigDecimal()
            R.id.option_ten_percent -> "0.10".toBigDecimal()
            else -> "0.08".toBigDecimal()
        }

        //calcula o valor da gorjeta
        var tip: BigDecimal = cost * tipPercentage
        var tipDouble: Double = tip.toDouble()

        //acessa a opção de arredondar para cima
        val roundUp: Boolean = binding.roundUpSwitch.isChecked

        //arredonda valor se tiver marcado
        if (roundUp) {
            tipDouble = ceil(tipDouble)
            tip = tipDouble.toBigDecimal()
        }

        //formata para moeda configurada no sistema
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}

