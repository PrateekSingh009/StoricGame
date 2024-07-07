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

    private lateinit var _binding : FragmentChatsListBinding
    private val binding get() = _binding
    private val viewModel : ListViewModel by viewModels<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.getUserListFromDb()
//        dummyDataToDb()
    }

    private fun onItemClick(item : UserItem) {
        parentFragmentManager.replaceFragment(MessageFragment(item), R.id.fragment_container)
    }

    private fun setupObserver() {
        viewModel.userListLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.Loading -> {
                    Log.e(TAG,"Loading")
                }
                is UIState.Failure -> {
                    Log.e(TAG,state.error.toString())
                }
                is UIState.Success -> {
                    state.data.let {
                        Log.i("UserItem",it.toString())
                        setupRecyclerView(it)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(list : List<UserItem>?) {
        if (list != null) {
            binding.chatsRecyclerView.apply {
                adapter = ListAdapter(list, ::onItemClick)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun dummyDataToDb() {
        val smsg1 = MessageItem("Hi","Prateek","Sejal")
        val smsg2 = MessageItem("How are you?","Prateek","Sejal")
        val smsg3 = MessageItem("I am fine!!","Prateek","Sejal")
        val smsg4 = MessageItem("What you doing?","Prateek","Sejal")

        val rmsg1 = MessageItem("Hey","Sejal","Prateek")
        val rmsg2 = MessageItem("I am good","Sejal","Prateek")
        val rmsg3 = MessageItem("Nothing!!!!!","Sejal","Prateek")
        val rmsg4 = MessageItem("What you doing?","Sejal","Prateek")

        val prateek_dialogue = CharacterItem("Prateek",hashMapOf("1" to smsg1,"2" to smsg2,"3" to smsg3,"4" to smsg4))
        val sejal_dialogue = CharacterItem("Sejal",hashMapOf("1" to rmsg1,"2" to rmsg2,"3" to rmsg3,"4" to rmsg4))

        val chapter1 = Chapter(hashMapOf("Prateek" to prateek_dialogue,"Sejal" to sejal_dialogue))

        val story = Story(hashMapOf("1" to chapter1))

        viewModel.addDataToDb(story)
    }
}

