package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.dto.PublishPageDTO;
import zhijianhu.entity.Publish;
import zhijianhu.libraryserver.mapper.PublishMapper;
import zhijianhu.libraryserver.service.PublishService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.PageVO;
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

    @Override
    public PageVO<PublishVO> getByPage(PublishPageDTO publishPageDTO) {
        Integer pageNum = publishPageDTO.getPageNum();
        Integer pageSize = publishPageDTO.getPageSize();
        String name = publishPageDTO.getName();
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPage(pageNum);
        pageQuery.setPageSize(pageSize);
        Page<Publish> mpPage = pageQuery.toMpPage();
        Page<Publish> page = lambdaQuery()
                .like(name != null, Publish::getName, name)
                .page(mpPage);
        return PageVO.of(page, PublishVO.class);
    }
}



