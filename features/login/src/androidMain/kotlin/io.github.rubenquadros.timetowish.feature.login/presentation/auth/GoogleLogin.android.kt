package io.github.rubenquadros.timetowish.feature.login.presentation.auth

import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import io.github.rubenquadros.timetowish.core.activity.ActivityHolder

actual class GoogleLoginDelegate actual constructor() {
    actual suspend fun login(clientId: String) {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption(clientId))
            .build()

        ActivityHolder.getActivityContext()?.let { context ->
            val manager = CredentialManager.create(context)

            try {
                val result = manager.getCredential(
                    request = request,
                    context = context
                )

                handleSignIn(result)
            } catch (e: GetCredentialException) {

            }
        }
    }

    private fun getGoogleIdOption(clientId: String): GetSignInWithGoogleOption {
        return GetSignInWithGoogleOption.Builder(clientId).build()
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        //return this result
                    } catch (e: GoogleIdTokenParsingException) {
                        //log failure
                    }
                } else {
                    //log failure
                }
            }
            else -> {
                //log and do nothing
            }
        }
    }
}