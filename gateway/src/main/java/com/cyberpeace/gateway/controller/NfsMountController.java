package com.cyberpeace.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.ShellUtils;
import com.cyberpeace.gateway.controller.req.NfsMountReq;
import com.cyberpeace.gateway.controller.req.NfsUmountReq;

/**
 * NFS挂载Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "外部接口-nfs挂载相关api")
@RestController
@RequestMapping("/exec/nfs/mount")
public class NfsMountController {

	/**
	 * 挂载指令
	 */
	private String MOUNT_SHELL = "mount -t nfs -o nolock -o soft %s:%s %s";

	/**
	 * 卸载指令
	 */
	private String UMOUNT_SHELL = "umount %s";

	/**
	 * mkdir
	 */
	private String MKDIR = "mkdir -p %s";

	/**
	 * NFS挂载
	 *
	 * @param nfsMountReq NFS挂载请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("NFS目录挂载")
	@PostMapping("/mount")
	public BaseRsp<Boolean> nfsMount(@RequestBody NfsMountReq nfsMountReq) {

		if (!new File(nfsMountReq.getPath()).exists()) {
			ShellUtils.execShell(String.format(MKDIR, nfsMountReq.getPath()));
		}

		ShellUtils.execShell(String.format(MOUNT_SHELL, nfsMountReq.getHost(),
				nfsMountReq.getPath(), nfsMountReq.getPath()));

		return new BaseRsp<>(Boolean.TRUE);
	}

	/**
	 * NFS 卸载
	 *
	 * @param umountReq umount请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("NFS目录卸载")
	@PostMapping("/umount")
	public BaseRsp<Boolean> nfsUmount(@RequestBody NfsUmountReq umountReq) {
		ShellUtils.execShell(String.format(UMOUNT_SHELL, umountReq.getPath()));
		return new BaseRsp<>(Boolean.TRUE);
	}
}
