package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListBo;

/**
 * 靶场任务提交映射
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface CrTaskSubmitMapper extends BaseMapper<CrTaskSubmit> {

    /**
     * 查询由房间id
     *
     * @param roomId 房间id
     * @return {@code List<CrTaskSubmit> }
     * @throws
     * @see String
     * @see List<CrTaskSubmit>
     */
    List<CrTaskSubmit> selectByRoomId(String roomId);

    /**
     * 查询最后由任务id
     *
     * @param taskId 任务id
     * @return {@code CrTaskSubmit }
     * @throws
     * @see String
     * @see CrTaskSubmit
     */
    CrTaskSubmit selectLastByTaskId(String taskId);

    /**
     * 列表靶场任务提交
     *
     * @param page      分页
     * @param listQuery 列表查询
     * @return {@code IPage<MissionEvaluationContext> }
     * @throws
     * @see Page
     * @see MissionEvaluationQuery
     * @see IPage<MissionEvaluationContext>
     */
    IPage<MissionEvaluationContext> listCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

    /**
     * 列表RB靶场任务提交
     *
     * @param page      分页
     * @param listQuery 列表查询
     * @return {@code IPage<MissionEvaluationContext> }
     * @throws
     * @see Page
     * @see MissionEvaluationQuery
     * @see IPage<MissionEvaluationContext>
     */
    IPage<MissionEvaluationContext> listRbCrTaskSubmit(@Param("page") Page page,
			@Param("listQuery") MissionEvaluationQuery listQuery);

    /**
     * 查询命令提交由任务
     *
     * @param resourceId 资源id
     * @return {@code List<CrTaskSubmit> }
     * @throws
     * @see String
     * @see List<CrTaskSubmit>
     */
    List<CrTaskSubmit> selectOrderedSubmitsByTask(String resourceId);

    /**
     * 查询由房间id和用户
     *
     * @param roomId  房间id
     * @param userIds 用户id列表
     * @return {@code List<CrTaskSubmit> }
     * @throws
     * @see String
     * @see List<String>
     * @see List<CrTaskSubmit>
     */
    List<CrTaskSubmit> selectByRoomIdAndUsers(String roomId, List<String> userIds);

    /**
     * 列表
     *
     * @param roomId         房间id
     * @param roleId         角色id
     * @param userId         用户id
     * @param evaluateStatus 评估状态
     * @return {@code List<EvaluationListBo> }
     * @throws
     * @see String
     * @see String
     * @see String
     * @see Integer
     * @see List<EvaluationListBo>
     */
    List<EvaluationListBo> list(@Param("roomId") String roomId, @Param("roleId") String roleId,
			@Param("userId") String userId, @Param("evaluateStatus") Integer evaluateStatus);

    /**
     * 获取任务博由id
     *
     * @param taskSubmitId 任务提交id
     * @return {@code EvaluationMissionBo }
     * @throws
     * @see String
     * @see EvaluationMissionBo
     */
    EvaluationMissionBo getMissionBoById(@Param("taskSubmitId") String taskSubmitId);
}
