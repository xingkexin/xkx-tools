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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 网站的文件夹表
 * </p>
 *
 * @author 小天狼x
 * @since 2020-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@TableName("website_folder")
public class WebsiteFolder extends Model<WebsiteFolder> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 文件夹名称
     */
    @TableField("folder_name")
    private String folderName;

    /**
     * 文件夹的顺序
     */
    @TableField("folder_sort")
    private Integer folderSort;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Website> websiteList = new ArrayList<>();


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
