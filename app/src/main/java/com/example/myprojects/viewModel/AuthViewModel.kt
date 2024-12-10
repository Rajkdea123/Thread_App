package com.example.myprojects.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojects.model.UserModel
import com.example.myprojects.utils.SharedPref
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class AuthViewModel : ViewModel(){

    val auth = FirebaseAuth.getInstance()
    private  val db= FirebaseDatabase.getInstance()
    val userRef= db.getReference("user")
    private  val storageRef= Firebase.storage.reference
    private  val imageRef= storageRef.child("user/${UUID.randomUUID()}.jpg")

    private  val _firebaseUser= MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> = _firebaseUser

    private val _error= MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser

    }
    fun login (email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    getData(auth.currentUser!!.uid,context)
                    _firebaseUser.postValue(auth.currentUser)
                }else{
                    _error.postValue("Something went wrong .")
                }
            }
    }

    private fun getData(uid: String,context: Context) {




        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val userData= snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(
                    userData!!.name,
                    userData.email,
                    userData.bio,
                    userData.userName,
                    userData.imageUrl,
                    context
                )

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    fun register (email:String ,password:String,name:String,bio:String,userName:String,
                  imageUri: Uri, context: Context
    ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(email,password,name,bio,userName,imageUri,auth.currentUser?.uid, context)
                }else{
                    _error.postValue("Something went wrong .")
                }
            }
    }

    private fun saveImage(email: String, password: String, name: String, bio: String, userName: String, imageUri: Uri, uid: String?,context: Context) {

        val uploadTask= imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener { 
            imageRef.downloadUrl.addOnSuccessListener { 
                saveData(email,password,name,bio,userName,it.toString(),uid,context)
            }
        }
    }

    private fun saveData(email: String, password: String, name: String, bio: String, userName: String, toString: String, uid: String?,context: Context) {

        val firestoreDb= Firebase.firestore
        val followersRef= firestoreDb.collection("followers").document(uid!!)
        val followingRef= firestoreDb.collection("following").document(uid!!)
        followingRef.set(mapOf("followingIds" to listOf<String>()))
        followersRef.set(mapOf("followerIds" to listOf<String>()))


    val userData= UserModel(email, password, name, bio, userName, toString)

        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {

             SharedPref.storeData(name,email,bio,userName,toString,context)
            }.addOnFailureListener{

            }
    }
     @SuppressLint("NullSafeMutableLiveData")
     fun logout(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }
}