package io.github.rubenquadros.timetowish.shared.presentation.webPage

import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIApplication
import platform.UIKit.UINavigationController
import platform.UIKit.UITabBarController
import platform.UIKit.UIViewController

actual fun displayContent(url: String) {

    fun getTopViewController(): UIViewController? {
        val keyWindow = UIApplication.sharedApplication.keyWindow ?: return null
        var topController = keyWindow.rootViewController ?: return null

        while (true) {
            topController = when {
                topController is UINavigationController -> {
                    topController.visibleViewController ?: break
                }

                topController is UITabBarController -> {
                    topController.selectedViewController ?: break
                }

                topController.presentedViewController != null -> {
                    topController.presentedViewController ?: break
                }

                else -> break
            }
        }

        return topController
    }

    val urlNs = NSURL.URLWithString(url) ?: return

    val safariViewController = SFSafariViewController(uRL = urlNs)
    val controller = getTopViewController()

    controller?.presentViewController(
        viewControllerToPresent = safariViewController,
        animated = true,
        completion = null
    )
}