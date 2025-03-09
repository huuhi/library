package zhijianhu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/9
 * 说明:发送帖子的DTO
 */
@Data
public class PostDTO implements Serializable {

    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private String image;

//    标签id
    private List<Integer> tagsId;



}
