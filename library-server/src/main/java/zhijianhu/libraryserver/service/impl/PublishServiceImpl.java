package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.Publish;
import zhijianhu.libraryserver.mapper.PublishMapper;
import zhijianhu.libraryserver.service.PublishService;
import zhijianhu.vo.PublishVO;

import java.util.List;

/**
* @author windows
* @description 针对表【publish】的数据库操作Service实现
* @createDate 2025-02-28 14:40:35
*/
@Service
public class PublishServiceImpl extends ServiceImpl<PublishMapper, Publish>
    implements PublishService {

    @Override
    public List<PublishVO> getPublishList() {
        List<Publish> list = this.list();
        return BeanUtil.copyToList(list, PublishVO.class);
    }
}




