package com.example.recipe_app_firebase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application):AndroidViewModel(application) {
    private val recipe:MutableLiveData<List<Recipe>> = MutableLiveData()
    private var db:FirebaseFirestore=Firebase.firestore

    fun getRecipe():LiveData<List<Recipe>>{
        return recipe
    }
    fun getData(){
        db.collection("Recipes")
            .get()
            .addOnSuccessListener { result->
                val tempR= arrayListOf<Recipe>()
                for(document in result){
                    var title =""
                    var author =""
                    var ingredients = ""
                    var instructions =""
                    document.data.map { (key, value)
                        ->
                        when (key) {
                            "title" -> title = value as String
                            "author" -> author = value as String
                            "ingredents" -> ingredients = value as String
                            "instruction" -> instructions = value as String
                        }
                       tempR.add(Recipe(document.id,title,author,ingredients,instructions) )}
                }
                recipe.value=tempR
            }
            .addOnFailureListener {exception->
                Log.w("MainActivity", "Error getting documents.", exception)
            }


    }
    fun add(recipe: Recipe){
        CoroutineScope(Dispatchers.IO).launch {
            val newRecipe = hashMapOf(
                "title" to recipe.title,
               "author" to recipe.author,
                "ingredents" to recipe.ingredents,
              "instruction" to recipe.instruction
            )
            db.collection("Recipes").add(newRecipe)
            getData()
        }
    }
}