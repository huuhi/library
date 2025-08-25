package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.entity.Tags;
import zhijianhu.vo.TagVO;

import java.util.List;

/**
* @author windows
* @description 针对表【tags】的数据库操作Service
* @createDate 2025-03-09 18:42:47
*/
public interface TagsService extends IService<Tags> {

    List<TagVO> getTags(String name);
}
