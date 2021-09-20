package com.droidknights.app2021.data.model

import com.droidknights.app2021.shared.Level
import com.droidknights.app2021.shared.Room
import com.droidknights.app2021.shared.Tag
import com.droidknights.app2021.shared.model.User
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
internal data class SessionContainer(
    val sessions: List<SessionData> = emptyList()
)

@Serializable
data class SessionData(
    val title: String,
    val content: List<String>,
    val speakers: List<User>,
    val level: Level,
    val tags: List<Tag> = emptyList(),
    val room: Room = Room.Etc,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
)