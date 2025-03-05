package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zhijianhu.entity.Review;
import zhijianhu.libraryserver.service.ReviewService;
import zhijianhu.libraryserver.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

/**
* @author windows
* @description 针对表【review(图书评论表)】的数据库操作Service实现
* @createDate 2025-03-05 18:03:24
*/
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
    implements ReviewService{

}




