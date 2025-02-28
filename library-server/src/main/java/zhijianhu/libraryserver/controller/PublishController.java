package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.entity.Publish;
import zhijianhu.libraryserver.service.PublishService;
import zhijianhu.result.Result;
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
    public Result<List<PublishVO>> list() {
        List<PublishVO> publishVOList = publishService.getPublishList();
        return Result.success(publishVOList);
    }

}
