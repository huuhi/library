package zhijianhu.libraryserver.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.TagDTO;
import zhijianhu.entity.Tags;
import zhijianhu.libraryserver.service.TagsService;
import zhijianhu.result.Result;

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
    public Result<Void> addTag(@RequestBody Tags tag){
        log.info("添加标签:{}",tag);
        boolean save = tagsService.save(tag);
        return save? Result.success() : Result.error(MessageConstant.ADD_TAG_FAIL);
    }


}
