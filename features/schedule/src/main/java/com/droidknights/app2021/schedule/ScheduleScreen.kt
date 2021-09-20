package com.droidknights.app2021.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidknights.app2021.core.ui.compose.util.toColor
import com.droidknights.app2021.shared.Room
import com.droidknights.app2021.shared.model.Session
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun ScheduleScreen(
    sessions: List<Session>,
    onSessionClick: (Session) -> Unit = {}
) {
    val groupingSession = sessions.groupBy {
        it.room
    }

    val rooms = sortedRoomList(groupingSession = groupingSession)
    val pagerState = rememberPagerState(pageCount = rooms.size)

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rooms[pagerState.currentPage].name,
                color = "#2F2E32".toColor(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = "#4cc786".toColor(),
                inactiveColor = "#4cc786".toColor().copy(alpha = 0.3f),
                indicatorWidth = 10.dp
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            Schedules(
                sessions = groupingSession[rooms.elementAt(page)].orEmpty(),
                onSessionClick = onSessionClick
            )
        }
    }
}

@Composable
private fun Schedules(
    modifier: Modifier = Modifier,
    sessions: List<Session>,
    onSessionClick: (Session) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(sessions) { session ->
            SessionUi(
                session = session,
                onSessionClick = onSessionClick
            )
            Divider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = "#EFEFEF".toColor()
            )
        }
    }
}

private fun sortedRoomList(groupingSession: Map<Room, List<Session>>): List<Room> {
    val sortedRoomList = mutableListOf<Room>()
    if (groupingSession.containsKey(Room.Track1)) {
        sortedRoomList.add(Room.Track1)
    }
    if (groupingSession.containsKey(Room.Track2)) {
        sortedRoomList.add(Room.Track2)
    }
    if (groupingSession.containsKey(Room.Etc)) {
        sortedRoomList.add(Room.Etc)
    }
    return sortedRoomList.toList()
}
