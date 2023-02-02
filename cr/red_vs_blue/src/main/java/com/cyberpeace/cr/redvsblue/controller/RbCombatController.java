package com.cyberpeace.cr.redvsblue.controller;

import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "红蓝对抗城市沙盘")
@Slf4j
@RestController
@RequestMapping("/cr/red_vs_blue/combat/")
public class RbCombatController {

	@Autowired
	private RbCombatLogService rbCombatLogService;


//	@ApiOperation("获取攻防日志统计结果")
//	@GetMapping("/combat_records")
//	public RbCombatRecordsRsp getCombatRecords(@ApiParam(name = "room_id", value = "查询id",
//			required = true) @RequestParam("room_id") @NotBlank String roomId) {
//		return rbCombatLogService.getCombatRecords(roomId);
//	}

}
