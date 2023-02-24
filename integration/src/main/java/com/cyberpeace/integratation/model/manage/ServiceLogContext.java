package com.cyberpeace.integratation.model.manage;

import java.io.Serializable;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class ServiceLogContext implements Serializable {

	@ApiModelProperty("日志内容")
	private String content;

	@ApiModelProperty("请求IP")
	private String ip;

	@ApiModelProperty("模块")
	private String module;

	@ApiModelProperty("子模块")
	private String subModule;

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("链接")
	private String url;

	@ApiModelProperty("用户ID")
	private String userId;

	@ApiModelProperty("用户名")
	private String userName;

	public ServiceLogContext(SubModule subModule, Type type) {
		this.module = subModule.getParent().getDesc();
		this.subModule = subModule.getDesc();
		this.type = type.getDesc();
		this.ip = CommonContext.getIp();
		this.url = CommonContext.getUrl();
		this.userId = UserInfoContext.getUserContext().getUserId();
		this.userName = UserInfoContext.getUserContext().getUserName();
	}

	public ServiceLogContext() {}

	public enum Module {

		TSDP(1, "态势大屏"),
		STGL(2, "视图管理"),
		TXGL(3, "图形管理");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Module(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public enum SubModule {

		TSDP(1, Module.TSDP, "态势大屏"),
		DPSL(2, Module.STGL, "大屏实例"),
		DPMB(3, Module.STGL, "大屏模板"),
		SJK(4, Module.STGL, "数据源-数据库"),
		API(5, Module.STGL, "数据源-API"),
		GIS(6, Module.TXGL, "GIS组件"),
		TPZJ(7, Module.TXGL, "拓扑组件"),
		TJZJ(8, Module.TXGL, "统计组件"),
		XSKJ(9, Module.TXGL, "显示控件"),
		TXZY(10, Module.TXGL, "图形资源"),
		ZJZT(11, Module.TXGL, "组件主题"),
		DXZY(12, Module.TXGL, "动效资源"),
		DXYS(13, Module.TXGL, "动效映射"),
		BHTY(14, Module.TXGL, "标绘图元"),
		BHWJ(15, Module.TXGL, "标绘文件");

		@Getter
		private Integer code;

		@Getter
		private Module parent;

		@Getter
		private String desc;

		private SubModule(Integer code, Module parent, String desc) {
			this.code = code;
			this.parent = parent;
			this.desc = desc;
		}

	}

	public enum Type {

		XZ(1, "新增"),
		CX(2, "查询"),
		XG(3, "修改"),
		SC(4, "删除"),
		DR(5, "导入"),
		DC(6, "导出");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
