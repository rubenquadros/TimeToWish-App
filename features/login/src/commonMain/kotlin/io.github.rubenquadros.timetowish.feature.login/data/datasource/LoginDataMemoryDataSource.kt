package io.github.rubenquadros.timetowish.feature.login.data.datasource

import io.github.rubenquadros.timetowish.core.base.memory.MemoryDataSource
import io.github.rubenquadros.timetowish.feature.login.data.model.GetLoginDataResponse
import org.koin.core.annotation.Single

@Single
internal class LoginDataMemoryDataSource : MemoryDataSource<GetLoginDataResponse>()