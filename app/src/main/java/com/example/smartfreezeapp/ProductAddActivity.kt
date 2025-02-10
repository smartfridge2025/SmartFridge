package com.example.smartfreezeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class ProductAddActivity : AppCompatActivity() {

    private lateinit var scanQrAddBtn : Button
    private lateinit var scanQrDelBtn : Button
    private lateinit var scanQrShowBtn : Button
    private lateinit var infoTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_add)

        val linkToProd: Button = findViewById(R.id.buttonShowProd)
        linkToProd.setOnClickListener {
            var prod_intent = Intent(this, MainActivity::class.java)
            startActivity(prod_intent)
        }
        scanQrAddBtn = findViewById(R.id.buttonAddScanQR)
        scanQrDelBtn = findViewById(R.id.buttonDelScanQR)
        scanQrShowBtn = findViewById(R.id.buttonInfoScanQR)
        infoTextView = findViewById(R.id.textViewInfo)
        infoTextView.text = ""
        registerUiAddListener()
        registerUiDelListener()
        registerUiInfoListener()



    }

    private fun registerUiAddListener() {
        scanQrAddBtn.setOnClickListener {
            addScannerLauncher.launch(
                ScanOptions().setPrompt("Добавьте продукт по QR")
                .setDesiredBarcodeFormats(ScanOptions.QR_CODE))
        }
    }

    private fun registerUiDelListener() {
        scanQrDelBtn.setOnClickListener {
            delScannerLauncher.launch(
                ScanOptions().setPrompt("Удалить продукт по QR")
                    .setDesiredBarcodeFormats(ScanOptions.QR_CODE))
        }
    }

    private fun registerUiInfoListener() {
        scanQrShowBtn.setOnClickListener {
            showScannerLauncher.launch(
                ScanOptions().setPrompt("Отобразить продукт по QR")
                    .setDesiredBarcodeFormats(ScanOptions.QR_CODE))
        }
    }

    private val addScannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    )
    {result ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        else {
            val scannedValue = result.contents
            val db = DbDriver(this, null)
            val parts = scannedValue.split(',')
            val product_id = parts[0].trim()
            val product_name = parts[1].trim()
            val product_type = parts[2].trim()
            val product_creation_date = parts[3].trim()
            val product_expiration_date = parts[4].trim()
            val product_quantity= parts[5].trim()
            val product_nutri_val = parts[6].trim()
            val product_quant = parts[7].trim()
            val adding_product = Product(
                product_id,
                product_name,
                product_type,
                product_creation_date,
                product_expiration_date,
                product_quantity.toInt(),
                product_nutri_val.toInt(),
                product_quant)
            db.addProduct(adding_product)
            Toast.makeText(this, "Добавлен продукт: $product_name", Toast.LENGTH_SHORT).show()
        }
    }

    private val delScannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    )
    {result ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        else {
            val scannedValue = result.contents
            val db = DbDriver(this, null)
            val parts = scannedValue.split(',')
            val product_id = parts[0].trim()
            val product_name = parts[1].trim()
            db.delProduct(product_id)
            Toast.makeText(this, "Удалён продукт: $product_name", Toast.LENGTH_SHORT).show()
        }
    }

    private val showScannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    )
    {result ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        else {
            val scannedValue = result.contents
            val parts = scannedValue.split(',')
            val product_id = parts[0].trim()
            val product_name = parts[1].trim()
            val product_type = parts[2].trim()
            val product_creation_date = parts[3].trim()
            val product_expiration_date = parts[4].trim()
            val product_quantity= parts[5].trim()
            val product_nutri_val = parts[6].trim()
            val product_quant = parts[7].trim()
            Toast.makeText(this, "Отсканирован продукт: $product_name", Toast.LENGTH_SHORT).show()
            val strDelim = "\n"
            val resultStr = "Название продукта: " + product_name + strDelim + "Тип продукта: " + product_type + strDelim + "Дата производства: " + product_creation_date + strDelim + "Годен до: " + product_expiration_date + strDelim + "Всего " + product_quantity + " " + product_quant + " продукта" + strDelim + "калорийностью " + product_nutri_val + " Ккал"
            infoTextView.text = resultStr
        }
    }
}