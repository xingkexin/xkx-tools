package org.xkx.tools.controller;

import cn.hutool.core.net.URLEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("url")
@Api(tags = "URL编解码")
public class UrlController {

	@ApiOperation(value = "智能编码")
	@PostMapping("encode/smart")
	public String encodeBySmart(String str, String charsetStr) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		Charset charset;
		try {
			charset = Charset.forName(charsetStr);
		} catch (Exception e) {
			return "请正确填写字符集！";
		}
		URLEncoder encoder = new URLEncoder();
		StringBuffer sb = new StringBuffer();
		// 判断是否为完整url
		if (str.matches("https?://.+")) {
			// ?左侧无需编码
			String[] split = str.split("\\?", 2);
			if (split.length == 1) {
				return str;
			} else {
				sb.append(split[0]).append("?");
				// #分隔查询参数和锚定位
				String[] split1 = split[1].split("#", 2);

				// 编码查询参数部分
				String queryParamsStr = split1[0];
				String[] queryParamArray = queryParamsStr.split("&");
				queryParamsStr = Arrays.stream(queryParamArray).map(queryParam -> {
					String[] nameAndValue = queryParam.split("=", 2);
					if (nameAndValue.length == 2) {
						nameAndValue[1] = encoder.encode(nameAndValue[1], charset);
					}
					return String.join("=", nameAndValue);
				}).collect(Collectors.joining("&"));
				sb.append(queryParamsStr);

				// 编码锚定位
				if (split1.length == 2) {
					String anchorStr = split1[1];
					sb.append("#");
					sb.append(encoder.encode(anchorStr, charset));
				}
			}
			return sb.toString();
		} else {
			return encoder.encode(str, charset);
		}
	}

	@ApiOperation(value = "2 直接编码")
	@PostMapping("encode/direct")
	public String encodeByDirect(String str, String charsetStr) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		URLEncoder encoder = new URLEncoder();
		try {
			String encodedStr = encoder.encode(str, Charset.forName(charsetStr));
			return encodedStr;
		} catch (Exception e) {
			return "请正确填写字符集！";
		}
	}

	@ApiOperation(value = "3 解码")
	@PostMapping("decode")
	public String decode(String str, String charsetStr) {
		if (StringUtils.isBlank(str)) {
			return "";
		}

		try {
			String decodedStr = URLDecoder.decode(str, charsetStr);
			return decodedStr;
		} catch (UnsupportedEncodingException e) {
			return "请正确填写字符集！";
		}
	}
}
