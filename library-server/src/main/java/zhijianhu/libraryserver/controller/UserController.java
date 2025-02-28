package zhijianhu.libraryserver.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.JwtClaimsConstant;
import zhijianhu.dto.UserChangePasswordDTO;
import zhijianhu.dto.UserDTO;
import zhijianhu.dto.UserLoginDTO;
import zhijianhu.dto.UserPageQueryDTO;
import zhijianhu.properties.JwtProperties;
import zhijianhu.result.Result;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.utils.JwtUtil;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserLoginVO;
import zhijianhu.entity.Users;
import zhijianhu.vo.UserPageVO;

import java.util.HashMap;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/2/24
 * 说明:
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtProperties JwtProperties;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        Users user=usersService.login(userLoginDTO);
        String image = user.getImage();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                JwtProperties.getUserTtl(),
                claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .name(user.getName())
                .token(token)
                .image(image)
                .build();
        return Result.success(userLoginVO);
    }

//   用户注册
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        log.info("用户注册：{}", userDTO);
        boolean result = usersService.register(userDTO);
        return result?Result.success():Result.error("注册失败");
    }
//    修改用户信息
    @PutMapping
    public Result update(@RequestBody UserDTO userDTO){
        log.info("修改用户信息：{}", userDTO);
        Users users = BeanUtil.copyProperties(userDTO, Users.class);
        boolean b = usersService.updateById(users);
        return b?Result.success():Result.error("修改失败");
    }
//   修改状态
    @PutMapping("/status/{status}")
    public Result updateStatus(@PathVariable("status") Integer status,
                               @RequestParam("id") Integer id,
                               @RequestParam("violationReason") String violationReason){
        log.info("修改用户状态：{}", status);
        Users user = usersService.getById(id);
        user.setStatus(status);
        user.setViolationReason(violationReason);
        boolean b = usersService.updateById(user);
        return b?Result.success():Result.error("修改失败");
    }
//    修改密码
    @PutMapping("/password")
    public Result updatePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO){
        log.info("修改用户密码：{}",userChangePasswordDTO.getId());
        Boolean b= usersService.updatePassword(userChangePasswordDTO);
        return b?Result.success():Result.error("修改失败");
    }
//    分页查询
    @GetMapping("/list")
    public Result<PageVO<UserPageVO>> list(UserPageQueryDTO userPageQueryDTO){
        log.info("分页查询用户列表{}",userPageQueryDTO);
        PageVO<UserPageVO> pageVO = usersService.querybyPage(userPageQueryDTO);
        return Result.success(pageVO);

    }

//    根据id查询
}
