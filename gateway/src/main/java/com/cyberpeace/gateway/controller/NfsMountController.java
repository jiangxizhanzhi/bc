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
 * NFS安装Controller
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `NfsMountController` 的 Spring MVC 控制器，用于处理与 NFS（网络文件系统）挂载和卸载相关的操作。以下是该代码的详细分析： 
 * ### 包和导入 
 * - **包声明**：代码位于 `com.cyberpeace.gateway.controller` 包中。 
 * - **导入**：导入了必要的类和注解，包括 Swagger 注解、Spring Web 注解以及自定义的模型和工具类。 
 * ### 类和注释 
 * - **类声明**：`NfsMountController` 是一个 REST 控制器，使用 `@RestController` 和 `@RequestMapping` 注解来定义其基本路径为 `/exec/nfs/mount`。 
 * - **类注释**：提供了类的描述，说明了类的功能和作者信息。 
 * ### 成员变量 
 * - **MOUNT_SHELL**：定义了一个字符串，用于生成挂载 NFS 的 shell 命令。 
 * - **UMOUNT_SHELL**：定义了一个字符串，用于生成卸载 NFS 的 shell 命令。 
 * - **MKDIR**：定义了一个字符串，用于生成创建目录的 shell 命令。 
 * ### 方法 
 * #### `nfsMount` 
 * - **注释**：使用 `@ApiOperation` 注解，描述了该方法的功能。 
 * - **参数**：接收一个 `NfsMountReq` 对象作为请求体。 
 * - **功能**： 
 * 1. 检查挂载点是否存在，如果不存在则创建。 
 * 2. 使用 `ShellUtils.execShell` 方法执行挂载命令。 
 * 3. 返回一个 `BaseRsp` 对象，表示操作成功。 
 * #### `nfsUmount` 
 * - **注释**：使用 `@ApiOperation` 注解，描述了该方法的功能。 
 * - **参数**：接收一个 `NfsUmountReq` 对象作为请求体。 
 * - **功能**： 
 * 1. 使用 `ShellUtils.execShell` 方法执行卸载命令。 
 * 2. 返回一个 `BaseRsp` 对象，表示操作成功。 
 * ### 总结 
 * - **主要功能**：该控制器提供了两个主要功能：NFS 目录的挂载和卸载。 
 * - **技术栈**：使用了 Spring MVC 进行 REST API 开发，并使用了 Swagger 进行 API 文档的生成。 
 * - **潜在问题**： 
 * - 没有错误处理机制，如果 shell 命令执行失败，可能会导致未知的行为。 
 * - 没有验证输入的有效性，可能会导致非法操作或安全漏洞。 
 * ### 改进建议 
 * - 添加异常处理逻辑，确保在 shell 命令执行失败时能够返回适当的错误信息。 
 * - 增加输入验证逻辑，确保请求中的参数符合预期格式和范围。 
**/
@Api(tags = "外部接口-nfs挂载相关api")
@RestController
@RequestMapping("/exec/nfs/mount")
public class NfsMountController {

	/**
	 * 挂载 Shell
	 */
	private String MOUNT_SHELL = "mount -t nfs -o nolock -o soft %s:%s %s";

	/**
	 * umount shell
	 */
	private String UMOUNT_SHELL = "umount %s";

	/**
	 * mkdir
	 */
	private String MKDIR = "mkdir -p %s";

	/**
	 * NFS 挂载
	 *
	 * @param nfsMountReq NFS安装请求体
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

