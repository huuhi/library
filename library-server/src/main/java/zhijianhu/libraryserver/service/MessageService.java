package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.entity.Message;
import zhijianhu.vo.MessageVO;

import java.util.List;

/**
* @author windows
* @description 针对表【message(消息表)】的数据库操作Service
* @createDate 2025-03-10 16:46:48
*/
public interface MessageService extends IService<Message> {

    List<MessageVO> getLikeMessageByUserId(Integer userId);
}
