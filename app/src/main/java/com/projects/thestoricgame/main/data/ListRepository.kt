package com.projects.thestoricgame.main.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.projects.thestoricgame.model.CharacterItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.projects.thestoricgame.model.UserItem
import com.projects.thestoricgame.main.api.ApiService
import com.projects.thestoricgame.model.MessageItem
import com.projects.thestoricgame.model.Story
import com.projects.thestoricgame.utils.utility.UIState
import javax.inject.Inject

class ListRepository @Inject constructor(private val apiService: ApiService) {

    private var database: DatabaseReference = Firebase.database.reference
    private val firestore = Firebase.firestore

    fun getUserListFromDB(result: (UIState<List<UserItem>>) -> Unit)  {
        firestore.collection("users")
            .get()
            .addOnSuccessListener { snapshot ->
                val userList = arrayListOf<UserItem>()
                for (document in snapshot) {
                    Log.d("Document", "${document.id} => ${document.data}")
                    userList.add(document.toObject(UserItem::class.java))
                }
                result.invoke(
                    UIState.Success(userList)
                )
            }
            .addOnFailureListener { exception ->
                Log.w("Document", "Error getting documents.", exception)
                result.invoke(
                    UIState.Failure(
                        exception.localizedMessage
                    )
                )
            }
    }

    fun addDataToDB(story: Story) {
        database.child("Story").setValue(story)
    }

    fun getMessageFromDB(chapter_number : Int,result: (UIState<List<CharacterItem>>) -> Unit) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Story/chapters/${chapter_number}/characters")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val allCharactersMessages = mutableListOf<CharacterItem>()

                for (characterSnapshot in dataSnapshot.children) {
                    val characterName = characterSnapshot.key
                    if (characterName != null) {
                        val messagesMap = mutableMapOf<String,MessageItem>()
                        val messagesSnapshot = characterSnapshot.child("messages")

                        for (messageSnapshot in messagesSnapshot.children) {
                            if (messageSnapshot.exists() && messageSnapshot.key != "0") {
                                val message = messageSnapshot.getValue(MessageItem::class.java)
                                message?.let { messageSnapshot.key?.let { key ->
                                    messagesMap.put(
                                        key,message)
                                } }
                            }
                        }
                        allCharactersMessages.add(CharacterItem(characterName, messagesMap))
                    }
                }

                allCharactersMessages.forEach { characterMessages ->
                    Log.d("CharacterMessages", "Character: ${characterMessages.Name}, Messages: ${characterMessages.messages}")
                }
                result.invoke(
                    UIState.Success(allCharactersMessages)
                )
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Firebase", "loadPost:onCancelled", databaseError.toException())
                result.invoke(
                    UIState.Failure(
                        databaseError.message
                    )
                )
            }
        })
    }

    suspend fun getListFromRetrofit() : List<UserItem>? {
        return apiService.getInventory().body()
    }
}