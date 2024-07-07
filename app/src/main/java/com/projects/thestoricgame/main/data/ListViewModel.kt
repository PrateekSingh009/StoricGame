package com.projects.thestoricgame.main.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.thestoricgame.model.CharacterItem
import com.projects.thestoricgame.model.MessageItem
import com.projects.thestoricgame.model.Story
import com.projects.thestoricgame.model.UserItem
import com.projects.thestoricgame.utils.utility.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: ListRepository) : ViewModel() {

    val _userListLiveData = MutableLiveData<UIState<List<UserItem>>>()
    val userListLiveData: LiveData<UIState<List<UserItem>>>
        get() = _userListLiveData

    val _messageListLiveData = MutableLiveData<UIState<List<CharacterItem>>>()
    val messageListLiveData: LiveData<UIState<List<CharacterItem>>>
        get() = _messageListLiveData

    fun getUserListFromDb() {
        _userListLiveData.value = UIState.Loading
        repository.getUserListFromDB() {_userListLiveData.value = it}
    }

    fun getMessageFromDb(chapter_number : Int) {
        _messageListLiveData.value = UIState.Loading
        repository.getMessageFromDB(chapter_number) {_messageListLiveData.value = it}
    }

    fun addDataToDb(story: Story) {
        repository.addDataToDB(story)
    }
}