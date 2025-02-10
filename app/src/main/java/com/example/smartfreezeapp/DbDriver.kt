package com.example.smartfreezeapp
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbDriver(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "products",factory,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE IF NOT EXISTS products(uniq_code TEXT PRIMARY KEY, name TEXT, type TEXT, creation_date TEXT,expiration_date TEXT, quantity Int, nutri_val Int, quant TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       // db!!.execSQL("DROP TABLE IF EXISTS products")
       // onCreate(db)
    }
    fun addProduct(product: Product){
        val values = ContentValues()
        values.put("uniq_code", product.uniq_code)
        values.put("name", product.name)
        values.put("type", product.type)
        values.put("creation_date", product.creation_date)
        values.put("expiration_date", product.expiration_date)
        values.put("quantity", product.quantity)
        values.put("nutri_val", product.nutri_val)
        values.put("quant", product.quant)


        val db = this.writableDatabase
        db.insert("products", null, values)
        db.close()
    }

    fun getProduct(uniq_code: String):Boolean{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM products WHERE uniq_code='$uniq_code'",null)
        db.close()
        return result.moveToFirst()
    }

    fun delProduct(uniq_code: String):Boolean{
        //val values = ContentValues()
        val db = this.writableDatabase
        //val result = db.rawQuery("DELETE * FROM products WHERE uniq_code='$uniq_code'",null)
        //val result = db.rawQuery("DELETE FROM products WHERE uniq_code='$uniq_code'",null)
        db.delete("products","uniq_code=?", arrayOf(uniq_code))
        db.close()
        return true
    }

    fun getAllProducts():MutableList<Product>{
        val productsList = ArrayList<Product>()
        val this_db = this.readableDatabase
        val result = this_db.rawQuery("SELECT * FROM products",null)
        if (result.moveToFirst()){
            do {
                productsList.add(Product(
                    result.getString(0),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getInt(6),
                    result.getString(7)))
            } while (result.moveToNext())
        }
        this_db.close()
        return productsList
    }
}