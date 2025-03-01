package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.libraryserver.service.BookClassesService;
import zhijianhu.libraryserver.service.impl.BookClassesServiceImpl;
import zhijianhu.result.Result;
import zhijianhu.vo.ClazzVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */
@RestController
@Slf4j
public class BookClassController {
    @Autowired
    private BookClassesService bookClassesService;

    @GetMapping("/class")
    @Cacheable(value = "clazz", key = "'boss'")
    public Result<List<ClazzVO>> getClazz(){
//        获取所有的父级分类
        log.info("获取所有的父级分类");
        List<ClazzVO> clazz= bookClassesService.getBossClazz();
        return Result.success(clazz);

    }
//    获取所有分类

    @GetMapping("/class/all")
    @Cacheable(value = "clazz", key = "'all'")
    public Result<List<ClazzVO>> getAllClazz(){
        log.info("获取所有分类");
        List<ClazzVO> clazz= bookClassesService.getAllClazz();
        return Result.success(clazz);
    }


}
