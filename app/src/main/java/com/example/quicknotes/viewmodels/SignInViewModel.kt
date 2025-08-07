package com.example.quicknotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _isSignedIn = MutableLiveData<Boolean>()
    val isSignedIn: LiveData<Boolean> = _isSignedIn

    fun handleSignInResult(idToken: String?) {
        if (idToken == null) {
            _isSignedIn.value = false
            return
        }

        val credential = GoogleAuthProvider.getCredential(idToken, null)

        viewModelScope.launch {
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    _isSignedIn.value = task.isSuccessful
                }
        }
    }

    fun checkSignedInStatus() {
        _isSignedIn.value = auth.currentUser != null
    }
}
