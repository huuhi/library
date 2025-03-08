package zhijianhu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryStatVO {
    private String name;   // 分类名称
    private Integer value; // 图书数量
}