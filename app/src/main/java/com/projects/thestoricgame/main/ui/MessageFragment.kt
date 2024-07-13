package com.projects.thestoricgame.main.ui

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.thestoricgame.model.UserItem
import com.projects.thestoricgame.R
import com.projects.thestoricgame.databinding.FragmentMessageBinding
import com.projects.thestoricgame.main.adapter.ListAdapter
import com.projects.thestoricgame.main.adapter.MessagesListAdapter
import com.projects.thestoricgame.main.data.ListViewModel
import com.projects.thestoricgame.model.CharacterItem
import com.projects.thestoricgame.model.MessageItem
import com.projects.thestoricgame.utils.extensions.replaceFragment
import com.projects.thestoricgame.utils.utility.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MessageFragment(private val user: UserItem) : Fragment() {

    private lateinit var _binding: FragmentMessageBinding
    private val binding get() = _binding
    private val viewModel: ListViewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(user)
        setupObserver()
        viewModel.getMessageFromDb(1)
    }

    private fun setupObserver() {
        viewModel.messageListLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    Log.e(ContentValues.TAG, "Loading")
                }

                is UIState.Failure -> {
                    Log.e(ContentValues.TAG, state.error.toString())
                }

                is UIState.Success -> {
                    state.data.let {
                        Log.i("UserItem", it.toString())
                        setupMessageRecyclerView(it)
                    }
                }
            }
        }
    }

    private fun setupMessageRecyclerView(list: List<CharacterItem>?) {
        if (list != null) {
            val messageList = mutableListOf<MessageItem>()
            val listAdapter = MessagesListAdapter("Prateek")
            binding.messagesRecyclerView.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(context)
            }
            CoroutineScope(Dispatchers.Main).launch {
                val flow = emitItemsWithDelay(list)
                flow.collect { messageItem ->
                    messageList.add(messageItem)
                    refreshRecyclerView(messageList)
                }
            }
        }
    }

    private fun emitItemsWithDelay(items: List<CharacterItem>): Flow<MessageItem> = flow {
        for (item in items) {
            item.messages.forEach {
                emit(it.value)
                delay(2000L)
            }
        }
    }

    private fun refreshRecyclerView(messages: MutableList<MessageItem>) {
        (binding.messagesRecyclerView.adapter as MessagesListAdapter).submitList(messages.toList())
    }

    private fun setupToolbar(user: UserItem) {
        binding.includedLayout.apply {
            otherUserNameText.text = user.name
            backBtn.setOnClickListener {
                parentFragmentManager.replaceFragment(ChatsListFragment(), R.id.fragment_container)
            }
        }
    }

    companion object {
        const val TAG = "StoryApp"
    }
}