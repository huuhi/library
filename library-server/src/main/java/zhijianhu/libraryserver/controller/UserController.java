package zhijianhu.libraryserver.controller;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import zhijianhu.constant.JwtClaimsConstant;
import zhijianhu.dto.UserChangePasswordDTO;
import zhijianhu.dto.UserDTO;
import zhijianhu.dto.UserLoginDTO;
import zhijianhu.dto.UserPageQueryDTO;
import zhijianhu.entity.UserContext;
import zhijianhu.properties.JwtProperties;
import zhijianhu.result.Result;
import zhijianhu.libraryserver.service.UsersService;
import zhijianhu.utils.JwtUtil;
import zhijianhu.vo.PageVO;
import zhijianhu.vo.UserLoginVO;
import zhijianhu.entity.Users;
import zhijianhu.vo.UserNameAndIdVO;
import zhijianhu.vo.UserPageVO;

import java.util.HashMap;
import java.util.List;

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
        claims.put(JwtClaimsConstant.USERNAME,user.getUsername());
        claims.put(JwtClaimsConstant.USER_IMAGE,image);
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
    @CacheEvict(value = "userNameAndId" , key = "'all'")
    public Result<Void> register(@RequestBody UserDTO userDTO) {
        log.info("用户注册：{}", userDTO);
        boolean result = usersService.register(userDTO);
        return result?Result.success():Result.error("注册失败");
    }
//    修改用户信息
    @PutMapping
    @CacheEvict(value = "userNameAndId" , key = "'all'")
    public Result<Void> update(@RequestBody UserDTO userDTO){
        log.info("修改用户信息：{}", userDTO);
        Users users = BeanUtil.copyProperties(userDTO, Users.class);
        boolean b = usersService.updateById(users);
        return b?Result.success():Result.error("修改失败");
    }
//   修改状态
    @PutMapping("/status/{status}")
    public Result<Void> updateStatus(@PathVariable("status") Integer status,
                               @RequestParam("id") Integer id,
                               @RequestParam(value = "violationReason" , required = false) String violationReason){
        log.info("修改用户状态：{}", status);
        boolean b= usersService.updateStatus(status,id,violationReason);
        return b?Result.success():Result.error("修改失败");
    }
//    修改密码
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO){
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
    @GetMapping("/{id}")
    public Result<UserPageVO> getById(@PathVariable("id") Integer id){
        log.info("根据id查询用户{}",id);
        Users user = usersService.getById(id);
        UserPageVO userPageVO = BeanUtil.copyProperties(user, UserPageVO.class);
        return Result.success(userPageVO);
    }
//    获取所有用户名称和id
    @GetMapping("/all")
    @Cacheable(value = "userNameAndId" , key = "'all'")
    public Result<List<UserNameAndIdVO>> getAll(){
        log.info("获取所有用户名称和id");
        List<UserNameAndIdVO> userNameAndIdVOS = usersService.getAllUserNameAndId();
        return Result.success(userNameAndIdVOS);
    }
//    修改用户借书上限
    @PutMapping("/confine/{confine}")
    public Result<Void> updateConfine(@PathVariable("confine") Integer confine,
                               @RequestParam("id") Integer id){
        Boolean success= usersService.updateConfine(confine,id);
        return success?Result.success():Result.error("修改失败");
    }
//    用户退出登录应该删除用户id
    @PutMapping("/exit/{id}")
    public Result<Void> exit(@PathVariable("id") Integer id){
        log.info("用户退出登录：{}",id);
        UserContext.clear();//清除用户id
        return Result.success();
    }

}
