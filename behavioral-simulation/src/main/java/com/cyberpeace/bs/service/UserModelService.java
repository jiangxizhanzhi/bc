package com.cyberpeace.bs.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.service.bo.behavior.BehaviorWithVariable;
import com.cyberpeace.bs.service.bo.model.*;
import com.cyberpeace.common.model.ListRsp;

/**
 * 用户模型Service
 *
 * @author SN
 * @date 2023/05/08
 */
public interface UserModelService {

    /**
     * 获取用户行为列表
     *
     * @param page       分页
     * @param userTypeId 用户类型
     * @param pattern    用户名模糊匹配
     * @return 用户行为分页列表
     */
    ListRsp<UserModelBo> listUserModel(Page page, String userTypeId, String pattern,
                                       Integer accessOs);

    /**
     * 获取用户行为模拟详情
     *
     * @param resourceId 编号
     * @return 用户行为模拟详情
     */
    UserModelDetail getDetail(String resourceId);

    /**
     * 新增用户行为模拟
     *
     * @param addBo 前端输入
     */
    void create(UserModelAddBo addBo);

    /**
     * 更新用户行为模拟详情
     *
     * @param modifyBo 前端输入编辑
     */
    void update(UserModelModifyBo modifyBo);

    /**
     * 删除用户应为模拟
     *
     * @param resourceIds 要删除的id
     */
    void delete(List<String> resourceIds);

    /**
     * 生成行为的时间、以及图形
     *
     * @param chartInput 输入的行为和配置
     * @return 时间和图形
     */
    ChartResult createChart(ChartInput chartInput);

    /**
     * 获取用户模型包含的行为列表，并附带行为变量值
     *
     * @param userModelId 用户模型id
     * @return 带行为变量的行为列表
     */
    List<BehaviorWithVariable> getBehaviorsWithVariables(String userModelId);

    /**
     * 获取用户模型，获取不到就抛错
     *
     * @param resourceId
     * @return
     */
    UserModel getOrException(String resourceId);

    /**
	 * 批量获取用户模型包含的行为列表，并附带行为变量值
	 * 去掉内网行为的host变量
	 *
	 * @param userModelIds
	 * @return
	 */
    List<UserModelWithVariable> getBehaviorsBatchWithVariables(List<String> userModelIds);
}
