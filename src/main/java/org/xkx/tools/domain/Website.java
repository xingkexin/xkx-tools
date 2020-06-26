package org.xkx.tools.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 网站表
 * </p>
 *
 * @author 小天狼x
 * @since 2020-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@TableName("website")
public class Website extends Model<Website> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 网站文件夹的id
     */
    @TableField("webste_folder_id")
    private Long websteFolderId;

    /**
     * 网站名称
     */
    @TableField("website_name")
    private String websiteName;

    /**
     * 网站url
     */
    @TableField("website_url")
    private String websiteUrl;

    /**
     * 网站的排序，数字越小越靠前
     */
    @TableField("website_sort")
    private Integer websiteSort;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
