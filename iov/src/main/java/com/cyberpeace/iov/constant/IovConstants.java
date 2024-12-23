package com.cyberpeace.iov.constant;

import java.util.List;

import com.google.common.collect.Lists;

public final class IovConstants {

	public static final class IovArea {}

	public static final class IovEcu {

		/**
		 * 视频播放器，中控屏
		 * cr_constants_config,type=43
		 */
		public static final Integer VIDEO_SCREEN = 42;

		/**
		 * 车门
		 * cr_constants_config,type=43
		 */
		public static final List<Integer> DOOR = Lists.newArrayList(101, 102, 103, 104);
	}

	public static final class CanData {

		/**
		 * 切换app
		 */
		public static final String SWITCH_APP = "A18491E1DC90581E";

		/**
		 * 旋转屏幕
		 */
		public static final String ROTATE_SCREEN = "EBCCE1F7C5A7D8B1";

		/**
		 * 开启车门
		 */
		public static final String OPEN_DOOR = "A8D8CE62B94C486B";

	}

	/**
	 * websocket 消息类型
	 */
	public static final class IovWsType {
		/**
		 * 闪光
		 */
		public static final Integer FLASH = 1;
	}

}
