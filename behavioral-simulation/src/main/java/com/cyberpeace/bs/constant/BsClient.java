package com.cyberpeace.bs.constant;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BsClient {

	public static final List<CodeNameRsp> WebClient = Lists.newArrayList();
	static {
		WebClient.add(new CodeNameRsp(11, "FireFox"));
		WebClient.add(new CodeNameRsp(12, "IE"));
		WebClient.add(new CodeNameRsp(13, "Chrome"));
		WebClient.add(new CodeNameRsp(14, "Safari"));
		WebClient.add(new CodeNameRsp(15, "其他"));
	}

	public static final List<CodeNameRsp> MailClient = Lists.newArrayList();
	static {
		MailClient.add(new CodeNameRsp(21, "Outlook"));
		MailClient.add(new CodeNameRsp(22, "Thunderbird"));
		MailClient.add(new CodeNameRsp(23, "其他"));
	}

	public static final List<CodeNameRsp> VideoClient = Lists.newArrayList();
	static {
		VideoClient.add(new CodeNameRsp(31, "H.264播放器"));
		VideoClient.add(new CodeNameRsp(32, "腾讯视频"));
		VideoClient.add(new CodeNameRsp(33, "vlc"));
		VideoClient.add(new CodeNameRsp(34, "其他"));
	}

	public static final List<CodeNameRsp> IMClient = Lists.newArrayList();
	static {
		IMClient.add(new CodeNameRsp(41, "QQ"));
		IMClient.add(new CodeNameRsp(42, "微信"));
		IMClient.add(new CodeNameRsp(43, "What's App"));
		IMClient.add(new CodeNameRsp(44, "内网通"));
		IMClient.add(new CodeNameRsp(45, "飞秋"));
		IMClient.add(new CodeNameRsp(46, "其他"));
	}

	public static final List<CodeNameRsp> GameClient = Lists.newArrayList();
	static {
		GameClient.add(new CodeNameRsp(51, "Minecraft"));
		GameClient.add(new CodeNameRsp(52, "其他"));
	}

	public static final List<CodeNameRsp> FTPClient = Lists.newArrayList();
	static {
		FTPClient.add(new CodeNameRsp(61, "Xftp"));
		FTPClient.add(new CodeNameRsp(62, "Filezilla"));
		FTPClient.add(new CodeNameRsp(63, "其他"));
	}

	public static final List<String> TYPES = Lists.newArrayList("Web客户端", "邮件客户端", "视频客户端",
			"即时通讯客户端", "网络游戏客户端", "FTP客户端");

	public static final Map<String, List<CodeNameRsp>> AppMap = Maps.newLinkedHashMap();
	static {
		AppMap.put("Web客户端", WebClient);
		AppMap.put("邮件客户端", MailClient);
		AppMap.put("视频客户端", VideoClient);
		AppMap.put("即时通讯客户端", IMClient);
		AppMap.put("网络游戏客户端", GameClient);
		AppMap.put("FTP客户端", FTPClient);
	}

	private BsClient() {}
}
