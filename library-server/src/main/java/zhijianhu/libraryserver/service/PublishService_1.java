package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.PublishPageDTO;
import zhijianhu.entity.Publish;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.PublishVO;

import java.util.List;

/**
* @author windows
* @description 针对表【publish】的数据库操作Service
* @createDate 2025-02-28 14:40:35
*/
public interface PublishService extends IService<Publish> {

    List<PublishVO> getPublishList();

    PageVO<PublishVO> getByPage(PublishPageDTO publishPageDTO);
}
