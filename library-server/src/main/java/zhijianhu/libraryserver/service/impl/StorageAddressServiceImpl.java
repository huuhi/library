package zhijianhu.libraryserver.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.stereotype.Service;
import zhijianhu.dto.AddressPageDTO;
import zhijianhu.entity.StorageAddress;
import zhijianhu.libraryserver.mapper.StorageAddressMapper;
import zhijianhu.libraryserver.service.StorageAddressService;
import zhijianhu.query.PageQuery;
import zhijianhu.vo.AddressVO;
import zhijianhu.vo.PageVO;

import java.util.ArrayList;
import java.util.List;

/**
* @author windows
* @description 针对表【storage_address】的数据库操作Service实现
* @createDate 2025-02-25 21:16:39
*/
@Service
public class StorageAddressServiceImpl extends ServiceImpl<StorageAddressMapper, StorageAddress>
    implements StorageAddressService {


    @Override
    public List<AddressVO> getAddress() {
//        查询所有的地址
        List<StorageAddress> list = list();
        return BeanUtil.copyToList(list, AddressVO.class);
    }

    @Override
    public PageVO<AddressVO> getAddressByPage(AddressPageDTO page) {
        //分页查询地址
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        String name = page.getAddress();
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPage(pageNum);
        pageQuery.setPageSize(pageSize);
        Page<StorageAddress> mpPage = pageQuery.toMpPage();
        Page<StorageAddress> page1 = lambdaQuery()
                .like(name != null, StorageAddress::getAddress, name)
                .page(mpPage);
        return PageVO.of(page1, AddressVO.class);
    }
}




