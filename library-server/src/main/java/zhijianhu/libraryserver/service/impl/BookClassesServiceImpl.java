package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhijianhu.entity.BookClasses;
import zhijianhu.libraryserver.mapper.BookClassesMapper;
import zhijianhu.libraryserver.service.BookClassesService;
import zhijianhu.vo.ClazzVO;

import java.util.*;

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

    @Override
    public List<ClazzVO> getBossClazz() {
        List<BookClasses> list = lambdaQuery().isNull(BookClasses::getParentId)
                .list();
        return BeanUtil.copyToList(list, ClazzVO.class);
    }

    private void findParent(Integer classId, List<String> pathSegments) {
        BookClasses current = bookClassMapper.selectById(classId);
        if (current == null) return;

        pathSegments.add(current.getName());

        if (current.getParentId() != null) {
            findParent(current.getParentId(), pathSegments);  // 递归查找父分类
        }
    }

    // BookClassesServiceImpl.java
    @Override
    public List<Integer> getAllSubCategoryIds(Integer categoryId) {
        // 递归查询所有子分类ID（含自身）
        List<BookClasses> allCategories = this.list();
        Map<Integer, List<Integer>> parentChildMap = new HashMap<>();

        // 构建父-子分类映射
        allCategories.forEach(category -> {
            Integer parentId = category.getParentId() != null ?
                             category.getParentId() : 0;
            parentChildMap.computeIfAbsent(parentId, k -> new ArrayList<>())
                         .add(category.getId());
        });

        // 递归收集所有子分类
        List<Integer> result = new ArrayList<>();
        collectSubCategories(categoryId, parentChildMap, result);
        return result;
    }

    @Override
    public List<ClazzVO> getAllClazz() {
        List<BookClasses> list = this.list();
        ArrayList<ClazzVO> clazzVOList = new ArrayList<>();
        list.forEach(item -> {
            Integer id = item.getId();
            String clazz = this.getFullPath(id);
            ClazzVO clazzVO = ClazzVO
                    .builder()
                    .id(id)
                    .name(clazz)
                    .build();
            clazzVOList.add(clazzVO);
        });
        return clazzVOList;
    }

    private void collectSubCategories(Integer currentId,
                                     Map<Integer, List<Integer>> parentChildMap,
                                     List<Integer> result) {
        result.add(currentId);
        List<Integer> children = parentChildMap.get(currentId);
        if (children != null) {
            children.forEach(childId ->
                collectSubCategories(childId, parentChildMap, result));
        }
    }
}




