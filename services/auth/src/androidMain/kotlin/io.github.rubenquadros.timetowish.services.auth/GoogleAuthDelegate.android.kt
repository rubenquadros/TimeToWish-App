package io.github.rubenquadros.timetowish.services.auth

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import io.github.rubenquadros.timetowish.core.activity.ActivityHolder

class GoogleAuthDelegate : Auth {

    override suspend fun login(clientId: String, serverId: String): SignInResult {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleSignInOption(clientId))
            .build()

        return ActivityHolder.getActivityContext()?.let { context ->
            val manager = CredentialManager.create(context)

            try {
                val result = manager.getCredential(
                    request = request,
                    context = context
                )

                getSignInResult(result)
            } catch (e: GetCredentialException) {
                //log failure
                SignInResult.Failure
            }
        } ?: SignInResult.Failure
    }


    override suspend fun logout(): SignOutResult {
        return ActivityHolder.getActivityContext()?.let { context ->
            val manager = CredentialManager.create(context)

            runCatching {
                manager.clearCredentialState(ClearCredentialStateRequest())
                SignOutResult.Success
            }.getOrElse {
                SignOutResult.Failure
            }
        } ?: SignOutResult.Failure
    }

    private fun getGoogleSignInOption(clientId: String): GetSignInWithGoogleOption {
        return GetSignInWithGoogleOption.Builder(clientId).build()
    }

    private fun getSignInResult(result: GetCredentialResponse): SignInResult {
        return when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        SignInResult.Success(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        //log failure
                        SignInResult.Failure
                    }
                } else {
                    //log failure
                    SignInResult.Failure
                }
            }
            else -> {
                //log and do nothing
                SignInResult.Failure
            }
        }
    }
}

actual fun getGoogleAuthDelegate(): Auth = GoogleAuthDelegate()