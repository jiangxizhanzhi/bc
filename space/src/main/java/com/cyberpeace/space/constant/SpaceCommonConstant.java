package com.cyberpeace.space.constant;

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


	public static final class KnowledgeType {

		public static final Integer COURSE = 3;

		public static final Integer LESSON = 3;

		public static final Integer PRACTICE_OPERATION = 3;

		public static final Integer PRACTICE_THEORY = 3;

		public static final Integer TACTIC = 3;

		public static final Integer TACTIC_ATTACK = 3;

		public static final Integer TACTIC_DEFENSE = 3;

		public static final Integer FLOW = 3;

		public static final Integer SCENE = 3;

		public static final Integer ALGORITHM = 3;

		public static final Integer RULE = 3;

		public static final Integer STRATEGY = 3;
	}

	public static class OrderStatus {
		public static final Integer FINISHED = 1;
		public static final Integer UNFINISHED = 2;
	}

	public static class RvbUserGroup {

		public static final Integer COMMANDER = 1;

		public static final Integer MIDDLE = 2;

		public static final Integer WORKER = 3;
	}

}
