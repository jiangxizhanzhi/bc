package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场自定义错误码。
 * 1、从101001开始递增编号；
 * 2、截止编号地址101999；
 * <p>
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 */
public final class NodeOperate {

	/**
	 * 节点常量
	 */
	public static final int NODE_CONSTANT = 43;

	/**
	 * 节点重建
	 */
	public static final int COM = 8;
	/**
	 * 节点重建
	 */
	public static final int TELNET = 7;

	/**
	 * 节点重启
	 */
	public static final int NC = 6;

	/**
	 * 节点重建
	 */
	public static final int RDP = 5;

	/**
	 * 节点重启
	 */
	public static final int CONSOLE = 4;

	/**
	 * 节点重建
	 */
	public static final int RECREATE = 3;

	/**
	 * 节点重启
	 */
	public static final int RESTART = 2;

	/**
	 * 节点关机
	 */
	public static final int STOP = 1;

	/**
	 * 节点登入
	 */
	public static final int ACCESS = 0;
}
