package io.github.rubenquadros.timetowish.ui.image

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem

fun getImageLoader(context: PlatformContext): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder().maxSizePercent(context, percent = 0.25).build()
        }
        .diskCache {
            newDiskCache()
        }
        .apply {
            logger(DebugLogger())
        }
        .crossfade(true)
        .build()
}

private fun newDiskCache(): DiskCache {
    return DiskCache.Builder()
        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(512L * 1024 * 1034) //512MB
        .build()
}