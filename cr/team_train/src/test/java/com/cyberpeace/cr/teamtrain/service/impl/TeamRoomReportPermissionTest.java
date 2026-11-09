package com.cyberpeace.cr.teamtrain.service.impl;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.teamtrain.controller.OutInterfaceController;
import com.cyberpeace.cr.teamtrain.controller.TeamTrainRoomController;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamRoomReportPermissionTest {
    private TeamRoomService rooms;
    private TeamTrainRoomController controller;
    private OutInterfaceController external;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap", Collections.emptyMap());
        rooms = mock(TeamRoomService.class);
        doThrow(new BusinessException()).when(rooms).checkReadPermission("other");
        controller = new TeamTrainRoomController();
        external = new OutInterfaceController();
        ReflectionTestUtils.setField(controller, "teamRoomService", rooms);
        ReflectionTestUtils.setField(external, "teamRoomService", rooms);
        UserInfoContext.setUserContext(new UserInfo());
    }

    @AfterEach
    void clearUser() { UserInfoContext.removeUserContext(); }

    @Test
    void detailRejectsOtherRoomsBeforeReadingData() {
        assertThrows(BusinessException.class, () -> controller.getTeamRoom("other"));
        verify(rooms).checkReadPermission("other");
        verifyNoMoreInteractions(rooms);
    }

    @Test
    void bothReportRoutesRejectOtherRoomsBeforeReadingData() {
        assertThrows(BusinessException.class, () -> controller.getTrainReports("other", 10));
        assertThrows(BusinessException.class, () -> external.getTrainReports("other", 10));
        verify(rooms, times(2)).checkReadPermission("other");
        verifyNoMoreInteractions(rooms);
    }

    @Test
    void pdfAndDocxCannotBypassReportPermission() {
        assertThrows(BusinessException.class, () -> controller.TrainReportsPdf(null, "other", 10));
        assertThrows(BusinessException.class, () -> controller.TrainReportsDocx(null, "other", 10));
        verify(rooms, times(2)).checkReadPermission("other");
        verifyNoMoreInteractions(rooms);
    }

    @Test
    void scopedExternalRoutesRequireAuthenticatedLogin() {
        for (Method method : OutInterfaceController.class.getDeclaredMethods()) {
            if (method.getName().equals("list") || method.getName().equals("getAllDetail")
                    || method.getName().equals("getTrainReports")) {
                assertNotNull(method.getAnnotation(RequiresAuthentication.class), method.getName());
            }
        }
    }
}
