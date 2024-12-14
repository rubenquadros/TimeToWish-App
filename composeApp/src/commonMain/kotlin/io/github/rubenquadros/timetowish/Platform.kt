package io.github.rubenquadros.timetowish

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform