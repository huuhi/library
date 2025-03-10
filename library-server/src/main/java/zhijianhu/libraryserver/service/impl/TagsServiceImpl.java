package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import zhijianhu.entity.Tags;
import zhijianhu.libraryserver.service.TagsService;
import zhijianhu.libraryserver.mapper.TagsMapper;
import org.springframework.stereotype.Service;
import zhijianhu.vo.TagVO;

import java.util.List;

/**
* @author windows
* @description 针对表【tags】的数据库操作Service实现
* @createDate 2025-03-09 18:42:47
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

    @Override
    public List<TagVO> getTags(String name) {
        List<Tags> list = lambdaQuery()
                .like(name != null, Tags::getName, name)
                .list();
        return BeanUtil.copyToList(list,TagVO.class);
    }
}




