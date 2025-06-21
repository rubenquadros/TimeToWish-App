package io.github.rubenquadros.timetowish.feature.generatewish.data.datasource

import io.github.rubenquadros.timetowish.core.base.MemoryDataSource
import io.github.rubenquadros.timetowish.feature.generatewish.data.model.Chat
import org.koin.core.annotation.Single

@Single
internal class ChatMemoryDataSource : MemoryDataSource<List<Chat>>()

