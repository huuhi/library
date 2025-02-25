package zhijianhu.libraryserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zhijianhu.entity.Books;
/**
* @author windows
* @description 针对表【books(图书信息表)】的数据库操作Mapper
* @createDate 2025-02-24 14:25:21
* @Entity generator.domain.Books
*/
@Mapper
public interface BooksMapper extends BaseMapper<Books> {

}




