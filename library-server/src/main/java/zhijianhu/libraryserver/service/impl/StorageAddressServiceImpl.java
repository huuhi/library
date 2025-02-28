package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.StorageAddress;
import zhijianhu.libraryserver.mapper.StorageAddressMapper;
import zhijianhu.libraryserver.service.StorageAddressService;
import zhijianhu.vo.AddressVO;

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
        List<AddressVO> addressVOList = new ArrayList<>();
        list.forEach(storageAddress ->{
            String address = storageAddress.getAddress();
            Integer id = storageAddress.getId();
            AddressVO addressVO = AddressVO
                    .builder()
                    .id(id)
                    .address(address)
                    .build();
            addressVOList.add(addressVO);
        });
        return addressVOList;
    }
}




