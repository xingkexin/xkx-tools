package org.xkx.tools.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xkx.tools.base.R;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
@RestController
@RequestMapping("/time")
@Api(tags = "日期时间", position = 11)
public class DateController {

	@ApiOperation(value = "获取日期时间", httpMethod = "GET", position = 11)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "日期类型，date代表yyyy-MM-dd格式，datetime代表yyyy-MM-dd HH:mm:ss，timestamp代表毫秒时间戳", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "val", value = "需要格式化的值，格式取决于type参数，不传值代表当前时间。", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "zoneId", value = "时区id，不传值代表当前服务器所属时区", required = false, dataType = "String", paramType = "form")
	})
	@RequestMapping("/datetime")
	public R<Map<String, Object>> getDate(String type, String val, String zoneId) {
		R<Map<String, Object>> r = new R<>();
		Map<String, Object> data = new LinkedHashMap<>();

		LocalDateTime localDateTime;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		ZoneId zone = getZoneIdByDefault(zoneId);
		if(StringUtils.isNotBlank(val)) {
			try {
				if("date".equals(type)) {
					LocalDate localDate = LocalDate.parse(val, dateFormatter);
					localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
				}else if("datetime".equals(type)) {
					localDateTime = LocalDateTime.parse(val, dateTimeFormatter);
				}else if("timestamp".equals(type)) {
					long timestamp = NumberUtils.toLong(val, 0);
					localDateTime = Instant.ofEpochMilli(timestamp).atZone(zone).toLocalDateTime();
				} else {
					localDateTime = LocalDateTime.now(zone);
				}
			} catch (Exception e) {
				log.error("出错喽！", e);
				r.error("999", "别瞎搞哦！");
				return r;
			}
		}else {
			localDateTime = LocalDateTime.now(zone);
		}

		ZonedDateTime zonedDateTime = localDateTime.atZone(zone);
		Instant instant = zonedDateTime.toInstant();
		WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
		data.put("zoneId", zone.getId());
		data.put("zoneOffset", zonedDateTime.getOffset().getId());
		data.put("timestamp", instant.toEpochMilli());
		data.put("date", localDateTime.format(dateFormatter));
		data.put("time", localDateTime.format(timeFormatter));
		data.put("datetime", localDateTime.format(dateTimeFormatter));
		data.put("dayOfWeek", localDateTime.getDayOfWeek().getValue());
		data.put("dayOfYear", localDateTime.getDayOfYear());
		data.put("weekOfMonth", localDateTime.get(weekFields.weekOfMonth()));
		data.put("weekOfYear", localDateTime.get(weekFields.weekOfYear()));

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
