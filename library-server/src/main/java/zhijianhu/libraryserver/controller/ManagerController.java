package zhijianhu.libraryserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zhijianhu.entity.UserContext;
import zhijianhu.libraryserver.service.ActivityService;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.result.Result;
import zhijianhu.vo.*;

import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/3/8
 * 说明:
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class ManagerController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ActivityService activityService;

    @GetMapping("/info")
    public Result<AdminInfoVO> getAdminInfo() {
        Integer adminId = UserContext.getUserId();
        AdminInfoVO adminInfoVO = usersService.getAdminInfo(adminId);
        return Result.success(adminInfoVO);
    }
    @GetMapping("/statistics")
    public Result<AdminStatisticsVO> getAdminStatistics() {
        // 从当前登录用户获取管理员ID
        Integer adminId = UserContext.getUserId();
        AdminStatisticsVO statistics = usersService.getAdminStatistics(adminId);
        return Result.success(statistics);
    }
    @GetMapping("/borrowing-trends")
    public Result<BorrowingTrendsVO> getBorrowingTrends(@RequestParam(defaultValue = "6") Integer months) {
        BorrowingTrendsVO trendsData = usersService.getBorrowingTrends(months);
        return Result.success(trendsData);
    }
    @GetMapping("/book-categories")
    public Result<List<BookCategoryStatVO>> getBookCategoryStats() {
        List<BookCategoryStatVO> categoryStats = usersService.getBookCategoryStats();
        return Result.success(categoryStats);
    }
    @GetMapping("/activity/recent")
    public Result<List<ActivityVO>> getRecentActivities(@RequestParam(defaultValue = "5") Integer limit) {
        List<ActivityVO> recentActivities = activityService.getRecentActivities(limit);
        return Result.success(recentActivities);
    }

}
