package com.cyberpeace.situation.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Api(tags = "SW")
@RestController
@RequestMapping("/sw")
public class SituationSwController {

	// 10分钟一个数，从00:00到23:50，共计144个
	private List<Element> attack = Lists.newArrayList();

	private List<Element> flow = Lists.newArrayList();

	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@PostConstruct
	void init() {
		initFlow();
		initAttack();
		initFlow();
		initAttack();
	}

	@CrossOrigin("*")
	@ApiOperation("获取流量趋势")
	@GetMapping("/flow/trend")
	public BaseRsp<List<Element>> getFlowTrend() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime eightHoursAgo = now.minusHours(8);
		int indexStart = eightHoursAgo.getHour() * 6 + eightHoursAgo.getMinute() / 10 + 1;
		List<Element> res = flow.subList(indexStart, indexStart + 48);

		return new BaseRsp<>(res);
	}

	@CrossOrigin("*")
	@ApiOperation("获取攻击趋势")
	@GetMapping("/attack/trend")
	public BaseRsp<List<Element>> getAttackTrend() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime eightHoursAgo = now.minusHours(8);
		int indexStart = eightHoursAgo.getHour() * 6 + eightHoursAgo.getMinute() / 10 + 1;
		List<Element> res = attack.subList(indexStart, indexStart + 48);

		return new BaseRsp<>(res);
	}

	@CrossOrigin("*")
	@ApiOperation("获取攻击趋势")
	@GetMapping("/news")
	public BaseRsp<JSONObject> getNews() {

		InputStream inputStream = getClass().getResourceAsStream("/page.json");
		JSONObject rawData = JsonUtil.toJsonObject(inputStream, JSONObject.class)
				.getJSONObject("data");

		// 数据平移到当前时间的1分钟之前，且数据1分钟刷新一次
		LocalTime now = LocalTime.now();
		// baseline time 06:53:13
		int min2add = (now.getHour() - 6) * 60 + (now.getMinute() - 53) - 1;

		JSONArray list = rawData.getJSONArray("list");
		for (int i = 0; i < list.size(); i++) {
			JSONObject element = list.getJSONObject(i);
			String rawTime = element.getString("time");
			element.put("time", modifyDateTime(rawTime, min2add));
		}
		return new BaseRsp<>(rawData);
	}

	private String modifyDateTime(String raw, int min2add) {
		String[] dateAndTime = raw.split(" ");
		LocalTime rawTime = LocalTime.parse(dateAndTime[1], timeFormatter);
		return dateAndTime[0] + " " + rawTime.plusMinutes(min2add).format(timeFormatter);
	}

	private void initFlow() {
		flow.add(new Element("2023-04-14 00:00:00", 761486));
		flow.add(new Element("2023-04-14 00:10:00", 412267));
		flow.add(new Element("2023-04-14 00:20:00", 68080));
		flow.add(new Element("2023-04-14 00:30:00", 809892));
		flow.add(new Element("2023-04-14 00:40:00", 820805));
		flow.add(new Element("2023-04-14 00:50:00", 352481));
		flow.add(new Element("2023-04-14 01:00:00", 330478));
		flow.add(new Element("2023-04-14 01:10:00", 511064));
		flow.add(new Element("2023-04-14 01:20:00", 721736));
		flow.add(new Element("2023-04-14 01:30:00", 311722));
		flow.add(new Element("2023-04-14 01:40:00", 299866));
		flow.add(new Element("2023-04-14 01:50:00", 161438));
		flow.add(new Element("2023-04-14 02:00:00", 880027));
		flow.add(new Element("2023-04-14 02:10:00", 306571));
		flow.add(new Element("2023-04-14 02:20:00", 365355));
		flow.add(new Element("2023-04-14 02:30:00", 724637));
		flow.add(new Element("2023-04-14 02:40:00", 396964));
		flow.add(new Element("2023-04-14 02:50:00", 107265));
		flow.add(new Element("2023-04-14 03:00:00", 972030));
		flow.add(new Element("2023-04-14 03:10:00", 556688));
		flow.add(new Element("2023-04-14 03:20:00", 183974));
		flow.add(new Element("2023-04-14 03:30:00", 703162));
		flow.add(new Element("2023-04-14 03:40:00", 261154));
		flow.add(new Element("2023-04-14 03:50:00", 209477));
		flow.add(new Element("2023-04-14 04:00:00", 957743));
		flow.add(new Element("2023-04-14 04:10:00", 771445));
		flow.add(new Element("2023-04-14 04:20:00", 521448));
		flow.add(new Element("2023-04-14 04:30:00", 398472));
		flow.add(new Element("2023-04-14 04:40:00", 791914));
		flow.add(new Element("2023-04-14 04:50:00", 788508));
		flow.add(new Element("2023-04-14 05:00:00", 695050));
		flow.add(new Element("2023-04-14 05:10:00", 368938));
		flow.add(new Element("2023-04-14 05:20:00", 485467));
		flow.add(new Element("2023-04-14 05:30:00", 371947));
		flow.add(new Element("2023-04-14 05:40:00", 662782));
		flow.add(new Element("2023-04-14 05:50:00", 662953));
		flow.add(new Element("2023-04-14 06:00:00", 333353));
		flow.add(new Element("2023-04-14 06:10:00", 901479));
		flow.add(new Element("2023-04-14 06:20:00", 456335));
		flow.add(new Element("2023-04-14 06:30:00", 67461));
		flow.add(new Element("2023-04-14 06:40:00", 323384));
		flow.add(new Element("2023-04-14 06:50:00", 173551));
		flow.add(new Element("2023-04-14 07:00:00", 579824));
		flow.add(new Element("2023-04-14 07:10:00", 38299));
		flow.add(new Element("2023-04-14 07:20:00", 448991));
		flow.add(new Element("2023-04-14 07:30:00", 652746));
		flow.add(new Element("2023-04-14 07:40:00", 298690));
		flow.add(new Element("2023-04-14 07:50:00", 258765));
		flow.add(new Element("2023-04-14 08:00:00", 851869));
		flow.add(new Element("2023-04-14 08:10:00", 194104));
		flow.add(new Element("2023-04-14 08:20:00", 443150));
		flow.add(new Element("2023-04-14 08:30:00", 517195));
		flow.add(new Element("2023-04-14 08:40:00", 364766));
		flow.add(new Element("2023-04-14 08:50:00", 828783));
		flow.add(new Element("2023-04-14 09:00:00", 503904));
		flow.add(new Element("2023-04-14 09:10:00", 153488));
		flow.add(new Element("2023-04-14 09:20:00", 249690));
		flow.add(new Element("2023-04-14 09:30:00", 642302));
		flow.add(new Element("2023-04-14 09:40:00", 744175));
		flow.add(new Element("2023-04-14 09:50:00", 804089));
		flow.add(new Element("2023-04-14 10:00:00", 146551));
		flow.add(new Element("2023-04-14 10:10:00", 767299));
		flow.add(new Element("2023-04-14 10:20:00", 278288));
		flow.add(new Element("2023-04-14 10:30:00", 468250));
		flow.add(new Element("2023-04-14 10:40:00", 296846));
		flow.add(new Element("2023-04-14 10:50:00", 191160));
		flow.add(new Element("2023-04-14 11:00:00", 524426));
		flow.add(new Element("2023-04-14 11:10:00", 468015));
		flow.add(new Element("2023-04-14 11:20:00", 585031));
		flow.add(new Element("2023-04-14 11:30:00", 191681));
		flow.add(new Element("2023-04-14 11:40:00", 438050));
		flow.add(new Element("2023-04-14 11:50:00", 798913));
		flow.add(new Element("2023-04-14 12:00:00", 509801));
		flow.add(new Element("2023-04-14 12:10:00", 654506));
		flow.add(new Element("2023-04-14 12:20:00", 595137));
		flow.add(new Element("2023-04-14 12:30:00", 800723));
		flow.add(new Element("2023-04-14 12:40:00", 864810));
		flow.add(new Element("2023-04-14 12:50:00", 559798));
		flow.add(new Element("2023-04-14 13:00:00", 757121));
		flow.add(new Element("2023-04-14 13:10:00", 791238));
		flow.add(new Element("2023-04-14 13:20:00", 915366));
		flow.add(new Element("2023-04-14 13:30:00", 291433));
		flow.add(new Element("2023-04-14 13:40:00", 947538));
		flow.add(new Element("2023-04-14 13:50:00", 658231));
		flow.add(new Element("2023-04-14 14:00:00", 921826));
		flow.add(new Element("2023-04-14 14:10:00", 798872));
		flow.add(new Element("2023-04-14 14:20:00", 719487));
		flow.add(new Element("2023-04-14 14:30:00", 208812));
		flow.add(new Element("2023-04-14 14:40:00", 494158));
		flow.add(new Element("2023-04-14 14:50:00", 340904));
		flow.add(new Element("2023-04-14 15:00:00", 479271));
		flow.add(new Element("2023-04-14 15:10:00", 449593));
		flow.add(new Element("2023-04-14 15:20:00", 38333));
		flow.add(new Element("2023-04-14 15:30:00", 658418));
		flow.add(new Element("2023-04-14 15:40:00", 343742));
		flow.add(new Element("2023-04-14 15:50:00", 560259));
		flow.add(new Element("2023-04-14 16:00:00", 408715));
		flow.add(new Element("2023-04-14 16:10:00", 580695));
		flow.add(new Element("2023-04-14 16:20:00", 846376));
		flow.add(new Element("2023-04-14 16:30:00", 825184));
		flow.add(new Element("2023-04-14 16:40:00", 134195));
		flow.add(new Element("2023-04-14 16:50:00", 875042));
		flow.add(new Element("2023-04-14 17:00:00", 820249));
		flow.add(new Element("2023-04-14 17:10:00", 548512));
		flow.add(new Element("2023-04-14 17:20:00", 767294));
		flow.add(new Element("2023-04-14 17:30:00", 658969));
		flow.add(new Element("2023-04-14 17:40:00", 721026));
		flow.add(new Element("2023-04-14 17:50:00", 847870));
		flow.add(new Element("2023-04-14 18:00:00", 691253));
		flow.add(new Element("2023-04-14 18:10:00", 371152));
		flow.add(new Element("2023-04-14 18:20:00", 769149));
		flow.add(new Element("2023-04-14 18:30:00", 369023));
		flow.add(new Element("2023-04-14 18:40:00", 957822));
		flow.add(new Element("2023-04-14 18:50:00", 201918));
		flow.add(new Element("2023-04-14 19:00:00", 663830));
		flow.add(new Element("2023-04-14 19:10:00", 816906));
		flow.add(new Element("2023-04-14 19:20:00", 926339));
		flow.add(new Element("2023-04-14 19:30:00", 604433));
		flow.add(new Element("2023-04-14 19:40:00", 204590));
		flow.add(new Element("2023-04-14 19:50:00", 328997));
		flow.add(new Element("2023-04-14 20:00:00", 869518));
		flow.add(new Element("2023-04-14 20:10:00", 384830));
		flow.add(new Element("2023-04-14 20:20:00", 810953));
		flow.add(new Element("2023-04-14 20:30:00", 248203));
		flow.add(new Element("2023-04-14 20:40:00", 235276));
		flow.add(new Element("2023-04-14 20:50:00", 108591));
		flow.add(new Element("2023-04-14 21:00:00", 942270));
		flow.add(new Element("2023-04-14 21:10:00", 205783));
		flow.add(new Element("2023-04-14 21:20:00", 931482));
		flow.add(new Element("2023-04-14 21:30:00", 362204));
		flow.add(new Element("2023-04-14 21:40:00", 884978));
		flow.add(new Element("2023-04-14 21:50:00", 660582));
		flow.add(new Element("2023-04-14 22:00:00", 178960));
		flow.add(new Element("2023-04-14 22:10:00", 120826));
		flow.add(new Element("2023-04-14 22:20:00", 559702));
		flow.add(new Element("2023-04-14 22:30:00", 84420));
		flow.add(new Element("2023-04-14 22:40:00", 708180));
		flow.add(new Element("2023-04-14 22:50:00", 277518));
		flow.add(new Element("2023-04-14 23:00:00", 215074));
		flow.add(new Element("2023-04-14 23:10:00", 781654));
		flow.add(new Element("2023-04-14 23:20:00", 442381));
		flow.add(new Element("2023-04-14 23:30:00", 817278));
		flow.add(new Element("2023-04-14 23:40:00", 422763));
		flow.add(new Element("2023-04-14 23:50:00", 135613));
	}

	private void initAttack() {
		attack.add(new Element("2023-04-14 00:00:00", 27));
		attack.add(new Element("2023-04-14 00:10:00", 61));
		attack.add(new Element("2023-04-14 00:20:00", 50));
		attack.add(new Element("2023-04-14 00:30:00", 27));
		attack.add(new Element("2023-04-14 00:40:00", 28));
		attack.add(new Element("2023-04-14 00:50:00", 43));
		attack.add(new Element("2023-04-14 01:00:00", 71));
		attack.add(new Element("2023-04-14 01:10:00", 61));
		attack.add(new Element("2023-04-14 01:20:00", 40));
		attack.add(new Element("2023-04-14 01:30:00", 66));
		attack.add(new Element("2023-04-14 01:40:00", 43));
		attack.add(new Element("2023-04-14 01:50:00", 20));
		attack.add(new Element("2023-04-14 02:00:00", 68));
		attack.add(new Element("2023-04-14 02:10:00", 62));
		attack.add(new Element("2023-04-14 02:20:00", 43));
		attack.add(new Element("2023-04-14 02:30:00", 63));
		attack.add(new Element("2023-04-14 02:40:00", 57));
		attack.add(new Element("2023-04-14 02:50:00", 45));
		attack.add(new Element("2023-04-14 03:00:00", 51));
		attack.add(new Element("2023-04-14 03:10:00", 31));
		attack.add(new Element("2023-04-14 03:20:00", 76));
		attack.add(new Element("2023-04-14 03:30:00", 36));
		attack.add(new Element("2023-04-14 03:40:00", 55));
		attack.add(new Element("2023-04-14 03:50:00", 42));
		attack.add(new Element("2023-04-14 04:00:00", 56));
		attack.add(new Element("2023-04-14 04:10:00", 75));
		attack.add(new Element("2023-04-14 04:20:00", 29));
		attack.add(new Element("2023-04-14 04:30:00", 27));
		attack.add(new Element("2023-04-14 04:40:00", 77));
		attack.add(new Element("2023-04-14 04:50:00", 45));
		attack.add(new Element("2023-04-14 05:00:00", 26));
		attack.add(new Element("2023-04-14 05:10:00", 26));
		attack.add(new Element("2023-04-14 05:20:00", 45));
		attack.add(new Element("2023-04-14 05:30:00", 38));
		attack.add(new Element("2023-04-14 05:40:00", 34));
		attack.add(new Element("2023-04-14 05:50:00", 45));
		attack.add(new Element("2023-04-14 06:00:00", 55));
		attack.add(new Element("2023-04-14 06:10:00", 29));
		attack.add(new Element("2023-04-14 06:20:00", 36));
		attack.add(new Element("2023-04-14 06:30:00", 42));
		attack.add(new Element("2023-04-14 06:40:00", 44));
		attack.add(new Element("2023-04-14 06:50:00", 23));
		attack.add(new Element("2023-04-14 07:00:00", 52));
		attack.add(new Element("2023-04-14 07:10:00", 68));
		attack.add(new Element("2023-04-14 07:20:00", 23));
		attack.add(new Element("2023-04-14 07:30:00", 67));
		attack.add(new Element("2023-04-14 07:40:00", 50));
		attack.add(new Element("2023-04-14 07:50:00", 68));
		attack.add(new Element("2023-04-14 08:00:00", 63));
		attack.add(new Element("2023-04-14 08:10:00", 56));
		attack.add(new Element("2023-04-14 08:20:00", 31));
		attack.add(new Element("2023-04-14 08:30:00", 20));
		attack.add(new Element("2023-04-14 08:40:00", 38));
		attack.add(new Element("2023-04-14 08:50:00", 52));
		attack.add(new Element("2023-04-14 09:00:00", 28));
		attack.add(new Element("2023-04-14 09:10:00", 74));
		attack.add(new Element("2023-04-14 09:20:00", 40));
		attack.add(new Element("2023-04-14 09:30:00", 36));
		attack.add(new Element("2023-04-14 09:40:00", 72));
		attack.add(new Element("2023-04-14 09:50:00", 72));
		attack.add(new Element("2023-04-14 10:00:00", 25));
		attack.add(new Element("2023-04-14 10:10:00", 75));
		attack.add(new Element("2023-04-14 10:20:00", 48));
		attack.add(new Element("2023-04-14 10:30:00", 32));
		attack.add(new Element("2023-04-14 10:40:00", 70));
		attack.add(new Element("2023-04-14 10:50:00", 75));
		attack.add(new Element("2023-04-14 11:00:00", 61));
		attack.add(new Element("2023-04-14 11:10:00", 56));
		attack.add(new Element("2023-04-14 11:20:00", 40));
		attack.add(new Element("2023-04-14 11:30:00", 39));
		attack.add(new Element("2023-04-14 11:40:00", 73));
		attack.add(new Element("2023-04-14 11:50:00", 25));
		attack.add(new Element("2023-04-14 12:00:00", 45));
		attack.add(new Element("2023-04-14 12:10:00", 26));
		attack.add(new Element("2023-04-14 12:20:00", 35));
		attack.add(new Element("2023-04-14 12:30:00", 35));
		attack.add(new Element("2023-04-14 12:40:00", 53));
		attack.add(new Element("2023-04-14 12:50:00", 39));
		attack.add(new Element("2023-04-14 13:00:00", 40));
		attack.add(new Element("2023-04-14 13:10:00", 44));
		attack.add(new Element("2023-04-14 13:20:00", 52));
		attack.add(new Element("2023-04-14 13:30:00", 51));
		attack.add(new Element("2023-04-14 13:40:00", 64));
		attack.add(new Element("2023-04-14 13:50:00", 67));
		attack.add(new Element("2023-04-14 14:00:00", 63));
		attack.add(new Element("2023-04-14 14:10:00", 34));
		attack.add(new Element("2023-04-14 14:20:00", 56));
		attack.add(new Element("2023-04-14 14:30:00", 20));
		attack.add(new Element("2023-04-14 14:40:00", 35));
		attack.add(new Element("2023-04-14 14:50:00", 44));
		attack.add(new Element("2023-04-14 15:00:00", 68));
		attack.add(new Element("2023-04-14 15:10:00", 63));
		attack.add(new Element("2023-04-14 15:20:00", 30));
		attack.add(new Element("2023-04-14 15:30:00", 37));
		attack.add(new Element("2023-04-14 15:40:00", 71));
		attack.add(new Element("2023-04-14 15:50:00", 39));
		attack.add(new Element("2023-04-14 16:00:00", 67));
		attack.add(new Element("2023-04-14 16:10:00", 69));
		attack.add(new Element("2023-04-14 16:20:00", 24));
		attack.add(new Element("2023-04-14 16:30:00", 30));
		attack.add(new Element("2023-04-14 16:40:00", 43));
		attack.add(new Element("2023-04-14 16:50:00", 34));
		attack.add(new Element("2023-04-14 17:00:00", 67));
		attack.add(new Element("2023-04-14 17:10:00", 26));
		attack.add(new Element("2023-04-14 17:20:00", 30));
		attack.add(new Element("2023-04-14 17:30:00", 51));
		attack.add(new Element("2023-04-14 17:40:00", 66));
		attack.add(new Element("2023-04-14 17:50:00", 74));
		attack.add(new Element("2023-04-14 18:00:00", 70));
		attack.add(new Element("2023-04-14 18:10:00", 42));
		attack.add(new Element("2023-04-14 18:20:00", 69));
		attack.add(new Element("2023-04-14 18:30:00", 21));
		attack.add(new Element("2023-04-14 18:40:00", 31));
		attack.add(new Element("2023-04-14 18:50:00", 32));
		attack.add(new Element("2023-04-14 19:00:00", 49));
		attack.add(new Element("2023-04-14 19:10:00", 65));
		attack.add(new Element("2023-04-14 19:20:00", 43));
		attack.add(new Element("2023-04-14 19:30:00", 46));
		attack.add(new Element("2023-04-14 19:40:00", 65));
		attack.add(new Element("2023-04-14 19:50:00", 20));
		attack.add(new Element("2023-04-14 20:00:00", 23));
		attack.add(new Element("2023-04-14 20:10:00", 67));
		attack.add(new Element("2023-04-14 20:20:00", 69));
		attack.add(new Element("2023-04-14 20:30:00", 20));
		attack.add(new Element("2023-04-14 20:40:00", 73));
		attack.add(new Element("2023-04-14 20:50:00", 71));
		attack.add(new Element("2023-04-14 21:00:00", 64));
		attack.add(new Element("2023-04-14 21:10:00", 61));
		attack.add(new Element("2023-04-14 21:20:00", 47));
		attack.add(new Element("2023-04-14 21:30:00", 64));
		attack.add(new Element("2023-04-14 21:40:00", 54));
		attack.add(new Element("2023-04-14 21:50:00", 67));
		attack.add(new Element("2023-04-14 22:00:00", 30));
		attack.add(new Element("2023-04-14 22:10:00", 60));
		attack.add(new Element("2023-04-14 22:20:00", 29));
		attack.add(new Element("2023-04-14 22:30:00", 62));
		attack.add(new Element("2023-04-14 22:40:00", 48));
		attack.add(new Element("2023-04-14 22:50:00", 49));
		attack.add(new Element("2023-04-14 23:00:00", 36));
		attack.add(new Element("2023-04-14 23:10:00", 68));
		attack.add(new Element("2023-04-14 23:20:00", 49));
		attack.add(new Element("2023-04-14 23:30:00", 25));
		attack.add(new Element("2023-04-14 23:40:00", 48));
		attack.add(new Element("2023-04-14 23:50:00", 23));
	}
}

@Data
class Element implements Serializable {

	private String time;

	private Integer dataSize;

	public Element(String time, Integer size) {
		this.time = time;
		this.dataSize = size;
	}
}