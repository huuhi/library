package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.Message;
import zhijianhu.libraryserver.mapper.MessageMapper;
import zhijianhu.libraryserver.service.MessageService;

/**
* @author windows
* @description 针对表【message(消息表)】的数据库操作Service实现
* @createDate 2025-03-10 16:46:48
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService {

}




