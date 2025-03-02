package zhijianhu.libraryserver.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import zhijianhu.dto.PublishDTO;
import zhijianhu.dto.PublishPageDTO;
import zhijianhu.entity.Publish;
import zhijianhu.libraryserver.service.PublishService;
import zhijianhu.result.Result;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.PublishVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */
@RestController
@RequestMapping("/publish")
@Slf4j
public class PublishController {
    @Autowired
    private PublishService publishService;
    @GetMapping("/list")
    @Cacheable(value="publishList",key="'all'")
    public Result<List<PublishVO>> list() {
        List<PublishVO> publishVOList = publishService.getPublishList();
        return Result.success(publishVOList);
    }
//    分页
    @GetMapping("/page")
    public Result<PageVO<PublishVO>> page(@ModelAttribute PublishPageDTO publishPageDTO){
        log.info("分页查询出版社列表");
        PageVO<PublishVO>  page=  publishService.getByPage(publishPageDTO);
        return Result.success(page);
    }

//    根据id获取出版社信息
    @GetMapping("/get/{id}")
    public Result<Publish> get(@PathVariable("id") Integer id) {
        Publish publish = publishService.getById(id);
        return Result.success(publish);
    }
//    添加
    @PostMapping("/add")
    @CacheEvict(value="publishList",allEntries=true)
    public Result<Void> add(@RequestBody PublishDTO publishDTO) {
        Publish publish = BeanUtil.copyProperties(publishDTO, Publish.class);
        boolean save = publishService.save(publish);
        return save ? Result.success() : Result.error("添加出版社失败");
    }
//    修改
    @PutMapping("/update")
    @CacheEvict(value="publishList",allEntries=true)
    public Result<Void> update(@RequestBody Publish publish) {
        boolean b = publishService.updateById(publish);
        return b ? Result.success() : Result.error("修改出版社失败");
    }
//    删除
    @DeleteMapping("/delete/{id}")
    @CacheEvict(value="publishList",allEntries=true)
    public Result<Void> delete(@PathVariable("id") Integer id) {
        boolean b = publishService.removeById(id);
        return b ? Result.success() : Result.error("删除出版社失败");
    }




}
