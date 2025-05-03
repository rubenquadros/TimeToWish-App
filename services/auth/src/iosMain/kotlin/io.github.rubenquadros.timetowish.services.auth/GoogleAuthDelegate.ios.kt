package io.github.rubenquadros.timetowish.services.auth

import cocoapods.GoogleSignIn.GIDConfiguration
import cocoapods.GoogleSignIn.GIDSignIn
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UIKit.UIApplication
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
class GoogleAuthDelegate : Auth {

    override suspend fun login(clientId: String, serverId: String): SignInResult {
        val rootViewController =
            UIApplication.sharedApplication.keyWindow?.rootViewController ?: return SignInResult.Failure

        GIDSignIn.sharedInstance.configuration = GIDConfiguration(clientId, serverId)

        val result = suspendCancellableCoroutine { continuation ->
            GIDSignIn.sharedInstance.signInWithPresentingViewController(rootViewController) { gidSignInResult, nsError ->
                if (gidSignInResult?.user != null && nsError == null) {
                    continuation.resume(SignInResult.Success(gidSignInResult.user.idToken?.tokenString.orEmpty()))
                } else {
                    continuation.resume(SignInResult.Failure)
                }
            }
        }

        return result
    }

    override suspend fun logout(): SignOutResult {
        GIDSignIn.sharedInstance.signOut()

        return SignOutResult.Success
    }

}

actual fun getGoogleAuthDelegate(): Auth = GoogleAuthDelegate()