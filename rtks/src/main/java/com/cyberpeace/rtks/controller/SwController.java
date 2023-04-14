package com.cyberpeace.rtks.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.rtks.constant.RtksCommonConstants;
import com.cyberpeace.rtks.dao.mapper.RtksSituationMapper;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Api(tags = "RTKS-SW")
@RestController
@RequestMapping("/rtks/situation")
public class SwController {

	Map<String, String> roleMap = Maps.newHashMap();

	Map<String, String> userMap = Maps.newHashMap();

	@Autowired
	private RtksSituationService rtksSituationService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private RtksSituationMapper rtksSituationMapper;

	@PostConstruct
	void init() {
		roleMap.put("c3fe96a8de569cdb92e273c8809a73b7", "渗透测试工程师");
		roleMap.put("e25dc8c9d15ddd621f16616083d68726", "安全开发工程师");
		roleMap.put("aeec2fc773691f459105b0323c3e1711", "安全取证工程师");
		roleMap.put("f7753c4236c6b6981a577118a686aeb3", "逆向分析工程师");
		roleMap.put("542fc3e2caf0fa9b4935432ffe4daafa", "安全运维工程师");
	}

	@ApiOperation("插入匹配度假数据")
	@GetMapping("/test")
	public BaseRsp test() {
		List<UserInfo> userInfoList = remoteUserService.getAllUsers();

		for (UserInfo userInfo : userInfoList) {
			if (userInfo.getUserName().startsWith("sw_user_")) {
				userMap.put(userInfo.getUserId(), userInfo.getName());
			}
		}
		Random random = new Random();
		List<Record> recordList = Lists.newArrayList();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse("2023-03-14 09:00:00", formatter);

		List<Record> finalRecordList = recordList;
		roleMap.forEach((roleId, roleName) -> {
			userMap.forEach((userId, name) -> {
				Record r = new Record();
				r.setMatch(random.nextInt(101));
				r.setUserId(userId);
				r.setName(name);
				r.setRoleId(roleId);
				r.setRoleName(roleName);

				int day = random.nextInt(31);
				int hour = random.nextInt(8);
				int minu = random.nextInt(60);
				int sec = random.nextInt(60);

				LocalDateTime localDateTime = start.plusDays(day).plusHours(hour).plusMinutes(minu)
						.plusSeconds(sec);
				r.setTime(localDateTime);

				finalRecordList.add(r);

				if (r.getMatch() >= RtksCommonConstants.EXCELLENT_MATCH) {
					LocalDateTime qualifiedTime = r.getTime().minusDays(1);
					qualifiedTime = qualifiedTime.withHour(random.nextInt(8) + 9);
					qualifiedTime = qualifiedTime.withMinute(random.nextInt(60));
					qualifiedTime = qualifiedTime.withSecond(random.nextInt(60));

					Record qualifiedRecord = new Record();
					qualifiedRecord.setUserId(userId);
					qualifiedRecord.setName(name);
					qualifiedRecord.setRoleName(roleName);
					qualifiedRecord.setRoleId(roleId);
					qualifiedRecord.setTime(qualifiedTime);
					qualifiedRecord.setMatch(RtksCommonConstants.QUALIFIED_MATCH);

					finalRecordList.add(qualifiedRecord);
				}

			});
		});

		recordList = recordList.stream().sorted(Comparator.comparing(Record::getTime))
				.collect(Collectors.toList());

		// 清空旧的log数据
		rtksSituationMapper.clearLog();

		for (Record record : recordList) {
			rtksSituationService.recordFake(record.getUserId(), record.getName(),
					record.getRoleId(), record.getRoleName(), record.getMatch(), record.getTime());
		}

		return new BaseRsp();
	}

	@ApiOperation("/1")
	@PostMapping("/add_user")
	public BaseRsp test1(@RequestBody Req req) {
		String url = "http://10.4.1.251/api/mp/auth/users/add";
		String username = "sw_user_" + IDGeneratorUtil.getID();
		AddUser addUser = new AddUser();
		addUser.setName(req.getName());
		addUser.setUsername(username);
		addUser.getOrg_ids().add(req.getOrg());
		HttpClientUtil.post(url, addUser, Object.class);

		return new BaseRsp();
	}
}

@Data
class AddUser implements Serializable {

	private String avatar = "/api/mp/avatar/1.png";

	private String description = "";

	private String name;

	private String password = "d8165e80c4abed2dcc1dd06df1a8be31";

	private String username;

	private List<String> org_ids = Lists.newArrayList();

	private List<String> role_ids = Stream.of("d954b242db104037ba20328e92117f5d")
			.collect(Collectors.toList());
}

@Data
class Req implements Serializable {

	private String org;

	private String name;
}

@Data
class Record implements Serializable {

	private String userId;

	private String name;

	private String roleId;

	private String roleName;

	private Integer match;

	private LocalDateTime time;
}
