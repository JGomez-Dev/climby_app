package com.jgomez.authentication_presentacion.viewmodel

import androidx.lifecycle.ViewModel

private fun getGoogleLoginAuth(): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(getString(R.string.gcp_id))
        .requestId()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(this, gso)
}

class LoginViewModel : ViewModel() {
}