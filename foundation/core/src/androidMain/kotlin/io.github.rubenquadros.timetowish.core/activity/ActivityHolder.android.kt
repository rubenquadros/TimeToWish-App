package io.github.rubenquadros.timetowish.core.activity

import android.content.Context
import java.lang.ref.WeakReference

actual object ActivityHolder {

    private var weakActivityRef = WeakReference<Context>(null)

    fun getActivityContext(): Context? = weakActivityRef.get()

    fun setActivityContext(context: Context) {
        weakActivityRef = WeakReference(context)
    }

    fun clear() {
        weakActivityRef = WeakReference(null)
    }
}