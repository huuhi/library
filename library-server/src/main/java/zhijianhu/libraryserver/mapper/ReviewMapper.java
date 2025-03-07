package zhijianhu.libraryserver.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zhijianhu.entity.Review;

/**
* @author windows
* @description 针对表【review(图书评论表)】的数据库操作Mapper
* @createDate 2025-03-05 18:03:24
* @Entity zhijianhu.libraryserver.Review
*/
public interface ReviewMapper extends BaseMapper<Review> {
    @Delete("delete from review where id=#{id}")
    Boolean deleteReviewById(Integer id);

    @Select("select * from review where id=#{id}")
    Review getReviewById(Integer id);

    @Update("update review set is_audit=#{isAudit},description=#{description} where id=#{id}")
    Boolean updateReview(Review review);

    Page<Review> getReviewByPage(@Param("isAudit") Integer isAudit, Page<Review> page);

}




