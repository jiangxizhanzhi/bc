package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.guide.*;
import com.cyberpeace.cr.crbase.controller.rsp.guide.*;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.service.context.guide.MpEvent;
import com.cyberpeace.integratation.model.scene.SceneEventContext;

import java.util.List;

/**
 * 态势导调Service
 *
 * @author SN
 * @date 2024/11/13
 */
public interface SituationGuideService {

    /**
     * 列表由房间id
     *
     * @param req 请求体
     * @return {@code ListRsp<SituationGuideListRsp> }
     */
    ListRsp<SituationGuideListRsp> listByRoomId(ListGuideSchemeReq req);

    /**
     * 新增导调方案
     *
     * @param addGuideSchemeReq 新增导调方案请求体
     * @return {@code String }
     */
    String addGuideScheme(AddGuideSchemeReq addGuideSchemeReq);

    /**
     * 获取导调方案详情
     *
     * @param resourceId 资源id
     * @return {@code SituationGuideRsp }
     */
    SituationGuideRsp getGuideSchemeDetail(String resourceId);

    /**
     * 更新导调方案
     *
     * @param updateGuideSchemeReq 更新导调方案请求体
     * @return {@code UpdateGuideSchemeReq }
     */
    UpdateGuideSchemeReq updateGuideScheme(UpdateGuideSchemeReq updateGuideSchemeReq);

    /**
     * 删除导调方案
     *
     * @param ids id列表
     * @return {@code Boolean }
     */
    Boolean deleteGuideScheme(List<String> ids);

    /**
     * 导调实现
     *
     * @param req 请求体
     * @return {@code Boolean }
     */
    Boolean guideImplement(GuideImplementReq req);

    /**
     * 导调测试
     *
     * @param req 请求体
     * @return {@code Boolean }
     */
    Boolean guideTest(AddGuideSchemeReq req);

    /**
     * 获取导调历史由任务id
     *
     * @param missionId 任务id
     * @param taskId    任务id
     * @return {@code SituationGuideHistory }
     */
    SituationGuideHistory getGuideHisByMissionId(String missionId, String taskId);

    /**
     * 推送数据自房间由导调
     *
     * @param isInstance 是否实例
     * @param his        历史
     * @return {@code Boolean }
     */
    Boolean pushDataToRoomByGuide(Boolean isInstance, SituationGuideHistory his);

    /**
     * 攻击 top5
     *
     * @param roomId 房间id
     * @return {@code List<AttackTopFiveRsp> }
     */
    List<AttackTopFiveRsp> attackTop5(String roomId);

    /**
     * 任务导调
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code List<GuideRoleStageRsp> }
     */
    List<GuideRoleStageRsp> missionGuide(String roomId, Integer businessType);

    /**
     * 导调统计学
     *
     * @param roomId 房间id
     * @return {@code GuideStatisticsRsp }
     */
    GuideStatisticsRsp guideStatistics(String roomId);

    /**
     * 导调时间分配
     *
     * @param roomId 房间id
     * @return {@code GuideTimeDistributionRsp }
     */
    GuideTimeDistributionRsp guideTimeDistribution(String roomId);

    /**
     * 获取参数类型
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code GuideParamTypeRsp }
     */
    GuideParamTypeRsp getParamType(String roomId, Integer businessType);

    /**
     * 列表模型
     *
     * @return {@code SituationModelRsp }
     */
    SituationModelRsp listModel();

    /**
     * 导调记录
     *
     * @param pageReq 分页请求体
     * @return {@code ListRsp<GuideHistoryRsp> }
     */
    ListRsp<GuideHistoryRsp> guideRecord(GuideRecordReq pageReq);

    /**
     * 获取中台事件列表
     *
     * @param screenGuideHistoryReq 屏幕导调历史请求体
     * @return {@code List<MpEvent> }
     */
    List<MpEvent> getMpEventList(ScreenGuideHistoryReq screenGuideHistoryReq);

    /**
     * 中台事件自导调历史响应
     *
     * @param mpEventList 中台事件列表
     * @return {@code List<GuideHistoryRsp> }
     */
    List<GuideHistoryRsp> mpEventToGuideHistoryRsp(List<MpEvent> mpEventList);

    /**
     * 导调历史响应自态势导调历史
     *
     * @param guideHistoryRspList 导调历史响应列表
     * @return {@code List<SituationGuideHistory> }
     */
    List<SituationGuideHistory> guideHistoryRspToSituationGuideHistory(List<ExecGuideHisReq> guideHistoryRspList);

    /**
     * 屏幕导调历史请求体自场景事件上下文
     *
     * @param screenGuideHistoryTimeLineReq 屏幕导调历史时间线请求体
     * @return {@code SceneEventContext }
     */
    SceneEventContext screenGuideHistoryReqToSceneEventContext(ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq);
}
