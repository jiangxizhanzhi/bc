package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.teamtrain.controller.req.XdStatisticsReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonalStatisticsBo;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class XdStatisticsReadScopeTest {
    private XdTeamTrainStatisticsServiceImpl service;
    private TeamRoomService rooms;

    @BeforeEach
    void setUp() {
        service = new XdTeamTrainStatisticsServiceImpl();
        rooms = mock(TeamRoomService.class);
        CrTaskMapper tasks = mock(CrTaskMapper.class);
        TeamRoomMapper mapper = mock(TeamRoomMapper.class);
        RemoteUserService users = mock(RemoteUserService.class);
        ReflectionTestUtils.setField(service, "teamRoomService", rooms);
        ReflectionTestUtils.setField(service, "crTaskMapper", tasks);
        ReflectionTestUtils.setField(service, "teamRoomMapper", mapper);
        ReflectionTestUtils.setField(service, "remoteUserService", users);
        when(tasks.selectByStatusListAndType(anyList(), eq(3))).thenReturn(Arrays.asList(task("mine", 20), task("other", 30)));
        TeamRoom room = new TeamRoom(); room.setXdType(1);
        when(mapper.selectAllByIds(anyList())).thenAnswer(call -> {
            List<String> ids = call.getArgument(0);
            return new ArrayList<>(Collections.nCopies(ids.size(), room));
        });
        UserInfo user = new UserInfo(); user.setUserId("student"); user.setName("Student");
        when(users.getByIdList(anyList())).thenReturn(Collections.singletonList(user));
    }

    @Test
    void personalDetailsAggregateOnlyReadableExperiments() {
        when(rooms.getReadableRoomIds()).thenReturn(Collections.singletonList("mine"));
        ListRsp<XdTeamPersonalStatisticsBo> result = service.getAllStatisticsDetail(new TrainStatisticsDetailReq(), new Page<>(1, 10), 3);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getList().get(0).getTaskNum());
        assertEquals(20, result.getList().get(0).getTotalScore());
    }

    @Test
    void adminDetailsAggregateAllExperiments() {
        when(rooms.getReadableRoomIds()).thenReturn(null);
        ListRsp<XdTeamPersonalStatisticsBo> result = service.getAllStatisticsDetail(new TrainStatisticsDetailReq(), new Page<>(1, 10), 3);
        assertEquals(2, result.getList().get(0).getTaskNum());
        assertEquals(50, result.getList().get(0).getTotalScore());
    }

    @Test
    void globalRankingDoesNotAcquireAParticipationScope() {
        assertTrue(service.getPersonScore(new XdStatisticsReq()).isEmpty());
        verifyNoInteractions(rooms);
    }

    private CrTask task(String roomId, int score) {
        CrTask task = new CrTask(); task.setRoomId(roomId); task.setUserId("student");
        task.setStatus(1); task.setScore(score); return task;
    }
}
