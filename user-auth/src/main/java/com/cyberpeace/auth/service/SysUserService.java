package com.cyberpeace.auth.service;


import java.util.Map;

import com.cyberpeace.auth.controller.req.UserImportReq;
import com.cyberpeace.auth.controller.req.UserReq;
import com.cyberpeace.common.model.BaseRsp;
import org.springframework.session.Session;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.service.bo.*;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.MultiSelectReq;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;

/**
* @author dell
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-11-29 17:27:18
*/
public interface SysUserService extends IService<SysUser> {

	/**
	 * 用户自己重置密码
	 * @param req
	 */
	void resetPwdself(ResetPwdSelfReq req);

	/**
	 * @param userId
	 * @return {@code UserInfo}
	 */
	SysUser getByUserId(String userId);

	/**
	 * @param token
	 * @return {@code UserInfo}
	 */
	SysUser getByToken(String token);

	UserInfo getCurrent();

	Map<String, ? extends Session> getSessionsByUsername(String username);

	/**
	 * @param req
	 */
	void createUser(UserAddReq req);

	void deleteUser(MultiSelectReq req);

	void updateUser(UserModifyReq req);

	UserRes getUserDetailById(String resourceId);

	ListRsp<UserRes> pageUser(UserReq req);

	/**
	 * 管理员重置密码
	 * @param req
	 */
	void resetPwd(ResetPwdReq req);

	/**
	 * 更新用户头像
	 * @param avatar	头像路径
	 * @param resourceId	用户id
	 */
	void updateAvatar(String avatar, String resourceId);

	/**
	 * 更新用户状态
	 * @param req	请求参数
	 * @return	返回结果
	 */
	void updateUserEnable(UserEnableReq req);

	/**
	 * 导入用户
	 * @param req	用户文件
	 * @return	导入结果
	 */
	BaseRsp importUser(UserImportReq req);
}
