package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import org.apache.shiro.authz.UnauthenticatedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TeamRoomReadPermissionTest {
    private TeamRoomServiceImpl service;
    private TeamRoomMapper mapper;
    private UserInfo user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap", Collections.emptyMap());
        service = new TeamRoomServiceImpl();
        mapper = mock(TeamRoomMapper.class);
        ReflectionTestUtils.setField(service, "teamRoomMapper", mapper);
        user = new UserInfo();
        user.setUserId("student");
        UserInfoContext.setUserContext(user);
    }

    @AfterEach
    void clearUser() {
        UserInfoContext.removeUserContext();
    }

    @Test
    void participationIsAppliedBeforePaginationAndOverridesCallerIds() {
        when(mapper.selectParticipatingRoomIds("student")).thenReturn(Arrays.asList("mine", "shared"));
        when(mapper.listTeamRoom(any(), any())).thenReturn(new Page<>());
        ListQuery query = new ListQuery();
        query.setIds(Collections.singletonList("other"));
        service.list(query, true);
        assertEquals(Arrays.asList("mine", "shared"), query.getIds());
        verify(mapper).listTeamRoom(any(), same(query));
        assertDoesNotThrow(() -> service.checkReadPermission("mine"));
        assertThrows(BusinessException.class, () -> service.checkReadPermission("other"));
    }

    @Test
    void noParticipationProducesEmptyScope() {
        when(mapper.selectParticipatingRoomIds("student")).thenReturn(Collections.emptyList());
        assertTrue(service.getReadableRoomIds().isEmpty());
        assertThrows(BusinessException.class, () -> service.checkReadPermission("other"));
    }

    @Test
    void administratorsCanReadAllRoomsWithoutParticipation() {
        user.setAdmin(true);
        assertNull(service.getReadableRoomIds());
        assertDoesNotThrow(() -> service.checkReadPermission("other"));
        verifyNoInteractions(mapper);
    }

    @Test
    void superusersCanReadAllRoomsWithoutParticipation() {
        user.setSuperuser(true);
        assertNull(service.getReadableRoomIds());
        assertDoesNotThrow(() -> service.checkReadPermission("other"));
        verifyNoInteractions(mapper);
    }

    @Test
    void missingLoginCannotBecomeAnUnrestrictedQuery() {
        UserInfoContext.removeUserContext();
        assertThrows(UnauthenticatedException.class, service::getReadableRoomIds);
        assertThrows(UnauthenticatedException.class, () -> service.checkReadPermission("other"));
    }
}
