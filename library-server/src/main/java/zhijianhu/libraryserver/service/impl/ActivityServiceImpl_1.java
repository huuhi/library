package zhijianhu.libraryserver.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhijianhu.entity.Activity;
import zhijianhu.libraryserver.mapper.ActivityMapper;
import zhijianhu.libraryserver.service.ActivityService;
import zhijianhu.vo.ActivityVO;

import java.util.List;

/**
* @author windows
* @description 针对表【activity(活动记录表)】的数据库操作Service实现
* @createDate 2025-03-08 16:56:28
*/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
    implements ActivityService {
    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    @Override
    public List<ActivityVO> getRecentActivities(Integer limit) {
        return activityMapper.limit(limit);
    }
}




