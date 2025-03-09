package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zhijianhu.entity.Tags;
import zhijianhu.libraryserver.service.TagsService;
import zhijianhu.libraryserver.mapper.TagsMapper;
import org.springframework.stereotype.Service;

/**
* @author windows
* @description 针对表【tags】的数据库操作Service实现
* @createDate 2025-03-09 18:42:47
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

}




