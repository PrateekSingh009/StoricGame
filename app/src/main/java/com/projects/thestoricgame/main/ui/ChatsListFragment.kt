package com.projects.thestoricgame.main.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.thestoricgame.model.UserItem
import com.projects.thestoricgame.R
import com.projects.thestoricgame.databinding.FragmentChatsListBinding
import com.projects.thestoricgame.main.adapter.ListAdapter
import com.projects.thestoricgame.main.data.ListViewModel
import com.projects.thestoricgame.model.Chapter
import com.projects.thestoricgame.model.CharacterItem
import com.projects.thestoricgame.model.MessageItem
import com.projects.thestoricgame.model.Story
import com.projects.thestoricgame.utils.extensions.replaceFragment
import com.projects.thestoricgame.utils.utility.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatsListFragment : Fragment() {

    private lateinit var _binding: FragmentChatsListBinding
    private val binding get() = _binding
    private val viewModel: ListViewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.getUserListFromDb()
//        dummyDataToDb()
    }

    private fun onItemClick(item: UserItem) {
        parentFragmentManager.replaceFragment(MessageFragment(item), R.id.fragment_container)
    }

    private fun setupObserver() {
        viewModel.userListLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    Log.e(TAG, "Loading")
                }

                is UIState.Failure -> {
                    Log.e(TAG, state.error.toString())
                }

                is UIState.Success -> {
                    state.data.let {
                        Log.i("UserItem", it.toString())
                        setupRecyclerView(it)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(list: List<UserItem>?) {
        if (list != null) {
            binding.chatsRecyclerView.apply {
                adapter = ListAdapter(list, ::onItemClick)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun dummyDataToDb() {
        val smsg1 = MessageItem(
            messageType = "message", choices = null, message = "Hi", senderID = "Prateek", receiverID = "Sejal"
        )
        val smsg2 = MessageItem(
            messageType = "message", choices =null, message = "How are you?", senderID = "Prateek", receiverID = "Sejal"
        )
        val smsg3 = MessageItem(
            messageType = "message", choices =null, message = "I am fine!!", senderID = "Prateek", receiverID = "Sejal"
        )
        val smsg4 = MessageItem(
            messageType = "message", choices =null, message = "What you doing?", senderID = "Prateek", receiverID = "Sejal"
        )

        val rmsg1 = MessageItem(
            messageType = "message", choices =null, message = "Hey", senderID = "Sejal", receiverID = "Prateek"
        )
        val rmsg2 = MessageItem(
            messageType = "message", choices =null, message = "I am good", senderID = "Sejal", receiverID = "Prateek"
        )
        val rmsg3 = MessageItem(
            messageType = "message", choices =null, message = "Nothing!!!!!", senderID = "Sejal", receiverID = "Prateek"
        )
        val rmsg4 = MessageItem(
            messageType = "message", choices =null, message = "Making a call", senderID = "Sejal", receiverID = "Prateek"
        )

        val choice1 = MessageItem(
            messageType = "choice", hashMapOf("No" to 6,"Yes" to 4), message = "Would you like to have a conversation?", senderID = null, receiverID = null
        )

        val chapter1 = Chapter(listOf(smsg1,rmsg1,smsg2,choice1,rmsg2,smsg3,rmsg3,smsg4,rmsg4))

        val story = Story(hashMapOf("1" to chapter1))

        viewModel.addDataToDb(story)
    }
}

