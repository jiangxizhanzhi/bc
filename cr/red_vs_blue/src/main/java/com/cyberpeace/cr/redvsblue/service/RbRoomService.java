package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;

/**
 * 红蓝对抗房间Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface RbRoomService extends IService<RbRoom> {

    /**
     * 获取训练状态
     *
     * @param roomId 红蓝对抗实例id
     * @return 训练状态
     * @throws
     * @see String
     * @see Integer
     */
    Integer getTrainingStatus(String roomId);

    /**
     * 根据ids批量获取
     *
     * @param roomIds
     * @return
     * @throws
     * @see List<String>
     * @see List<RbRoom>
     */
    List<RbRoom> getByIds(List<String> roomIds);

    /**
     * 获取所有红蓝房间
     *
     * @return
     * @throws
     * @see List<RbRoom>
     */
    List<RbRoom> getAll();

    /**
     * 列表查询红蓝对抗实例
     *
     * @param listQuery 分页查询体
     * @return 红蓝对抗实例
     * @throws
     * @see ListQuery
     * @see IPage<RbRoomData>
     */
    IPage<RbRoomData> list(ListQuery listQuery);

    /**
     * 获取当前红蓝房间数量
     *
     * @return
     * @throws
     * @see Integer
     */
    Integer countAll();

    /**
     * 创建红蓝对抗实例
     *
     * @param rbRoomContext 红蓝对抗数据
     * @return 红蓝对抗实例
     * @throws
     * @see RbRoomContext
     * @see RbRoom
     */
    RbRoom create(RbRoomContext rbRoomContext);

    /**
     * 获取红蓝对抗实例数据
     *
     * @param resourceId 红蓝对抗实例id
     * @return 红蓝对抗实例数据
     * @throws
     * @see String
     * @see RbRoomData
     */
    RbRoomData getRbRoomDataByRoomId(String resourceId);

    /**
     * 获取聊天面板信息
     *
     * @param crRole     角色
     * @param resourceId 红蓝对抗实例id
     * @param userId     用户id
     * @return 聊天面板信息
     * @throws
     * @see CrRole
     * @see String
     * @see String
     * @see DashboardInfo
     */
    DashboardInfo getDashboardInfo(CrRole crRole, String resourceId, String userId);

    /**
     * 更新红蓝对抗实例
     *
     * @param rbRoomContext 红蓝对抗数据
     * @param rbRoom        红蓝对抗实例
     * @throws
     * @see RbRoomContext
     * @see RbRoom
     */
    void updateRbRoom(RbRoomContext rbRoomContext, RbRoom rbRoom);

    /**
     * 校验导演权限
     *
     * @param roomId 红蓝对抗实例id
     * @param userId 用户id
     * @throws
     * @see String
     * @see String
     */
    void checkDirectorPermission(String roomId, String userId);

    /**
     * 校验裁判权限
     *
     * @param roomId 红蓝对抗实例id
     * @param userId 用户id
     * @throws
     * @see String
     * @see String
     */
    void checkRefereePermission(String roomId, String userId);

    /**
     * 校验训练是否运行
     *
     * @param roomId 红蓝对抗实例id
     * @throws
     * @see String
     */
    void checkRoomOpening(String roomId);

    /**
     * 校验学员权限
     *
     * @param roomId 红蓝对抗实例id
     * @param userId 用户id
     * @throws
     * @see String
     * @see String
     */
    void checkStudentPermission(String roomId, String userId);

    /**
     * 获取用户列表
     *
     * @param roomId 红蓝对抗实例id
     * @return 相关用户列表
     * @throws
     * @see String
     * @see List<UserRole>
     */
    List<UserRole> getUserList(String roomId);

    /**
     * 获取时间线数据
     *
     * @param roomId 红蓝对抗实例id
     * @return 时间线数据
     * @throws
     * @see String
     * @see TimeLineData
     */
    TimeLineData getMissionTimeline(String roomId);

    /**
     * 校验用户权限
     *
     * @param roomId 红蓝对抗实例id
     * @param userId 用户id
     * @return 用户在红蓝对抗实例中的角色
     * @throws
     * @see String
     * @see String
     * @see CrRole
     */
    CrRole checkRoomPermission(String roomId, String userId);

    /**
     * 校验是否有操作场景的权限
     *
     * @param roomId 红蓝对抗实例id
     * @param userId 用户id
     * @throws
     * @see String
     * @see String
     */
    void checkTeacherPermission(String roomId, String userId);

    /**
     * 获取聊天面板用户列表
     *
     * @param roleId 角色id
     * @param roomId 红蓝对抗实例id
     * @return 聊天面板用户列表
     * @throws
     * @see String
     * @see String
     * @see List<DashboardUser>
     */
    List<DashboardUser> getMembersInfo(String roleId, String roomId);

    /**
     * 校验实例是否未关闭
     *
     * @param roomId 红蓝对抗实例id
     * @throws
     * @see String
     */
    void checkRoomUnclosed(String roomId);

    /**
     * 获取学生角色
     *
     * @param roomId 训练id
     * @return {@code List<CrRole>}
     * @throws
     * @see String
     * @see List<CrRole>
     */
    List<CrRole> getStudentsRoles(String roomId);

    /**
     * 获取推荐训练时长
     *
     * @param roomId 房间id
     * @return {@code Integer}
     * @throws
     * @see String
     * @see Integer
     */
    Integer getDuration(String roomId);

    /**
     * 获取或异常
     *
     * @param roomId 房间id
     * @return {@code RbRoom}
     * @throws
     * @see String
     * @see RbRoom
     */
    RbRoom getOrExceptionById(String roomId);

    /**
     * 获取复盘时间轴
     *
     * @param roomId 房间id
     * @param userId 用户id
     * @return {@code TimeLineData}
     * @throws
     * @see String
     * @see String
     * @see TimeLineData
     */
    TimeLineData getReplayTimeline(String roomId, String userId);

    /**
     * 获取学员时间轴
     *
     * @param roomId 房间id
     * @return {@code TimeLineData}
     * @throws
     * @see String
     * @see TimeLineData
     */
    TimeLineData getStudentsTimeLine(String roomId);

    /**
     * 房间列表
     *
     * @param listQuery 列表查询
     * @return {@code ListRsp<RbRoomRsp>}
     * @throws
     * @see ListQuery
     * @see ListRsp<RbRoomRsp>
     */
    ListRsp<RbRoomRsp> listRoom(ListQuery listQuery);

    /**
     * 获取房间详情
     *
     * @param roomId 房间id
     * @return {@code RbRoomRsp}
     * @throws
     * @see String
     * @see RbRoomRsp
     */
    RbRoomRsp getRoomRsp(String roomId);
}
