package zhijianhu.libraryserver.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.MessageConstant;
import zhijianhu.dto.AddressPageDTO;
import zhijianhu.entity.StorageAddress;
import zhijianhu.libraryserver.annotation.OperateLog;
import zhijianhu.libraryserver.service.StorageAddressService;
import zhijianhu.result.Result;
import zhijianhu.vo.AddressVO;
import zhijianhu.vo.PageVO;

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
    private final StorageAddressService storageAddressService;

    public AddressController(StorageAddressService storageAddressService) {
        this.storageAddressService = storageAddressService;
    }


    //  获取所有地址信息
    @GetMapping("/get")
    @Cacheable(value="addressList",key="'all'")
    public Result<List<AddressVO>> getAddress() {
        log.info("获取所有地址信息");
        List<AddressVO> addressVOList = storageAddressService.getAddress();
        return Result.success(addressVOList);
    }
//    根据id获取地址详细信息
    @GetMapping("/get/{id}")
    public Result<StorageAddress> getAddressById(@PathVariable("id") Integer id) {
        log.info("根据id获取地址信息+{}",id);
        StorageAddress address= storageAddressService.getById(id);
        return Result.success(address);
    }
//    分页获取地址信息
    @GetMapping("/page")
    public Result<PageVO<AddressVO>> getAddressByPage(AddressPageDTO page){
        log.info("分页获取地址信息:{}",page);
         PageVO<AddressVO> pageVO = storageAddressService.getAddressByPage(page);
         return Result.success(pageVO);
    }



//    修改地址
    @PostMapping("/update")
    @CacheEvict(value="addressList",allEntries=true)
    @OperateLog
    public Result<Void> updateAddress(@RequestBody StorageAddress address) {
        boolean save = storageAddressService.updateById(address);
        return save? Result.success(): Result.error(MessageConstant.UPDATE_FAIL);
    }
//    新增地址
    @PostMapping("/add")
    @CacheEvict(value="addressList",allEntries=true)
    public Result<Void> addAddress(@RequestBody StorageAddress address) {
        boolean save = storageAddressService.save(address);
        return save? Result.success(): Result.error(MessageConstant.ADD_FAIL);
    }
    @DeleteMapping("/delete/{id}")
    @CacheEvict(value="addressList",allEntries=true)
    @OperateLog
    public Result<Void> deleteAddress(@PathVariable("id") Integer id) {
        boolean remove = storageAddressService.removeById(id);
        return remove? Result.success() : Result.error(MessageConstant.DELETE_FAIL);
    }


}
