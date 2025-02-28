package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.libraryserver.service.StorageAddressService;
import zhijianhu.libraryserver.service.impl.StorageAddressServiceImpl;
import zhijianhu.result.Result;
import zhijianhu.vo.AddressVO;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/28
 * 说明:
 */
@RestController
@Slf4j
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private StorageAddressService storageAddressService;

//  获取所有地址信息
    @GetMapping("/get")
    public Result<List<AddressVO>> getAddress() {
        log.info("获取所有地址信息");
        List<AddressVO> addressVOList = storageAddressService.getAddress();
        return Result.success(addressVOList);
    }

}
