package org.xkx.tools.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/commons")
@Api(tags = "UUID工具", position = 21)
@RestController
public class UuidController {

	@ApiOperation(value = "获取uuid", position = 11, httpMethod = "GET")
	@RequestMapping("/uuid")
	public String getUuid() {
		return UUID.randomUUID().toString();
	}

	@ApiOperation(value = "获取没有中划线的uuid", position = 12, httpMethod = "GET")
	@RequestMapping("/uuidWithoutLine")
	public String getUuidWithoutLine() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
