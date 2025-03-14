package zhijianhu.libraryserver.controller.admin;

import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zhijianhu.constant.MessageConstant;
import zhijianhu.result.Result;
import zhijianhu.utils.AliOssUtil;


import java.io.IOException;


/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/11
 * 说明:
 */
@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliOssUtil aliOssUtil;
//    file
    @PostMapping("/common/upload")
    public Result<String> upload(MultipartFile file) {
//        将文件转换成为字节数组
        log.info("上传文件{}",file.getOriginalFilename());
        String upload;
        try {
            upload = aliOssUtil.upload(file.getBytes(), file.getOriginalFilename());
//            生成随机文件名
        } catch (IOException e) {
            log.error("上传失败", e);
            return Result.error(MessageConstant.UPLOAD_FAIL);
        } catch (ClientException e) {
            log.error("上传失败", e);
            throw new RuntimeException(e);
        }
        return Result.success(upload);
    }
}
