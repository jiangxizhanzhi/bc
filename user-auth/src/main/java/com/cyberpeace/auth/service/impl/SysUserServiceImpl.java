package com.cyberpeace.auth.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.constant.AuthSystemPropertyConstant;
import com.cyberpeace.auth.controller.req.UserImportReq;
import com.cyberpeace.auth.controller.req.UserReq;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.mapper.SysUserMapper;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.*;
import com.cyberpeace.auth.util.UserValidator;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.DictInfo;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.DictInfoService;
import com.cyberpeace.cs.service.SystemPropertyService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;

import lombok.extern.slf4j.Slf4j;

/**
* @author dell
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-11-29 17:27:18
*/
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private RedisIndexedSessionRepository sessionRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private DictInfoService dictInfoService;

	@Autowired
	private SystemPropertyService systemPropertyService;

	@Override
	public void resetPwdself(ResetPwdSelfReq req) {
		TbUser currentUser = getCurrentSessionUser();
		if (null == currentUser) {
			throw new BusinessException(ErrorCode.INTERFACE_UNAUTHORIZED);
		}
		SysUser user = sysUserMapper.selectById(currentUser.getUserId());
		if (!bCryptPasswordEncoder.matches(req.getOldPwd(), user.getPassword())) {
			throw new BusinessException(ErrorCode.OLD_PWD_INCORRECT);
		}
		user.setPassword(bCryptPasswordEncoder.encode(req.getNewPwd()));
		sysUserMapper.updateById(user);
		// 登出该用户
		removeSessions(user.getResourceId());
	}

	@Override
	public void resetPwd(ResetPwdReq req) {
		SysUser user = getOrExceptionById(req.getUserId());
		UserValidator.validatePassword(req.getNewPwd());
		user.setPassword(bCryptPasswordEncoder.encode(req.getNewPwd()));
		sysUserMapper.updateById(user);
		// 登出该用户
		removeSessions(user.getResourceId());
	}

	@Override
	public void updateAvatar(String avatar, String resourceId) {
		SysUser user = getOrExceptionById(resourceId);
		user.setAvatar(avatar);
		sysUserMapper.updateById(user);
	}

	@Override
	public void updateUserEnable(UserEnableReq req) {
		if (CollectionUtils.isEmpty(req.getIds())) {
			throw new BusinessException(ErrorCode.EMPTY_IDS);
		}
		LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
		wrapper.in(SysUser::getResourceId, req.getIds()).set(SysUser::getEnabled, req.getEnable());
		sysUserMapper.update(null, wrapper);
	}

	@Override
	public BaseRsp importUser(UserImportReq req) {
		MultipartFile multipartFile = req.getFile();
		if (null == multipartFile) {
			throw new BusinessException(ErrorCode.FILE_NOT_EXIST);
		}
		String originalFilename = multipartFile.getOriginalFilename();
		if (!originalFilename.endsWith(FileSunffixConstant.XLSX.getDesc())){
			throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
		}
		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadDest(
				AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), true, multipartFile);
		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		ExcelDataHandler excelDataHandler = new ExcelDataHandler(viewPath, 0);
		List<UserExcel> userExcels = excelDataHandler.getSheetData(0, 0, UserExcel.class);

		List<DictInfo> dictInfo = dictInfoService.getDictInfo(DictInfo.KeyModel.AVATAR.getKey());
		Random random = new Random();
		// 转换为实体
		List<SysUser> users = userExcels.stream().map(user -> {
			UserAddReq userAdd = BeanUtils.copy(user, UserAddReq.class);
			userAdd.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
			userAdd.setAdmin(false);
			userAdd.setAvatar(dictInfo.get(random.nextInt(dictInfo.size())).getItemValue());
			return req2Entity(userAdd);
		}).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(users)){
			throw new BusinessException(ErrorCode.EMPTY_FILE);
		}
		currentUserNum(users.size());
		saveBatch(users);
		return new BaseRsp<>();
	}

	@Override
	public Map<String, String> getUserNameMap(List<String> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)){
			return new HashMap<>();
		}
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().in(SysUser::getResourceId,userIdList);
		List<SysUser> list = this.list(queryWrapper);
		return list.stream().collect(Collectors.toMap(SysUser::getResourceId, SysUser::getName));
	}

	@Override
	public List<UserInfo> getUsers(List<String> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)){
			return new ArrayList<>();
		}
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().in(SysUser::getResourceId,userIdList);
		List<SysUser> list = this.list(queryWrapper);
		List<UserInfo> res = new ArrayList<>();
		list.forEach(item -> {
			UserInfo userInfo = new UserInfo();
			userInfo.setName(item.getName());
			userInfo.setUserId(item.getResourceId());
			userInfo.setUserName(item.getUsername());
			res.add(userInfo);
		});
		return res;
	}

	@Override
	public void updateOnlineTime(Date loginTime,String userId) {
		if (loginTime == null) {
			return;
		}
		SysUser sysUser = this.getById(userId);
		//按小时设置在线时间
		sysUser.setOnlineTime(sysUser.getOnlineTime() + ((double) (System.currentTimeMillis() - loginTime.getTime())) / 1000 / 60 / 60);
		this.updateById(sysUser);
	}

	@Override
	public Map<String, UserInfo> getUserInfoMap(Collection<String> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return new HashMap<>();
		}
		List<SysUser> userList = sysUserMapper.selectBatchIds(userIds);
		return StreamApiUtils.toMap(userList, SysUser::getResourceId, this::makeUserInfo);
	}

	@Override
	public SysUser getByUserId(String userId) {
		return getOrExceptionById(userId);
	}

	@Override
	public SysUser getCurrent() {
		TbUser currentUser = getCurrentSessionUser();
		if (null == currentUser) {
			return null;
		}
		return getByUserId(currentUser.getUserId());
	}

	@Override
	public void createUser(UserAddReq req) {
		// 校验用户数量
		currentUserNum(1);
		if (sysUserMapper.existByUsername(req.getUsername())) {
			throw new BusinessException(ErrorCode.USERNAME_DUPLICATE);
		}
		SysUser user = req2Entity(req);
		sysUserMapper.insert(user);
	}

	@Override
	public void deleteUser(List<String> idList) {
		if (CollectionUtils.isEmpty(idList)) {
			throw new BusinessException(ErrorCode.EMPTY_IDS);
		}
		sysUserMapper.deleteBatchIds(idList);
		//todo 删除用户账号相关所有数据
		log.info("delete user {} success", JsonUtil.toJsonString(idList));

		for (String id : idList) {
			removeSessions(id);
		}
	}

	@Override
	public void updateUser(UserModifyReq req) {
		req.validate();
		TbUser sessionUser = this.getCurrentSessionUser();
		if (!sessionUser.isAdmin() && !sessionUser.getUserId().equals(req.getResourceId())) {
			throw new AccessDeniedException("");
		}
		SysUser user = req2Entity(req);
		user.setResourceId(req.getResourceId());
		sysUserMapper.updateById(user);
	}

	@Override
	public UserRes getUserDetailById(String resourceId) {
		SysUser user = getOrExceptionById(resourceId);
		return makeUserRes(user, false);
	}

	@Override
	public ListRsp<UserRes> pageUser(UserReq req) {
		Page page = req.toPageParam();
		IPage<SysUser> result = sysUserMapper.pageUser(page, req.getPattern(), req.getEnabled());
		IPage<UserRes> resPage = result.convert(r -> makeUserRes(r, true));
		return PageUtil.getListResult(resPage);
	}

	@Override
	public UserInfo getByToken(String sessionId) {
		Session session = sessionRepository.findById(sessionId);
		if (null == session) {
			throw new BusinessException(ErrorCode.INVALID_SESSION);
		}
		SecurityContext context = session.getRequiredAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		TbUser user = (TbUser) context.getAuthentication().getPrincipal();

		return makeUserInfo(getByUserId(user.getUserId()));
	}

	private SysUser getOrExceptionById(String userId) {
		SysUser user = sysUserMapper.selectById(userId);
		if (null == user) {
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}
		return user;
	}

	private SysUser getOrExceptionByUsername(String username) {
		SysUser user = sysUserMapper.selectByUsername(username);
		if (null == user) {
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}
		return user;
	}

	private UserInfo makeUserInfo(SysUser user) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user.getResourceId());
		userInfo.setName(user.getName());
		userInfo.setAdmin(user.getAdmin());
		userInfo.setUserName(user.getUsername());
		userInfo.setAvatarUrl(user.getAvatar());
		userInfo.setSuperuser(user.getAdmin());

		return userInfo;
	}

	/**
	 * makeuser结果
	 *
	 * @param user
	 * @param showOnline 是否显示在线
	 * @return {@code UserRes}
	 */
	private UserRes makeUserRes(SysUser user, boolean showOnline) {
		UserRes userRes = new UserRes();
		userRes.setId(user.getId());
		userRes.setResourceId(user.getResourceId());
		userRes.setAdmin(user.getAdmin());
		userRes.setUsername(user.getUsername());
		userRes.setName(user.getName());
		userRes.setAvatar(user.getAvatar());
		userRes.setNotes(user.getNotes());
		userRes.setExpireTime(user.getExpireTime());
		userRes.setEnabled(user.getEnabled());
		userRes.setOnlineTime(user.getOnlineTime());
		return userRes;
	}

	private boolean isUserOnline(String principalName) {
		return !sessionRepository.findByPrincipalName(principalName).isEmpty();
	}

	private <T extends UserAddReq> SysUser req2Entity(T req) {
		SysUser user = new SysUser();
		user.setUsername(req.getUsername());
		user.setName(req.getName());
		if (StringUtils.isNotBlank(req.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
		}
		user.setAvatar(req.getAvatar());
		if (isCurrentAdmin()) {
			user.setAdmin(req.getAdmin());
		}
		user.setExpireTime(req.getExpireTime());
		user.setNotes(req.getNotes());
		return user;
	}

	private boolean isCurrentAdminByDb() {
		SysUser userInfo = UserInfoContext.getNewUserContext();
		if (null == userInfo) {
			return false;
		}
		return userInfo.getAdmin();
	}


	private TbUser getCurrentSessionUser() {
		return (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private void currentUserNum(Integer insertNum){
		int count = count() + insertNum;
		if (systemPropertyService.getSystemPropertyInt(AuthSystemPropertyConstant.MAX_USER_COUNT, 15) < count){
			throw new BusinessException(ErrorCode.USER_MAX_NUM);
		}
	}

	private boolean isCurrentAdmin() {
		UserInfo userInfo = UserInfoContext.getUserContext();
		if (null == userInfo) {
			return false;
		}
		return userInfo.isAdmin();
	}


	private void removeSessions(String userId){
		for (String sessionId : sessionRepository.findByPrincipalName(userId).keySet()) {
			sessionRepository.deleteById(sessionId);
		}
	}
}
