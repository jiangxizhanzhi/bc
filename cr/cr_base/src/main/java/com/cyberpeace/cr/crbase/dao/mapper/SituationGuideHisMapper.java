package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.service.context.guide.AttackTimeCountGroup;
import com.cyberpeace.cr.crbase.service.context.guide.AttackTypeCountGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 态势导调历史Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface SituationGuideHisMapper extends BaseMapper<SituationGuideHistory> {

    /**
     * 计数由类型
     *
     * @param roomId 房间id
     * @param type   类型
     * @return {@code Integer }
     */
    Integer countByType(@Param("roomId") String roomId, @Param("type") Integer type);

    /**
     * 计数由类别
     *
     * @param roomId    房间id
     * @param eventType 事件类型
     * @return {@code List<AttackTypeCountGroup> }
     */
    List<AttackTypeCountGroup> countByCategory(@Param("roomId") String roomId, @Param("eventType") Integer eventType);

    /**
     * 获取事件名称列表
     *
     * @param roomId    房间id
     * @param eventType 事件类型
     * @return {@code List<String> }
     */
    List<String> getEventNameList(@Param("roomId") String roomId, @Param("eventType") Integer eventType);

    /**
     * 获取事件由屏幕
     *
     * @param sceneId   场景id
     * @param typeList  类型列表
     * @param eventType 事件类型
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param eventName 事件名称
     * @param category  类别
     * @param sourceId  源id
     * @param targetId  目标id
     * @param result    结果
     * @param role      岗位
     * @return {@code List<SituationGuideHistory> }
     */
    List<SituationGuideHistory> getEventByScreen(@Param("sceneId") String sceneId,
                                                 @Param("typeList") List<Integer> typeList,
                                                 @Param("eventType") Integer eventType,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime,
                                                 @Param("eventName") String eventName,
                                                 @Param("category") Integer category,
                                                 @Param("sourceId") String sourceId,
                                                 @Param("targetId") String targetId,
                                                 @Param("result") Integer result,
                                                 @Param("role") Integer role);

    /**
     * 计数由时间
     *
     * @param roomId    房间id
     * @param startDate 开始日期
     * @return {@code List<AttackTimeCountGroup> }
     */
    List<AttackTimeCountGroup> countByTime(@Param("roomId") String roomId, @Param("startDate") Date startDate);

    /**
     * 获取场景id列表由房间
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code String }
     */
    String getSceneIdsByRoom(@Param("roomId") String roomId, @Param("businessType") Integer businessType);

    /**
     * 获取事件次
     *
     * @param roomId 房间id
     * @return {@code List<String> }
     */
    List<String> getEventTimes(@Param("roomId") String roomId);

    /**
     * 导调记录列表由时间
     *
     * @param roomId    房间id
     * @param startDate 开始日期
     * @return {@code List<SituationGuideHistory> }
     */
    List<SituationGuideHistory> guideRecordListByTime(@Param("roomId") String roomId, @Param("startDate") Date startDate);

    /**
     * 获取拓扑id由房间
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code String }
     */
    String getTopoIdByRoom(@Param("roomId") String roomId, @Param("businessType") Integer businessType);

    /**
     * 获取复盘场景id
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code String }
     */
    String getReplaySceneId(@Param("roomId") String roomId, @Param("businessType") Integer businessType);

    /**
     * 获取事件时间由屏幕
     *
     * @param sceneId   场景id
     * @param typeList  类型列表
     * @param eventType 事件类型
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param eventName 事件名称
     * @param category  类别
     * @param sourceId  源id
     * @param targetId  目标id
     * @param result    结果
     * @param role      岗位
     * @return {@code List<String> }
     */
    List<String> getEventTimeByScreen(@Param("sceneId") String sceneId,
                                      @Param("typeList") List<Integer> typeList,
                                      @Param("eventType") Integer eventType,
                                      @Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime,
                                      @Param("eventName") String eventName,
                                      @Param("category") Integer category,
                                      @Param("sourceId") String sourceId,
                                      @Param("targetId") String targetId,
                                      @Param("result") Integer result,
                                      @Param("role") Integer role);
}
