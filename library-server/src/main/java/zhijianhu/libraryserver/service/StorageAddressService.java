package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.dto.AddressPageDTO;
import zhijianhu.entity.StorageAddress;
import zhijianhu.vo.AddressVO;
import zhijianhu.vo.PageVO;

import java.util.List;

/**
* @author windows
* @description 针对表【storage_address】的数据库操作Service
* @createDate 2025-02-25 21:16:39
*/
public interface StorageAddressService extends IService<StorageAddress> {


     List<AddressVO> getAddress();


     PageVO<AddressVO> getAddressByPage(AddressPageDTO page);
}
