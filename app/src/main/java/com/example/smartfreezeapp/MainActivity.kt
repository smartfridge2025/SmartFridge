package com.example.smartfreezeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.emptyLongSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfreezeapp.databinding.ActivityMainBinding
import com.example.smartfreezeapp.databinding.ActivityProductAddBinding
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import java.util.UUID



class MainActivity : AppCompatActivity() {
/*  #####################
    Proudly developed by:
    Valeria Kolesova
    Shigabova DIana
    Chekhova Poilna
    Kosachev Ivan
    #####################
 */
    lateinit var binding: ActivityMainBinding

    private val adapter = ProductsDataAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val linkToProd: TextView = findViewById(R.id.add_products)
        linkToProd.setOnClickListener {
            var add_prod_intent = Intent(this, ProductAddActivity::class.java)
            startActivity(add_prod_intent)
        }

        val linkToShowProducts: TextView = findViewById(R.id.show_products)
        linkToShowProducts.setOnClickListener {
            val db = DbDriver(this, null)
            var prod_list = db.getAllProducts()
            binding.apply {
                productList.layoutManager = LinearLayoutManager(this@MainActivity)
                productList.adapter = adapter
                adapter.clear()
                for (n in prod_list) {

                    val prod_name = "Продукт: " + n.name + "."
                    val prod_type = "Типа продукта: " + n.type + "."
                    val prod_start = "Произведено: " + n.creation_date + "."
                    val prod_finish = "Годен до: " + n.expiration_date + "."
                    val productToAdd = Product(
                        n.uniq_code,
                        prod_name,
                        prod_type,
                        prod_start,
                        prod_finish,
                        n.quantity,
                        n.nutri_val,
                        n.quant
                    )
                    adapter.addProduct(productToAdd)
                }

            }
        }
    }
}