package org.xkx.tools.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xkx.tools.dao.WebsiteDao;
import org.xkx.tools.domain.Website;
import org.xkx.tools.service.WebsiteService;

/**
 * <p>
 * 网站表 服务实现类
 * </p>
 *
 * @author 小天狼x
 * @since 2020-06-26
 */
@Service
public class WebsiteServiceImpl extends ServiceImpl<WebsiteDao, Website> implements WebsiteService {

}
