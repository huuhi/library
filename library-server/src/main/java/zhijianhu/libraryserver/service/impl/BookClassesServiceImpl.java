package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhijianhu.entity.BookClasses;
import zhijianhu.libraryserver.mapper.BookClassesMapper;
import zhijianhu.libraryserver.service.BookClassesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author windows
* @description 针对表【book_classes】的数据库操作Service实现
* @createDate 2025-02-25 21:16:30
*/
@Service
public class BookClassesServiceImpl extends ServiceImpl<BookClassesMapper, BookClasses>
    implements BookClassesService {

    @Autowired
    private BookClassesMapper bookClassMapper;  // MyBatis-Plus的Mapper

    /**
     * 根据分类ID获取完整路径
     * @param classId 分类ID
     * @return 路径字符串（如 "文学/小说/科幻小说"）
     */
    public String getFullPath(Integer classId) {
        List<String> pathSegments = new ArrayList<>();
        findParent(classId, pathSegments);
        Collections.reverse(pathSegments);  // 反转顺序（从顶级到子类）
        return String.join("/", pathSegments);
    }

    private void findParent(Integer classId, List<String> pathSegments) {
        BookClasses current = bookClassMapper.selectById(classId);
        if (current == null) return;

        pathSegments.add(current.getName());

        if (current.getParentId() != null) {
            findParent(current.getParentId(), pathSegments);  // 递归查找父分类
        }
    }

}




