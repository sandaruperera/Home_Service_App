package lk.nibm.hireupapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import lk.nibm.hireupapp.R
import lk.nibm.hireupapp.adapter.HardwareCategoriesNamesAdapter
import lk.nibm.hireupapp.common.CategoryDataManager
import lk.nibm.hireupapp.model.HardwareCategoriesData
import lk.nibm.hireupapp.model.ServiceProviders

class test : AppCompatActivity() {
    private lateinit var txtCategoryType : TextView
    private lateinit var serviceProviders : RecyclerView
    private lateinit var adapter: HardwareCategoriesNamesAdapter
    private var layoutManager : RecyclerView.LayoutManager? = null
    private lateinit var databaseReference: DatabaseReference
    private val itemList = mutableListOf<HardwareCategoriesData>()
    private lateinit var id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_providers)
        initializeComponents()
        loadCategory()
        loadSPRecyclerView()
    }
    private fun loadSPRecyclerView() {
        serviceProviders = findViewById(R.id.ServiceProvidersRecyclerView)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        serviceProviders.layoutManager = layoutManager
        adapter = HardwareCategoriesNamesAdapter(itemList)
        serviceProviders.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().reference.child("Shop").child("Category")
        Toast.makeText(applicationContext, id, Toast.LENGTH_SHORT).show()
        val query: Query = databaseReference.orderByChild("hardwareID").equalTo(id)
        query.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (itemSnapshot in snapshot.children){
                    val item = itemSnapshot.getValue(HardwareCategoriesData::class.java)
                    item?.let { itemList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that may occur while fetching data
            }
        })
    }



    private fun loadCategory() {
        val extras = intent.extras
        if (extras != null) {
            // Retrieve the "CATEGORY_NAME" extra using the key "CATEGORY_NAME"
            txtCategoryType.text = extras.getString("HARDWARE_CATEGORY_NAME")
//            categoryName = extras.getString("CATEGORY_NAME").toString()
//            categoryID = extras.getString("CATEGORY_ID").toString()
            id = extras.getString("HARDWARE_CATEGORY_ID").toString()
            CategoryDataManager.name = extras.getString("HARDWARE_CATEGORY_NAME").toString()
            CategoryDataManager.id = extras.getString("HARDWARE_CATEGORY_ID").toString()
        }
    }

    private fun initializeComponents() {
        txtCategoryType = findViewById(R.id.txtCategoryType)
    }
}