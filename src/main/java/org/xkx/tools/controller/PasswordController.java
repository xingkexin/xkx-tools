package org.xkx.tools.controller;

import com.baomidou.mybatisplus.core.toolkit.AES;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@Api(tags = "加解密服务")
public class PasswordController {

	@ApiOperation(value = "使用AES算法加密", httpMethod = "POST", position = 11)
	@RequestMapping("/aes/encrypt")
	public String encryptByAES(@ApiParam(value = "密钥", required = true) @RequestParam String key,
	                           @ApiParam(value = "需要加密的明文", required = true) @RequestParam String data){
		if(StringUtils.isBlank(key) || StringUtils.isBlank(data)) {
			return "缺少必填项！";
		}
		String result = AES.encrypt(data, key);
		return result;
	}

	@ApiOperation(value = "使用AES算法解密", httpMethod = "POST", position = 12)
	@RequestMapping("/aes/decrypt")
	public String decryptByAES(@ApiParam(value = "密钥", required = true) @RequestParam String key,
	                           @ApiParam(value = "需要解密的密文", required = true) @RequestParam String data){
		if(StringUtils.isBlank(key) || StringUtils.isBlank(data)) {
			return "缺少必填项！";
		}
		String result = AES.decrypt(data, key);
		return result;
	}
}
