
package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonFileResponseEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.YXUserService;
import com.cyberpeace.integratation.service.bo.YxRvbUserColl;
import com.cyberpeace.integratation.service.bo.YxUserInfo;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameApplicationMapper;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameOrderMapper;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameReleaseMapper;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.ActionFrameStep;
import com.cyberpeace.space.service.context.frame.ApplicationUser;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 空间行动框架行为依赖Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionFrameApplicationService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceActionFrameApplicationServiceImpl extends ServiceImpl<SpaceActionFrameApplicationMapper, SpaceActionFrameApplication> implements SpaceActionFrameApplicationService {

	@Autowired
	private SpaceActionFrameService frameService;

	@Autowired
	private SpaceActionFrameReleaseMapper releaseMapper;

	@Autowired
	private SpaceActionFrameOrderMapper orderMapper;

	@Autowired
	private YXUserService yxUserService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * Creates a space action frame application based on the request.
	 * First, it checks the usage of the specified Rvb ID and frame ID, ensuring they are valid and available.
	 * Then, it retrieves frame information based on the frame ID and copies the request parameters into a SpaceActionFrameApplication object.
	 * It generates a unique resource ID for the application and sets various attributes from the frame information.
	 * For the frame structure, it checks if it is empty. If so, it creates a new ActionFrameArrangeBo object and serializes it into a JSON string.
	 * If the frame structure is not empty, it deserializes the JSON string into an ActionFrameArrangeBo object, updates the resource IDs of the steps if necessary, and then serializes it back into a JSON string.
	 * Finally, it saves the application object.
	 *
	 * @param req The request object containing the necessary information to create a space action frame application, including the Rvb ID and frame ID.
	 */
	@Override
	public void create(SpaceActionFrameApplicationReq req) {
	    // Check the usage of the Rvb ID and frame ID to ensure they are valid and available.
	    this.checkUsage(req.getRvbId(), req.getFrameId());

	    // Retrieve frame information based on the frame ID.
	    SpaceActionFrame frame  = frameService.getById(req.getFrameId());

	    // Copy the request parameters into a SpaceActionFrameApplication object and generate a unique resource ID.
	    SpaceActionFrameApplication application = BeanUtils.copy(req, SpaceActionFrameApplication.class);
	    application.setResourceId(IDGeneratorUtil.getID());

	    // Set various attributes from the frame information.
	    application.setTag(frame.getTag());
	    application.setDescription(frame.getDescription());
	    application.setName(frame.getName());
	    application.setCategoryId(frame.getCategoryId());
	    application.setType(frame.getType());
	    application.setAnalyze(frame.getAnalyze());

	    // Handle the frame structure, which may be null.
	    String frameStr = frame.getFrame();
	    if (StringUtils.isBlank(frameStr)) {
	        // If the frame structure is empty, create a new ActionFrameArrangeBo object and serialize it into a JSON string.
	        ActionFrameArrangeBo bo = new ActionFrameArrangeBo();
	        bo.setResourceId(frame.getResourceId());
	        frameStr = JsonUtil.toJsonString(bo);
	    } else {
	        // If the frame structure is not empty, deserialize the JSON string into an ActionFrameArrangeBo object, update the resource IDs of the steps if necessary, and then serialize it back into a JSON string.
	        ActionFrameArrangeBo bo = JsonUtil.toJsonObject(frameStr, ActionFrameArrangeBo.class);
	        for (ActionFrameStep step : bo.getSteps()) {
	            if (StringUtils.isBlank(step.getResourceId())) {
	                step.setResourceId(IDGeneratorUtil.getID());
	            }
	        }
	        frameStr = JsonUtil.toJsonString(bo);
	    }
	    application.setFrame(frameStr);

	    // Save the application object.
	    this.save(application);
	}

	/**
	 * 根据RvbId和用户组获取应用用户列表
	 *
	 * @param rvbId Rvb用户ID字符串形式
	 * @param userGroup 用户组类型
	 * @return 应用用户列表，如果输入参数不合法或找不到用户，则返回空列表
	 */
	@Override
	public List<ApplicationUser> getUsers(String rvbId, Integer userGroup) {
	    // 检查输入参数合法性，如果rvbId为空或userGroup为null，则返回空列表
	    if (StringUtils.isBlank(rvbId) || null == userGroup) {
	        return Lists.newArrayList();
	    }
	    // 将rvbId字符串转换为Integer类型
	    Integer realRvbId = Integer.valueOf(rvbId);
	    // 尝试从用户服务获取Rvb用户集合
	    YxRvbUserColl coll = yxUserService.getYxRvbUsers(realRvbId);
	    // 如果用户集合为空，则尝试从JSON文件中读取用户数据
	    if (null == coll) {
	        try {
	            // 获取资源文件
	            File json = ResourceUtils.getFile(JsonFileResponseEnum.USERS.getFilePath());
	            // 读取文件内容
	            String content = FileUtil.getContent(json);
	            // 将JSON内容转换为用户集合对象
	            coll = JsonUtil.toJsonObject(content, YxRvbUserColl.class);
	        } catch (FileNotFoundException e) {
	            // 如果文件不存在，抛出业务异常
	            throw new BusinessException(ErrorCode.FILE_NOT_EXIST);
	        }
	    }
	    // 初始化返回结果列表
	    List<ApplicationUser> res = Lists.newArrayList();
	    // 根据用户组类型获取相应的用户信息列表
	    List<YxUserInfo> yxUserInfos = null;
	    if (SpaceCommonConstant.RvbUserGroup.COMMANDER.equals(userGroup)) {
	        yxUserInfos = coll.getCommander();
	    } else if (SpaceCommonConstant.RvbUserGroup.MIDDLE.equals(userGroup)) {
	        yxUserInfos = coll.getMiddle();
	    } else if (SpaceCommonConstant.RvbUserGroup.WORKER.equals(userGroup)) {
	        yxUserInfos = coll.getWorker();
	    } else if (SpaceCommonConstant.RvbUserGroup.STAFF.equals(userGroup)){
	        // 对于STAFF用户组，需要合并COMMANDER和MIDDLE用户组的用户信息
	        yxUserInfos = coll.getCommander();
	        yxUserInfos.addAll(coll.getMiddle());
	    }
	    // 如果用户信息列表不为空，则遍历用户信息并创建应用用户对象
	    if (!CollectionUtils.isEmpty(yxUserInfos)) {
	        for (YxUserInfo yxUserInfo : yxUserInfos) {
	            ApplicationUser user = new ApplicationUser();
	            // 使用用户名作为用户ID
	            user.setUserId(yxUserInfo.getUsername());
	            user.setUserName(yxUserInfo.getName());
	            res.add(user);
	        }
	    }
	    // 返回应用用户列表
	    return res;
	}

	/**
	 * 根据RVB ID和用户组获取用户映射表
	 * 该方法主要用于生成一个用户ID到用户名的映射表，便于快速查找用户名称
	 *
	 * @param rvbId RVB ID，用于确定用户组的标识
	 * @param userGroup 用户组ID，用于筛选特定组的用户
	 * @return 返回一个映射表，键为用户ID，值为用户名
	 */
	@Override
	public Map<String, String> getUserMap(String rvbId, Integer userGroup) {
	    // 获取特定RVB ID和用户组下的所有用户列表
	    List<ApplicationUser> userList = this.getUsers(rvbId, userGroup);
	    // 使用Stream API将用户列表转换为映射表，其中用户ID作为键，用户名作为值
	    return StreamApiUtils.toMap(userList, ApplicationUser::getUserId,
	            ApplicationUser::getUserName);
	}

	/**
	 * 根据应用ID列表删除相关记录
	 * 这个方法首先会根据提供的应用ID列表创建查询条件，然后在事务中删除对应的应用、发布和订单记录
	 * 使用事务确保数据一致性，如果删除过程中发生异常，事务将会回滚
	 *
	 * @param ids 应用ID列表，用于指定需要删除的记录
	 */
	@Override
	public void deleteByIds(List<String> ids) {
	    // 创建查询条件，用于删除发布表中的相关记录
	    LambdaQueryWrapper<SpaceActionFrameRelease> wrapperRelease = new LambdaQueryWrapper<>();
	    wrapperRelease.in(SpaceActionFrameRelease::getApplicationId, ids);
	    // 创建查询条件，用于删除订单表中的相关记录
	    LambdaQueryWrapper<SpaceActionFrameOrder> wrapperOrder = new LambdaQueryWrapper<>();
	    wrapperOrder.in(SpaceActionFrameOrder::getApplicationId, ids);

	    // 执行事务，确保所有删除操作要么全部成功，要么全部失败
	    transactionTemplate.execute(new TransactionCallbackWithoutResult() {

	        @Override
	        protected void doInTransactionWithoutResult(TransactionStatus status) {
	            // 删除应用表中的记录
	            removeByIds(ids);
	            // 删除发布表中的相关记录
	            releaseMapper.delete(wrapperRelease);
	            // 删除订单表中的相关记录
	            orderMapper.delete(wrapperOrder);
	        }
	    });
	}


	/**
	 * 检查RVB框架是否已被使用
	 * 此方法用于确定具有特定RVB ID和框架ID的SpaceActionFrameApplication记录是否已经存在
	 * 如果存在，表明该RVB框架已经在使用中，将抛出异常阻止进一步的操作
	 *
	 * @param rvbId RVB的唯一标识符
	 * @param frameId 框架的唯一标识符
	 * @throws BusinessException 如果找到匹配的记录，表示RVB框架已在使用中，抛出业务异常
	 */
	public void checkUsage(String rvbId, String frameId) {
	    // 创建一个Lambda查询包装器，用于构建查询条件
	    LambdaQueryWrapper<SpaceActionFrameApplication> wrapper = new LambdaQueryWrapper<>();
	    // 设置查询条件：根据RVB ID和框架ID进行精确匹配
	    wrapper.eq(SpaceActionFrameApplication::getRvbId, rvbId);
	    wrapper.eq(SpaceActionFrameApplication::getFrameId, frameId);
	    // 如果查询结果数量大于0，说明RVB框架已经在使用中
	    if (this.count(wrapper) > 0) {
	        // 抛出业务异常，指示RVB框架已在使用中
	        throw new BusinessException(SpaceError.RVB_FRAME_USED);
	    }
	}

}
