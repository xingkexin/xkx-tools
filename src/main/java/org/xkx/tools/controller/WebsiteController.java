package org.xkx.tools.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xkx.tools.base.R;
import org.xkx.tools.domain.Website;
import org.xkx.tools.domain.WebsiteFolder;
import org.xkx.tools.service.WebsiteFolderService;
import org.xkx.tools.service.WebsiteService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站表 前端控制器
 * </p>
 *
 * @author 小天狼x
 * @since 2020-06-26
 */
@RestController
@RequestMapping("/website")
@Api(tags = "网站收藏", position = 31)
public class WebsiteController {

	@Autowired
	private WebsiteFolderService websiteFolderService;

	@Autowired
	private WebsiteService websiteService;

	@ApiOperation(value = "获取所有系统收藏的网站", httpMethod = "GET", position = 11)
	@RequestMapping("/list")
	public R<List<WebsiteFolder>> getList(){
		R<List<WebsiteFolder>> r = new R<>();
		QueryWrapper<WebsiteFolder> websiteFolderQuery = new QueryWrapper<>();
		websiteFolderQuery.orderByAsc("folder_sort");
		List<WebsiteFolder> websiteFolderList = websiteFolderService.list(websiteFolderQuery);

		List<WebsiteFolder> datas = new ArrayList<>();
		for(WebsiteFolder item : websiteFolderList) {
//			Map<String, Object> data = new LinkedHashMap<>();
//			data.put("folder_name", item.getFolderName());
			QueryWrapper<Website> websiteQuery = new QueryWrapper<>();
			websiteQuery.eq("webste_folder_id", item.getId());
			websiteQuery.orderByAsc("website_sort");
			List<Website> websiteList = websiteService.list(websiteQuery);
//			data.put("children", websiteList);
			item.setWebsiteList(websiteList);
			datas.add(item);
		}

		r.setData(datas);
		return r;
	}
}

