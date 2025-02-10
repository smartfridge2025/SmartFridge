package com.example.smartfreezeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfreezeapp.databinding.ProductItemBinding



class ProductsDataAdapter: RecyclerView.Adapter<ProductsDataAdapter.DataHolder>() {
    val productList = ArrayList<Product>()
    class DataHolder(item:View): RecyclerView.ViewHolder(item)
        {
            val binding = ProductItemBinding.bind(item)
            fun bind(product:Product) = with(binding){
                name.text = product.name
                type.text = product.type
                creationDate.text = product.creation_date
                expirationDate.text = product.expiration_date
                quantity.text = product.quantity.toString()
                nutriVal.text = product.nutri_val.toString()

            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return DataHolder(view)
    }
    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(productList[position])
    }
    override fun getItemCount(): Int {
        return productList.size
    }

    fun addProduct(product: Product)
    {
        productList.add(product)
        notifyDataSetChanged()
    }

    fun clear()
    {
        productList.clear()
        notifyDataSetChanged()
    }

}
