
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
import com.cyberpeace.space.dao.mapper.SpaceActionFrameApplicationMapper;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.ApplicationUser;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceActionFrameApplicationServiceImpl extends ServiceImpl<SpaceActionFrameApplicationMapper, SpaceActionFrameApplication> implements SpaceActionFrameApplicationService {

	@Autowired
	private SpaceActionFrameService frameService;

	@Autowired
	private YXUserService yxUserService;

	@Override
	public void create(SpaceActionFrameApplicationReq req) {
        this.checkUsage(req.getRvbId(), req.getFrameId());
        SpaceActionFrame frame  = frameService.getById(req.getFrameId());
        SpaceActionFrameApplication application = BeanUtils.copy(req, SpaceActionFrameApplication.class);
        application.setResourceId(IDGeneratorUtil.getID());
        application.setFrame(frame.getFrame());
        application.setTag(frame.getTag());
        application.setDescription(frame.getDescription());
        application.setName(frame.getName());
        application.setCategoryId(frame.getCategoryId());
        application.setType(frame.getType());

        this.save(application);
	}

	@Override
	public List<ApplicationUser> getUsers(String rvbId, Integer userGroup) {
		Integer realRvbId = Integer.valueOf(rvbId);
		YxRvbUserColl coll = yxUserService.getYxRvbUsers(realRvbId);
		if (null == coll) {
			try {
				File json = ResourceUtils.getFile(JsonFileResponseEnum.USERS.getFilePath());
				String content = FileUtil.getContent(json);
				coll = JsonUtil.toJsonObject(content, YxRvbUserColl.class);
			} catch (FileNotFoundException e) {
				throw new BusinessException(ErrorCode.FILE_NOT_EXIST);
			}
		}
		List<ApplicationUser> res = Lists.newArrayList();
		List<YxUserInfo> yxUserInfos = null;
		if (SpaceCommonConstant.RvbUserGroup.COMMANDER.equals(userGroup)) {
			yxUserInfos = coll.getCommander();
		} else if (SpaceCommonConstant.RvbUserGroup.MIDDLE.equals(userGroup)) {
			yxUserInfos = coll.getMiddle();
		} else if (SpaceCommonConstant.RvbUserGroup.WORKER.equals(userGroup)) {
			yxUserInfos = coll.getWorker();
		}
		// username 作为 user_id
		if (!CollectionUtils.isEmpty(yxUserInfos)) {
			for (YxUserInfo yxUserInfo : yxUserInfos) {
				ApplicationUser user = new ApplicationUser();
				user.setUserId(yxUserInfo.getUsername());
				user.setUserName(yxUserInfo.getName());
				res.add(user);
			}
		}
		return res;
	}

	@Override
	public Map<String, String> getUserMap(String rvbId, Integer userGroup) {
		List<ApplicationUser> userList = this.getUsers(rvbId, userGroup);
		return StreamApiUtils.toMap(userList, ApplicationUser::getUserId,
				ApplicationUser::getUserName);
	}


	public void checkUsage(String rvbId, String frameId) {
		LambdaQueryWrapper<SpaceActionFrameApplication> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameApplication::getRvbId, rvbId);
		wrapper.eq(SpaceActionFrameApplication::getFrameId, frameId);
		if (this.count(wrapper) > 0) {
			throw new BusinessException(SpaceError.RVB_FRAME_USED);
		}
	}

}
