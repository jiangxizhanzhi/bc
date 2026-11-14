package com.cyberpeace.cr.singletrain.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.singletrain.controller.req.SingleTemplateDeleteReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateRoomUser;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SingleTemplateDeletionTest {
    private SingleBusinessTemplateHandlerImpl handler;
    private BusinessTemplateService templates;
    private SingleRoomService rooms;
    private RemoteUserService users;
    private SingleRoom firstRoom;
    private SingleRoom secondRoom;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap", Collections.emptyMap());
        UserInfo administrator = new UserInfo();
        administrator.setUserId("administrator");
        UserInfoContext.setUserContext(administrator);
        handler = new SingleBusinessTemplateHandlerImpl();
        templates = mock(BusinessTemplateService.class);
        rooms = mock(SingleRoomService.class);
        users = mock(RemoteUserService.class);
        ReflectionTestUtils.setField(handler, "businessTemplateService", templates);
        ReflectionTestUtils.setField(handler, "singleRoomService", rooms);
        ReflectionTestUtils.setField(handler, "remoteUserService", users);
        BusinessTemplate template = new BusinessTemplate();
        template.setResourceId("template_single");
        template.setName("Training");
        template.setBusinessType(BusinessType.TRAINING_SINGLE.getCode());
        when(templates.getById("template_single")).thenReturn(template);
        firstRoom = room("room-1", "student-1");
        secondRoom = room("room-2", "student-2");
        when(rooms.getByBusinessTemplateId("template_single"))
                .thenReturn(Arrays.asList(firstRoom, secondRoom));
    }

    @AfterEach
    void clearUser() {
        UserInfoContext.removeUserContext();
    }

    @Test
    void previewListsAllOwnersAndRetainsMissingUserIdsWithoutClosingAnything() {
        UserInfo student = new UserInfo();
        student.setUserId("student-1");
        student.setUserName("student01");
        student.setName("Student One");
        when(users.getIdUserInfoMap(Arrays.asList("student-1", "student-2")))
                .thenReturn(Collections.singletonMap("student-1", student));
        List<SingleTemplateRoomUser> result = handler.getDeleteRooms(Collections.singletonList("template_single"));
        assertEquals(2, result.size());
        assertEquals("student01", result.get(0).getUserName());
        assertEquals("Student One", result.get(0).getName());
        assertEquals("student-2", result.get(1).getUserName());
        assertEquals("room-2", result.get(1).getRoomId());
        verify(templates).checkPermission("template_single", "administrator");
        verify(rooms, never()).deleteRoomForTemplateDeletion(any());
        verify(templates, never()).batchDelete(any());
    }

    @Test
    void unconfirmedRoomsCannotBeClosed() {
        assertThrows(BusinessException.class, () -> handler.deleteTemplates(
                Collections.singletonList("template_single"), Collections.emptyList()));
        verify(rooms, never()).deleteRoomForTemplateDeletion(any());
        verify(templates, never()).batchDelete(any());
    }

    @Test
    void roomCreatedAfterPreviewStopsDeletionBeforeAnyOtherRoomIsClosed() {
        assertThrows(BusinessException.class, () -> handler.deleteTemplates(
                Collections.singletonList("template_single"), Collections.singletonList("room-1")));
        verify(rooms, never()).deleteRoomForTemplateDeletion(any());
        verify(templates, never()).batchDelete(any());
    }

    @Test
    void confirmedRoomsAreClosedBeforeTheTemplateIsDeleted() {
        List<String> ids = Collections.singletonList("template_single");
        handler.deleteTemplates(ids, Arrays.asList("room-1", "room-2"));
        InOrder order = inOrder(rooms, templates);
        order.verify(templates).checkPermission("template_single", "administrator");
        order.verify(rooms).getByBusinessTemplateId("template_single");
        order.verify(rooms).deleteRoomForTemplateDeletion(firstRoom);
        order.verify(rooms).deleteRoomForTemplateDeletion(secondRoom);
        order.verify(templates).batchDelete(ids);
    }

    @Test
    void failedRoomClosureKeepsTheTemplate() {
        doThrow(new IllegalStateException("MP unavailable"))
                .when(rooms).deleteRoomForTemplateDeletion(firstRoom);
        assertThrows(IllegalStateException.class, () -> handler.deleteTemplates(
                Collections.singletonList("template_single"), Arrays.asList("room-1", "room-2")));
        verify(templates, never()).batchDelete(any());
        verify(rooms, never()).deleteRoomForTemplateDeletion(secondRoom);
    }

    @Test
    void noRemainingRoomsAllowsNormalDeletion() {
        when(rooms.getByBusinessTemplateId("template_single")).thenReturn(Collections.emptyList());
        handler.deleteTemplates(Collections.singletonList("template_single"), Collections.emptyList());
        verify(templates).batchDelete(Collections.singletonList("template_single"));
        verify(rooms, never()).deleteRoomForTemplateDeletion(any());
    }

    @Test
    void roomsClosedAfterConfirmationAreSkipped() {
        when(rooms.getByBusinessTemplateId("template_single"))
                .thenReturn(Collections.singletonList(secondRoom));
        handler.deleteTemplates(Collections.singletonList("template_single"), Arrays.asList("room-1", "room-2"));
        verify(rooms, never()).deleteRoomForTemplateDeletion(firstRoom);
        verify(rooms).deleteRoomForTemplateDeletion(secondRoom);
        verify(templates).batchDelete(Collections.singletonList("template_single"));
    }

    @Test
    void retryAfterPartialClosureProcessesOnlyTheRemainingRoom() {
        List<String> ids = Collections.singletonList("template_single");
        List<String> confirmed = Arrays.asList("room-1", "room-2");
        when(rooms.getByBusinessTemplateId("template_single"))
                .thenReturn(Arrays.asList(firstRoom, secondRoom))
                .thenReturn(Collections.singletonList(secondRoom));
        doThrow(new IllegalStateException("MP unavailable")).doNothing()
                .when(rooms).deleteRoomForTemplateDeletion(secondRoom);
        assertThrows(IllegalStateException.class, () -> handler.deleteTemplates(ids, confirmed));
        verify(templates, never()).batchDelete(any());
        handler.deleteTemplates(ids, confirmed);
        verify(rooms, times(1)).deleteRoomForTemplateDeletion(firstRoom);
        verify(rooms, times(2)).deleteRoomForTemplateDeletion(secondRoom);
        verify(templates, times(1)).batchDelete(ids);
    }

    @Test
    void allTemplatesAreAuthorizedBeforeClosingAnyRoom() {
        BusinessTemplate other = new BusinessTemplate();
        other.setResourceId("other");
        other.setBusinessType(BusinessType.TRAINING_SINGLE.getCode());
        when(templates.getById("other")).thenReturn(other);
        doThrow(new IllegalStateException("permission denied"))
                .when(templates).checkPermission("other", "administrator");
        assertThrows(IllegalStateException.class, () -> handler.deleteTemplates(
                Arrays.asList("template_single", "other"), Arrays.asList("room-1", "room-2")));
        verify(rooms, never()).getByBusinessTemplateId(anyString());
        verify(rooms, never()).deleteRoomForTemplateDeletion(any());
    }

    @Test
    void otherTemplateTypesCannotUseTheSingleTrainingDeleteEndpoint() {
        BusinessTemplate other = new BusinessTemplate();
        other.setResourceId("team");
        other.setBusinessType(BusinessType.TRAINING_TEAM.getCode());
        when(templates.getById("team")).thenReturn(other);
        assertThrows(BusinessException.class, () -> handler.getDeleteRooms(Collections.singletonList("team")));
        verifyNoInteractions(rooms, users);
    }

    @Test
    void snakeCaseRequestCarriesTheConfirmedRoomSnapshot() throws Exception {
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        SingleTemplateDeleteReq req = mapper.readValue(
                "{\"ids\":[\"template_single\"],\"room_ids\":[\"room-1\"]}", SingleTemplateDeleteReq.class);
        assertEquals(Collections.singletonList("room-1"), req.getRoomIds());
    }

    private SingleRoom room(String id, String userId) {
        SingleRoom room = new SingleRoom();
        room.setResourceId(id);
        room.setUserId(userId);
        return room;
    }
}
