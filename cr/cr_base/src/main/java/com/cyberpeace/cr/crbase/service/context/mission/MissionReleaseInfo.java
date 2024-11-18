package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 任务发布信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Mission
 * @since 1.0.0
 */
@Data
public class MissionReleaseInfo extends Mission {

    /**
     * 完成用户列表
     *
     * @see List<UserData>
     */
    @ApiModelProperty(value = "已完成用户列表")
    private List<UserData>  finishUserList;
    /**
     * 未完成用户列表
     *
     * @see List<UserData>
     */
    @ApiModelProperty(value = "未完成用户列表")
    private List<UserData>  notFinishUserList;

    /**
     * is 发布
     *
     * @see Boolean
     */
    @ApiModelProperty(value = "是否下发")
    private Boolean isRelease;

    /**
     * 是动态
     *
     * @see Boolean
     */
    @ApiModelProperty(value = "是否动态新增")
    private Boolean isDynamic;
}
