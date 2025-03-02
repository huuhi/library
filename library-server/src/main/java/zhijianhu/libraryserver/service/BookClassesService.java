package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.ClassPageDTO;
import zhijianhu.entity.BookClasses;
import zhijianhu.vo.ClazzVO;
import zhijianhu.vo.PageVO;

import java.util.List;

/**
* @author windows
* @description 针对表【book_classes】的数据库操作Service
* @createDate 2025-02-25 21:16:30
*/
public interface BookClassesService extends IService<BookClasses> {
    String getFullPath(Integer id);

     List<ClazzVO> getBossClazz();

    List<Integer> getAllSubCategoryIds(Integer categoryId);


    List<ClazzVO> getAllClazz();

    ClazzVO getClazzById(Integer id);

    PageVO<ClazzVO> getPageClazz(ClassPageDTO classPageDTO);
}
