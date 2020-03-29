package org.xkx.tools.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xkx.tools.base.R;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/time")
@Api(tags = "日期时间", position = 11)
public class DateController {

	@ApiOperation(value = "获取当前时间", httpMethod = "GET", position = 11)
	@RequestMapping("/datetime")
	public R<Map<String, Object>> getCurrentDate(@ApiParam(value = "时区id") @RequestParam(required = false) String id) {
		R<Map<String, Object>> r = new R<>();
		Map<String, Object> data = new LinkedHashMap<>();

		Instant instant = Instant.now();
		ZoneId zoneId = getZoneIdByDefault(id);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		data.put("datetime", df.format(localDateTime));
		data.put("timestamp", instant.toEpochMilli());
		data.put("zoneId", zoneId.getId());
		r.setData(data);

		return r;
	}

	@ApiOperation(value = "获取时区", httpMethod = "GET", position = 21)
	@RequestMapping("/timezone")
	public R<Map<String, Object>> getTimeZone(@ApiParam(value = "时区id") @RequestParam(required = false) String id) {
		R<Map<String, Object>> r = new R<>();
		Map<String, Object> data = new LinkedHashMap<>();

		ZoneId zoneId = getZoneIdByDefault(id);
		data.put("id", zoneId.getId());
		data.put("displayNameFull", zoneId.getDisplayName(TextStyle.FULL, Locale.getDefault()));
		data.put("displayNameNarrow", zoneId.getDisplayName(TextStyle.NARROW, Locale.getDefault()));
		data.put("displayNameShort", zoneId.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
		r.setData(data);
		return r;
	}

	@ApiOperation(value = "获取支持的时区列表", httpMethod = "GET", position = 22)
	@RequestMapping("/timezone/list")
	public R<Map<String, Object>> getTimeZoneList() {
		R<Map<String, Object>> r = new R<>();
		Map<String, Object> data = new LinkedHashMap<>();

		data.put("availableIds", ZoneId.getAvailableZoneIds());
		data.put("availableIds", TimeZone.getAvailableIDs());
		r.setData(data);
		return r;
	}

	@ApiOperation(value = "获取当前语言环境", httpMethod = "GET", position = 31)
	@RequestMapping("/locale")
	public R<Map<String, Object>> getLocale() {
		R<Map<String, Object>> r = new R<>();
		Map<String, Object> data = new LinkedHashMap<>();

		Locale locale = Locale.getDefault();
		data.put("displayName", locale.getDisplayName());
		data.put("country", locale.getCountry());
		data.put("language", locale.getLanguage());
		r.setData(data);
		return r;
	}

	@ApiOperation(value = "获取支持的语言列表", httpMethod = "GET", position = 32)
	@RequestMapping("/locale/list")
	public R<Map<String, Object>> getLocaleList() {
		R<Map<String, Object>> r = new R<>();
		Map<String, Object> data = new LinkedHashMap<>();

		data.put("localeList", Locale.getAvailableLocales());
		data.put("countryList", Locale.getISOCountries());
		data.put("languageList", Locale.getISOLanguages());
		r.setData(data);
		return r;
	}

	private ZoneId getZoneIdByDefault(String id) {
		ZoneId zoneId;
		if (StringUtils.isNotBlank(id)) {
			try {
				zoneId = ZoneId.of(id);
			} catch (Exception e) {
				zoneId = ZoneId.systemDefault();
			}
		} else {
			zoneId = ZoneId.systemDefault();
		}
		return zoneId;
	}
}
