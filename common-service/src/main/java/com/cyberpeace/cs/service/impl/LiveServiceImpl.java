package com.cyberpeace.cs.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

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
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.constant.live.LiveWsMessageType;
import com.cyberpeace.cs.constant.live.LiveWsStatusActionCode;
import com.cyberpeace.cs.constant.live.SRSAction;
import com.cyberpeace.cs.service.LiveService;
import com.cyberpeace.cs.service.context.rtc.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LiveServiceImpl implements LiveService {

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	private String PUBLISH_URL;

	private String PLAY_URL;

	private String GET_STREAMS_URL;

	@PostConstruct
	public void init() {
		String srsHost = SystemPropUtil.getProp("SRS_HOST");
		String publishUrl = ConfigUtil.getContextProperty("rtc.publish.url");
		String playUrl = ConfigUtil.getContextProperty("rtc.play.url");
		String streamsUrl = ConfigUtil.getContextProperty("rtc.streams.url");

		PUBLISH_URL = ProtocolHead.HTTPS + srsHost + publishUrl;
		PLAY_URL = ProtocolHead.HTTPS + srsHost + playUrl;
		GET_STREAMS_URL = ProtocolHead.HTTP + srsHost + streamsUrl;
	}

	@Override
	public String getPublishUrl(WebRTCReq req) {
		String roomId = req.getRoomId();
		List<String> streamIds = getStreamIds();
		if (streamIds.contains(roomId)) {
			throw new BusinessException(ErrorCode.LIVE_DUPLICATED);
		}
		return PUBLISH_URL + req.getRoomId();
	}

	@Override
	public LiveStatus getLive(WebRTCReq req) {
		List<String> streamIds = getStreamIds();
		LiveStatus status = new LiveStatus();
		status.setOnLive(streamIds.contains(req.getRoomId()));
		status.setUrl(PLAY_URL + req.getRoomId());
		return status;
	}

	@Override
	public List<String> getStreamIds() {
		String rawRsp = doHttpGet(GET_STREAMS_URL);
		SrsStreamApiRes res = JsonUtil.toJsonObject(rawRsp, SrsStreamApiRes.class);
		return StreamApiUtils.map(res.getStreams(), SrsStream::getName);
	}

	@Override
	public void handleCallback(SrsCallbackReq req) {
		if (req.getAction().equals(SRSAction.ON_PUBLISH)) {
			pushLiveStatusMessage(req.getStream(), LiveWsStatusActionCode.PUBLISH);
		} else if (req.getAction().equals(SRSAction.ON_UNPUBLISH)) {
			pushLiveStatusMessage(req.getStream(), LiveWsStatusActionCode.UNPUBLISH);
		}
	}

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
	 * @param stream 直播流id
	 * @param code action code
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

class ProtocolHead {

	public static final String HTTP = "http://";

	public static final String HTTPS = "https://";

}
