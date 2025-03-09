package zhijianhu.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 
* @TableName post_tags
*/
@Data
@Builder
@TableName("post_tags")
public class PostTags implements Serializable {
    @TableField("post_id")
    private Integer postId;
    @TableField("tag_id")
    private Integer tagId;
    /**
    * 
    */
    @TableField("created_time")
    private LocalDateTime createdTime;



}
