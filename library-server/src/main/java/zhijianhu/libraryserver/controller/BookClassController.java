package zhijianhu.libraryserver.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import zhijianhu.dto.ClassDTO;
import zhijianhu.dto.ClassPageDTO;
import zhijianhu.entity.BookClasses;
import zhijianhu.libraryserver.service.BookClassesService;
import zhijianhu.libraryserver.service.impl.BookClassesServiceImpl;
import zhijianhu.result.Result;
import zhijianhu.vo.ClazzVO;
import zhijianhu.vo.PageVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */
@RestController
@Slf4j
@RequestMapping("/class")
public class BookClassController {
    @Autowired
    private BookClassesService bookClassesService;

    @GetMapping
    @Cacheable(value = "clazz", key = "'boss'")
    public Result<List<ClazzVO>> getClazz(){
//        获取所有的父级分类
        log.info("获取所有的父级分类");
        List<ClazzVO> clazz= bookClassesService.getBossClazz();
        return Result.success(clazz);

    }
//    获取所有分类
    @GetMapping("/all")
    @Cacheable(value = "clazz", key = "'all'")
    public Result<List<ClazzVO>> getAllClazz(){
        log.info("获取所有分类");
        List<ClazzVO> clazz= bookClassesService.getAllClazz();
        return Result.success(clazz);
    }
    @GetMapping("/page")
    public Result<PageVO<ClazzVO>> getAllClazz(@ModelAttribute ClassPageDTO classPageDTO){
        log.info("分页获取所有分类");
        PageVO<ClazzVO> clazz= bookClassesService.getPageClazz(classPageDTO);
        return Result.success(clazz);
    }
//根据id获取分类
    @GetMapping("/{id}")
    public Result<ClazzVO> getClazzById(@PathVariable("id") Integer id){
        log.info("根据id获取分类,id={}",id);
        ClazzVO clazz= bookClassesService.getClazzById(id);
        return Result.success(clazz);
    }
//   添加分类
    @PostMapping("/add")
    @CacheEvict(value = "clazz", allEntries = true)
    public Result<String> addClazz(@RequestBody ClassDTO bookClassDTO){
        log.info("添加分类,bookClasses={}",bookClassDTO);
        BookClasses bookClasses = BeanUtil.copyProperties(bookClassDTO, BookClasses.class);
        boolean save = bookClassesService.save(bookClasses);
        return save? Result.success("添加成功") : Result.error("添加失败");
    }
//    修改分类
    @PutMapping("/update")
    @CacheEvict(value = "clazz", allEntries = true)
    public Result<String> updateClazz(@RequestBody BookClasses bookClasses){
        log.info("修改分类,bookClasses={}",bookClasses);
        boolean update = bookClassesService.updateById(bookClasses);
        return update? Result.success("修改成功") : Result.error("修改失败");
    }
    //    删除分类
    @DeleteMapping("/{id}")
    @CacheEvict(value = "clazz", allEntries = true)
    public Result<String> deleteClazz(@PathVariable("id") Integer id){
        log.info("删除分类,id={}",id);
        boolean remove = bookClassesService.removeById(id);
        return remove? Result.success("删除成功") : Result.error("删除失败");
    }

}
