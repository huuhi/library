package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.PostTags;
import zhijianhu.libraryserver.mapper.PostTagsMapper;
import zhijianhu.libraryserver.service.PostTagsService;

/**
* @author windows
* @description 针对表【post_tags】的数据库操作Service实现
* @createDate 2025-03-09 13:41:04
*/
@Service
public class PostTagsServiceImpl extends ServiceImpl<PostTagsMapper, PostTags>
    implements PostTagsService {

}




