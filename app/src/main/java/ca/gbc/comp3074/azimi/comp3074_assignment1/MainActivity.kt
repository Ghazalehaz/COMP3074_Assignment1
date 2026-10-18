
package ca.gbc.comp3074.azimi.comp3074_assignment1


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        val etHours = findViewById<EditText>(R.id.etHours)
        val etRate = findViewById<EditText>(R.id.etRate)
        val etTax = findViewById<EditText>(R.id.etTax)

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnAbout = findViewById<Button>(R.id.btnAbout)

        val tvPay = findViewById<TextView>(R.id.tvPay)
        val tvOvertimePay = findViewById<TextView>(R.id.tvOvertimePay)
        val tvTotalPay = findViewById<TextView>(R.id.tvTotalPay)
        val tvTax = findViewById<TextView>(R.id.tvTax)

        btnCalculate.setOnClickListener {

            val hours = etHours.text.toString().toDoubleOrNull()
            val rate = etRate.text.toString().toDoubleOrNull()
            val taxRate = etTax.text.toString().toDoubleOrNull()

            if (hours == null || rate == null || taxRate == null) {
                Toast.makeText(
                    this,
                    "Please enter all values",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (hours < 0 || rate < 0 || taxRate < 0 || taxRate > 1) {
                Toast.makeText(
                    this,
                    "Please enter valid values",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val pay: Double
            val overtimePay: Double

            if (hours <= 40) {
                pay = hours * rate
                overtimePay = 0.0
            } else {
                pay = 40 * rate
                overtimePay = (hours - 40) * rate * 1.5
            }

            val totalPay = pay + overtimePay
            val tax = pay * taxRate

            tvPay.text = String.format(Locale.US, "Pay: $%.2f", pay)
            tvOvertimePay.text = String.format(
                Locale.US, "Overtime Pay: $%.2f", overtimePay
            )
            tvTotalPay.text = String.format(
                Locale.US, "Total Pay: $%.2f", totalPay
            )
            tvTax.text = String.format(Locale.US, "Tax: $%.2f", tax)
        }

        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
