package zhijianhu.libraryserver.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import zhijianhu.entity.Activity;
import zhijianhu.vo.ActivityVO;

import java.util.List;

/**
* @author windows
* @description 针对表【activity(活动记录表)】的数据库操作Mapper
* @createDate 2025-03-08 16:56:28
* @Entity zhijianhu.libraryserver.Activity
*/
public interface ActivityMapper extends BaseMapper<Activity> {

    List<ActivityVO> limit(Integer limit);
}




