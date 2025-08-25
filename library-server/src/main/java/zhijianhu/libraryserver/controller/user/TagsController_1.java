package zhijianhu.libraryserver.controller.user;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.TagDTO;
import zhijianhu.entity.Tags;
import zhijianhu.libraryserver.service.TagsService;
import zhijianhu.result.Result;
import zhijianhu.vo.TagVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/9
 * 说明:标签
 */
@RestController
@RequestMapping("/tag")
@Slf4j
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @PostMapping("/add")
    public Result<Void> addTag(@RequestBody TagDTO tagDTO){
        log.info("添加标签:{}",tagDTO);
        Tags tag = BeanUtil.copyProperties(tagDTO, Tags.class);
        boolean save = tagsService.save(tag);
        return save? Result.success() : Result.error(MessageConstant.ADD_TAG_FAIL);
    }
    @GetMapping("/getTag")
    public Result<List<TagVO>> getTag(String name){
        log.info("获取标签: {}",name);
        List<TagVO> tags= tagsService.getTags(name);
        return Result.success(tags);
    }

}
