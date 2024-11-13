package com.cyberpeace.cs.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.constant.live.LiveWsMessageType;
import com.cyberpeace.cs.constant.live.LiveWsStatusActionCode;
import com.cyberpeace.cs.constant.live.SRSAction;
import com.cyberpeace.cs.service.LiveService;
import com.cyberpeace.cs.service.context.rtc.*;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 直播Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Slf4j
@Service
public class LiveServiceImpl implements LiveService {

	/**
	 * 我Web插座处理器
	 */
	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 发布 URL
	 */
	private String PUBLISH_URL;

	/**
	 * 播放网址
	 */
	private String PLAY_URL;

	/**
	 * 获取流网址
	 */
	private String GET_STREAMS_URL;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		String srsHost = SystemPropUtil.getProp("SRS_HOST");
		String publishUrl = ConfigUtil.getContextProperty("rtc.publish.url");
		String playUrl = ConfigUtil.getContextProperty("rtc.play.url");
		String streamsUrl = ConfigUtil.getContextProperty("rtc.streams.url");

		PUBLISH_URL = srsHost + publishUrl;
		PLAY_URL = srsHost + playUrl;
		GET_STREAMS_URL = ProtocolHead.HTTP + srsHost + streamsUrl;
	}

	/**
	 * 获取发布网址
	 *
	 * @param req 请求体
	 * @return {@code String }
	 */
	@Override
	public String getPublishUrl(WebRTCReq req) {
		String roomId = req.getRoomId();
		Map<String, Set<String>> streamIds = getStreams();
		if (streamIds.containsKey(roomId)) {
			Set<String> broadcasterIdList = streamIds.get(roomId);
			Collection<String> broadcasterList = remoteUserService
					.getIdNameMap(Lists.newArrayList(broadcasterIdList.iterator())).values();
			while (broadcasterList.size() < broadcasterIdList.size()) {
				broadcasterList.add("DELETED_USER");
			}
			String broadcasterNames = String.join(",", broadcasterList);
			throw new BusinessException(ErrorCode.LIVE_DUPLICATED, broadcasterNames);
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		// 课堂id作为app，推流人id作为stream
		return String.format(PUBLISH_URL, roomId, userId);
	}

	/**
	 * 获取直播
	 *
	 * @param req 请求体
	 * @return {@code LiveStatus }
	 */
	@Override
	public LiveStatus getLive(WebRTCReq req) {
		Map<String, Set<String>> streams = getStreams();
		LiveStatus status = new LiveStatus();
		status.setOnLive(streams.containsKey(req.getRoomId()));
		String broadcasterId = Boolean.TRUE.equals(status.getOnLive())
				? streams.get(req.getRoomId()).iterator().next()
				: "unknown";
		// 课堂id作为app，推流人id作为stream
		status.setUrl(String.format(PLAY_URL, req.getRoomId(), broadcasterId));
		return status;
	}

	/**
	 * 获取流
	 *
	 * @return {@code Map<String, Set<String>> }
	 */
	private Map<String, Set<String>> getStreams() {
		String rawRsp = doHttpGet(GET_STREAMS_URL);
		SrsStreamApiRes res = JsonUtil.toJsonObject(rawRsp, SrsStreamApiRes.class);
		Map<String, Set<String>> streamMap = Maps.newHashMap();
		for (SrsStream stream : res.getStreams()) {
			if (!streamMap.containsKey(stream.getApp())) {
				streamMap.put(stream.getApp(), Sets.newHashSet());
			}
			streamMap.get(stream.getApp()).add(stream.getName());
		}
		return streamMap;

	}

	/**
	 * handle 回调
	 *
	 * @param req 请求体
	 */
	@Override
	public void handleCallback(SrsCallbackReq req) {
		if (req.getAction().equals(SRSAction.ON_PUBLISH)) {
			pushLiveStatusMessage(req.getApp(), LiveWsStatusActionCode.PUBLISH);
		} else if (req.getAction().equals(SRSAction.ON_UNPUBLISH)) {
			pushLiveStatusMessage(req.getApp(), LiveWsStatusActionCode.UNPUBLISH);
		}
	}

	/**
	 * 做http获取
	 *
	 * @param url 网址
	 * @return {@code String }
	 */
	private String doHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		String httpResponse = null;
		try {
			response = HttpClients.custom().build().execute(request);
			httpResponse = EntityUtils.toString(response.getEntity(), WebConstant.ENCODING);
			if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
				throw new RemoteServiceException(
						String.valueOf(response.getStatusLine().getStatusCode()));
			}
		} catch (Exception e) {
			log.error("srs response {}", httpResponse);
			log.error("srs exception" + e.getMessage(), e);
			throw new BusinessException(ErrorCode.SRS_BAD_RESPONSE);
		}
		return httpResponse;
	}

	/**
	 * 推送直播状态消息
	 *
	 * @param stream 直播流id
	 * @param code   action code
	 */
	private void pushLiveStatusMessage(String stream, Integer code) {
		LiveStatusIdentifier identifier = new LiveStatusIdentifier();
		identifier.setPath(WsPath.LIVE);
		identifier.setStream(stream);

		JSONObject data = new JSONObject();
		data.put(JsonKeyConstant.TYPE, LiveWsMessageType.STATUS);
		data.put(JsonKeyConstant.CODE, code);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();
		myWebSocketHandler.pushMessage(messageContext, true);
	}
}

/**
 * 协议头
 *
 * @author SN
 * @date 2024/11/11
 */
class ProtocolHead {

	/**
	 * http
	 */
	public static final String HTTP = "http://";

	/**
	 * https
	 */
	public static final String HTTPS = "https://";

}
