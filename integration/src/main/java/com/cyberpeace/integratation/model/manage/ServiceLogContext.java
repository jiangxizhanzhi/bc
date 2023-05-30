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

		/**
		 * 态势大屏
		 */
		TSDP(1, "态势大屏"),

		/**
		 * 视图管理
		 */
		STGL(2, "视图管理"),

		/**
		 * 图形管理
		 */
		TXGL(3, "图形管理"),

		/**
		 * 系统管理
		 */
		XTGL(4, "系统管理");

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

		/**
		 * 态势大屏
		 */
		TSDP(1, Module.TSDP, "态势大屏"),
		/**
		 * 态势标绘
		 */
		TSBH(2, Module.TSDP, "态势标绘"),
		/**
		 * 拓扑坐标
		 */

		TPZB(3, Module.TSDP, "拓扑坐标"),
		/**
		 * 重演大屏
		 */
		CYDP(4, Module.STGL, "重演大屏"),
		/**
		 * 大屏实例
		 */
		DPSL(5, Module.STGL, "大屏实例"),

		/**
		 * 大屏模板
		 */
		DPMB(6, Module.STGL, "大屏模板"),
		/**
		 * 数据源-数据库
		 */
		SJK(7, Module.STGL, "数据源-数据库"),
		/**
		 * 数据源-API
		 */
		API(8, Module.STGL, "数据源-API"), TYZJ(17, Module.TXGL, "通用组件"),
		/**
		 * GIS组件
		 */
		GIS(9, Module.TXGL, "GIS组件"),
		/**
		 * 拓扑组件
		 */
		TPZJ(10, Module.TXGL, "拓扑组件"), DHZJ(16, Module.TXGL, "动画组件"),
		/**
		 * 图标组件
		 */
		ICZJ(11, Module.TXGL, "图标组件"),
		/**
		 * 图表组件
		 */
		TBZJ(12, Module.TXGL, "图表组件"),
		/**
		 * 显示控件
		 */
		XSKJ(13, Module.TXGL, "显示控件"),
		/**
		 * 样式组件
		 */
		YSZJ(14, Module.TXGL, "样式组件"),
		/**
		 * 图表主题
		 */
		TBZT(15, Module.TXGL, "图表主题"),
		/**
		 * 动效资源
		 */
		DXZY(16, Module.TXGL, "动效资源"),
		/**
		 * 动效映射
		 */
		DXYS(17, Module.TXGL, "动效映射"),
		/**
		 * 标绘图元
		 */
		BHTY(18, Module.TXGL, "标绘图元"),
		/**
		 * 标绘文件
		 */
		BHWJ(19, Module.TXGL, "标绘文件"),
		/**
		 * 管理工具
		 */
		GLGJ(20, Module.XTGL, "管理工具");

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

		/**
		 * 新增
		 */
		XZ(1, "新增"),
		/**
		 * 查询
		 */
		CX(2, "查询"),
		/**
		 * 修改
		 */
		XG(3, "修改"),
		/**
		 * 删除
		 */
		SC(4, "删除"),
		/**
		 * 导入
		 */
		DR(5, "导入"),
		/**
		 * 导出
		 */
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
