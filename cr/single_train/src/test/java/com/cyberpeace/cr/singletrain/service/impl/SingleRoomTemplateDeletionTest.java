package com.cyberpeace.cr.singletrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.impl.LocalSceneServiceImpl;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SingleRoomTemplateDeletionTest {
    private SingleRoomServiceImpl service;
    private SingleRoomMapper mapper;
    private LocalSceneService localScenes;
    private TrainRecordService records;
    private SingleRoom room;
    private LocalScene local;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ConfigUtil.class, "ctxPropertiesMap", Collections.emptyMap());
        UserInfo administrator = new UserInfo();
        administrator.setUserId("administrator");
        UserInfoContext.setUserContext(administrator);
        service = new SingleRoomServiceImpl();
        mapper = mock(SingleRoomMapper.class);
        localScenes = mock(LocalSceneService.class);
        records = mock(TrainRecordService.class);
        TransactionTemplate transaction = mock(TransactionTemplate.class);
        doAnswer(invocation -> ((TransactionCallback<?>) invocation.getArgument(0))
                .doInTransaction(mock(TransactionStatus.class))).when(transaction).execute(any());
        ReflectionTestUtils.setField(service, "singleRoomMapper", mapper);
        ReflectionTestUtils.setField(service, "localSceneService", localScenes);
        ReflectionTestUtils.setField(service, "trainRecordService", records);
        ReflectionTestUtils.setField(service, "transactionTemplate", transaction);
        room = new SingleRoom();
        room.setResourceId("room");
        room.setLocalSceneId("local");
        room.setUserId("student");
        local = new LocalScene();
        local.setMpSceneId("mp-scene");
        local.setBusinessInstanceId("template_single_student");
        when(localScenes.getById("local")).thenReturn(local);
        when(localScenes.getSceneData("mp-scene", "student")).thenReturn(scene(1, 4));
    }

    @AfterEach
    void clearUser() {
        UserInfoContext.removeUserContext();
    }

    @Test
    void closesSceneAsItsOwnerThenClosesRoomAndEndsTraining() {
        when(localScenes.stopScene(local, "student")).thenReturn(true);
        service.deleteRoomForTemplateDeletion(room);
        InOrder order = inOrder(localScenes, mapper, records);
        order.verify(localScenes).stopScene(local, "student");
        order.verify(localScenes).removeById("local");
        order.verify(mapper).deleteById("room");
        order.verify(records).updateEndTimeAndExtra("room", "");
        assertEquals("administrator", UserInfoContext.getUserContext().getUserId());
    }

    @Test
    void falseStopResultKeepsLocalRoomAndHistory() {
        when(localScenes.stopScene(local, "student")).thenReturn(false);
        assertThrows(BusinessException.class, () -> service.deleteRoomForTemplateDeletion(room));
        verify(localScenes, never()).removeById(anyString());
        verifyNoInteractions(mapper, records);
    }

    @Test
    void networkFailureKeepsLocalRoomAndHistory() {
        when(localScenes.stopScene(local, "student")).thenThrow(new RemoteServiceException("connection refused"));
        assertThrows(RemoteServiceException.class, () -> service.deleteRoomForTemplateDeletion(room));
        verify(localScenes, never()).removeById(anyString());
        verifyNoInteractions(mapper, records);
    }

    @ParameterizedTest
    @ValueSource(strings = {ErrorCode.SCENE_NOT_EXIST_ZH, ErrorCode.SCENE_NOT_EXIST_EN})
    void sceneAlreadyDeletedAllowsStaleRoomCleanup(String message) {
        when(localScenes.stopScene(local, "student")).thenThrow(new RemoteServiceException(message));
        service.deleteRoomForTemplateDeletion(room);
        verify(mapper).deleteById("room");
        verify(records).updateEndTimeAndExtra("room", "");
    }

    @Test
    void missingLocalSceneAllowsStaleRoomCleanup() {
        when(localScenes.getById("local")).thenReturn(null);
        service.deleteRoomForTemplateDeletion(room);
        verify(localScenes, never()).stopScene(any(LocalScene.class), anyString());
        verify(mapper).deleteById("room");
        verify(records).updateEndTimeAndExtra("room", "");
    }

    @Test
    void stoppedSceneIsNotStoppedAgain() {
        when(localScenes.getSceneData("mp-scene", "student")).thenReturn(scene(9, 4));
        service.deleteRoomForTemplateDeletion(room);
        verify(localScenes, never()).stopScene(any(LocalScene.class), anyString());
        verify(mapper).deleteById("room");
        verify(records).updateEndTimeAndExtra("room", "");
    }

    @ParameterizedTest
    @ValueSource(strings = {ErrorCode.SCENE_NOT_EXIST_ZH, ErrorCode.SCENE_NOT_EXIST_EN})
    void sceneAlreadyMissingAtQuerySkipsTheStopRequest(String message) {
        when(localScenes.getSceneData("mp-scene", "student"))
                .thenThrow(new RemoteServiceException(message));
        service.deleteRoomForTemplateDeletion(room);
        verify(localScenes, never()).stopScene(any(LocalScene.class), anyString());
        verify(mapper).deleteById("room");
        verify(records).updateEndTimeAndExtra("room", "");
    }

    @Test
    void missingSceneDataSkipsTheStopRequest() {
        when(localScenes.getSceneData("mp-scene", "student")).thenReturn(null);
        service.deleteRoomForTemplateDeletion(room);
        verify(localScenes, never()).stopScene(any(LocalScene.class), anyString());
        verify(mapper).deleteById("room");
    }

    @Test
    void falseStopResultIsAcceptedIfAnotherRequestHasAlreadyClosedTheScene() {
        when(localScenes.getSceneData("mp-scene", "student"))
                .thenReturn(scene(1, 4), scene(9, 4));
        when(localScenes.stopScene(local, "student")).thenReturn(false);
        service.deleteRoomForTemplateDeletion(room);
        verify(localScenes, times(2)).getSceneData("mp-scene", "student");
        verify(mapper).deleteById("room");
    }

    @Test
    void falseStopResultIsAcceptedIfTheSceneDisappearsBeforeVerification() {
        when(localScenes.getSceneData("mp-scene", "student"))
                .thenReturn(scene(1, 4)).thenThrow(new RemoteServiceException(ErrorCode.SCENE_NOT_EXIST_ZH));
        when(localScenes.stopScene(local, "student")).thenReturn(false);
        service.deleteRoomForTemplateDeletion(room);
        verify(mapper).deleteById("room");
    }

    @Test
    void unavailableSceneStatusDoesNotBecomeAnAlreadyClosedScene() {
        when(localScenes.getSceneData("mp-scene", "student"))
                .thenThrow(new RemoteServiceException("connection refused"));
        assertThrows(RemoteServiceException.class, () -> service.deleteRoomForTemplateDeletion(room));
        verify(localScenes, never()).stopScene(any(LocalScene.class), anyString());
        verify(localScenes, never()).removeById(anyString());
        verifyNoInteractions(mapper, records);
    }

    @Test
    void deletionNotificationKeepsTheSingleSuffixInTheTemplateId() {
        service.getByBusinessId("template_single_student");
        verify(mapper).getSingleRoom("template_single", "student");
    }

    @Test
    void alreadyClosedRoomDoesNotBreakTheDeletionNotification() {
        SingleRoomServiceImpl roomService = mock(SingleRoomServiceImpl.class);
        SingleSceneEventListener listener = new SingleSceneEventListener();
        ReflectionTestUtils.setField(listener, "singleRoomService", roomService);
        assertTrue(listener.onSceneDelete("mp-scene", "template_single_student", null));
        verify(roomService, never()).clearLocal(any());
    }

    @Test
    void crossAccountSceneClosureKeepsTheExistingDemonstrationRestriction() {
        ApplicationContext previous = SpringContextUtil.getApplicationContext();
        ApplicationContext context = mock(ApplicationContext.class);
        RemoteUserService users = mock(RemoteUserService.class);
        RemoteSceneService scenes = mock(RemoteSceneService.class);
        when(context.getBean(RemoteUserService.class)).thenReturn(users);
        new SpringContextUtil().setApplicationContext(context);
        try {
            LocalSceneServiceImpl localService = new LocalSceneServiceImpl();
            ReflectionTestUtils.setField(localService, "remoteSceneService", scenes);
            when(scenes.stopScene(any())).thenReturn(true);
            assertTrue(localService.stopScene(local, "student"));
            ArgumentCaptor<SceneOpsContext> request = ArgumentCaptor.forClass(SceneOpsContext.class);
            verify(scenes).stopScene(request.capture());
            assertEquals("student", request.getValue().getContext().getAccess_user_id());
            assertEquals("mp-scene", request.getValue().getId());
            when(users.isDemoOpen()).thenReturn(true);
            ThreadLocalContext.whiteUpdateThreadLocal.set(false);
            assertThrows(BusinessException.class, () -> localService.stopScene(local, "student"));
            verify(scenes, times(1)).stopScene(any());
        } finally {
            new SpringContextUtil().setApplicationContext(previous);
            ThreadLocalContext.whiteUpdateThreadLocal.remove();
        }
    }

    private JSONObject scene(int event, int status) {
        return JSON.parseObject("{\"topology\":{\"scene\":{\"event\":" + event
                + ",\"event_status\":" + status + "}}}");
    }
}
