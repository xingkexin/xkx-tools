package org.xkx.tools.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xkx.tools.base.R;
import org.xkx.tools.utils.NumberUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Api(tags = "转换器")
@Slf4j
@RestController
@RequestMapping("/converter")
public class ConverterController {

	@ApiOperation(value = "获取不同进制数字", httpMethod = "GET", position = 11)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "originalBaseNum", value = "数字的进制数", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "str", value = "数字", required = true, dataType = "String", paramType = "query"),
	})
	@RequestMapping("/convertNumber")
	public R convertNumber(int originalBaseNum, String str) {
		R r = new R();
		if(!(originalBaseNum == 2 || originalBaseNum == 8 || originalBaseNum == 10 || originalBaseNum == 16)) {
			r.error(R.CodeEnum.PARAM_ERROR.getCode(), R.CodeEnum.PARAM_ERROR.getMsg());
			return r;
		}
		long num = NumberUtils.parseNumber(str, originalBaseNum);
		String num2 = NumberUtils.formatNumber(num, 2);
		String num8 = NumberUtils.formatNumber(num, 8);
		String num10 = NumberUtils.formatNumber(num, 10);
		String num16 = NumberUtils.formatNumber(num, 16);

		Map<String, String> data = new LinkedHashMap<>();
		data.put("num2", num2);
		data.put("num8", num8);
		data.put("num10", num10);
		data.put("num16", num16);
		r.setData(data);
		return r;
	}

	@ApiOperation(value = "将明文编码为Unicode码", httpMethod = "GET", position = 21)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "str", value = "明文字符串", required = true, dataType = "String", paramType = "query"),
	})
	@RequestMapping("/encodeToUnicode")
	public String encodeToUnicode(String str) {
		StringBuilder sb = new StringBuilder();
//		int length = str.codePointCount(0, str.length());
		for(int i=0; i<str.length(); i++) {
			int code = str.codePointAt(i);
			sb.append("\\u").append(Integer.toHexString(code));
			if(code > 65535) {
				i++;
			}
		}
		return sb.toString();
	}

	@ApiOperation(value = "将Unicode码解码为明文", httpMethod = "GET", position = 22)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "str", value = "Unicode字符串", required = true, dataType = "String", paramType = "query"),
	})
	@RequestMapping("/decodeFromUnicode")
	public String decodeFromUnicode(String str) {
		StringBuilder sb = new StringBuilder();
		String[] codes = str.split("\\\\[uU]");
		for(String code : codes) {
			if(code.length() == 0) continue;
			int codePoint = 0;
			code = code.toLowerCase();
			for(char c : code.toCharArray()) {
				switch (c) {
					case 'a': codePoint = codePoint * 16 + 10;break;
					case 'b': codePoint = codePoint * 16 + 11;break;
					case 'c': codePoint = codePoint * 16 + 12;break;
					case 'd': codePoint = codePoint * 16 + 13;break;
					case 'e': codePoint = codePoint * 16 + 14;break;
					case 'f': codePoint = codePoint * 16 + 15;break;
					default: codePoint = codePoint * 16 + (c - 48);break;
				}
			}
			if(codePoint >= Character.MIN_CODE_POINT && codePoint <= Character.MAX_CODE_POINT) {
				sb.appendCodePoint(codePoint);
			}
		}
		return sb.toString();
	}
}
