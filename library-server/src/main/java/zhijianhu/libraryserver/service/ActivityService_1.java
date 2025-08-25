package zhijianhu.libraryserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zhijianhu.entity.Activity;
import zhijianhu.vo.ActivityVO;

import java.util.List;

/**
* @author windows
* @description 针对表【activity(活动记录表)】的数据库操作Service
* @createDate 2025-03-08 16:56:28
*/
public interface ActivityService extends IService<Activity> {

    List<ActivityVO> getRecentActivities(Integer limit);
}
