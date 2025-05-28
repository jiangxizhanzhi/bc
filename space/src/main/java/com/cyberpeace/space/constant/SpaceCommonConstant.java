package com.cyberpeace.space.constant;

/**
 * 空间常用不断
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
public class SpaceCommonConstant {
    
	/**
	 * 默认策略框架
	 */
    public static final String DEFAULT_STRATEGY_FRAME = "default";

    /**
     * 战术阶段
     *
     * @author SN
     * @date 2024/11/06
     */
    public static final class TacticStage {

        /**
         * 前
         */
        public static final Integer PRE = 1;

        /**
         * 在
         */
        public static final Integer IN = 2;

        /**
         * 后
         */
        public static final Integer POST = 3;
	}

	public static final class TacticType {

		/**
		 * ATTACK
		 */
		public static final Integer ATTACK = 1;

		/**
		 * DEFENSE
		 */
		public static final Integer DEFENSE = 2;
	}

	public static class OrderStatus {
		public static final Integer UNRELEASED = 0;
		public static final Integer FINISHED = 1;
		public static final Integer UNFINISHED = 2;
	}

	public static class RvbUserGroup {

		public static final Integer COMMANDER = 1;

		public static final Integer MIDDLE = 2;

		public static final Integer WORKER = 3;

		public static final Integer STAFF = 4;
	}

}
