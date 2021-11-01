package com.example.recipe_app_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edTitle:EditText
    private lateinit var edAuthor:EditText
    private lateinit var edIngredents:EditText
    private lateinit var edInstruction:EditText
    private lateinit var btnSave:Button
    private lateinit var btnRetrieves:Button

    private lateinit var rvMain:RecyclerView
    private lateinit var rvAdapter: RVAdapter

    lateinit var recipe:ArrayList<Recipe>

    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipe= arrayListOf()

        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.getRecipe().observe(this,{
            re->rvAdapter.update(re )
        })
        
        rvMain=findViewById(R.id.rvMain)
        rvAdapter= RVAdapter(this)
        rvMain.adapter=rvAdapter
        rvMain.layoutManager=LinearLayoutManager(this)

        edTitle=findViewById(R.id.edTitle)
        edAuthor=findViewById(R.id.edAuthor)
        edIngredents=findViewById(R.id.edIngredents)
        edInstruction=findViewById(R.id.edInstructions)

        btnSave=findViewById(R.id.btnSave)

        btnSave.setOnClickListener {

            mainViewModel.add(Recipe("",edTitle.text.toString(),edAuthor.text.toString(),edIngredents.text.toString(),edInstruction.text.toString()))

        }

        btnRetrieves=findViewById(R.id.btnRetrieves)
        btnRetrieves.setOnClickListener {

           mainViewModel.getData()
        }

    }
}