package hello.controller;

import hello.entity.Result;
import hello.entity.User;
import hello.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@RestController
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    @Inject
    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth(){
        //String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = userService.getUserByUsername(authentication == null ? null : authentication.getName());
        if(loggedInUser == null){
            return Result.failure("User not logged in");
        }else{
            return Result.success(null,loggedInUser);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword){
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if(username==null || password == null){
            return Result.failure("username/password ==null");
        }
        if(username.isEmpty() || username.length()>15){
            return Result.failure("invalid username");
        }
        if(password.isEmpty() || password.length()>15){
            return Result.failure("invalid password");
        }
        try{
            userService.save(username,password);
        }catch (DuplicateKeyException e){
            return Result.failure("user already exists!");
        }
        return Result.success("success!",null);
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> usernameAndPassword){
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();

        UserDetails userDetails = null;
        try {
            userDetails = userService.loadUserByUsername(username);
        }catch (UsernameNotFoundException e){
            return Result.failure("用户不存在");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
        try{
            authenticationManager.authenticate(token);
            //把用户信息保存在一个地方
            //cookie
            SecurityContextHolder.getContext().setAuthentication(token);

            return Result.success("login success", userService.getUserByUsername(username));
        }catch (BadCredentialsException e){
            return Result.failure("密码不正确");
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUsername(userName);
        if(loggedInUser == null){
            return Result.failure("User not logged in");
        }else{
            SecurityContextHolder.clearContext();
            return Result.success("注销成功",null);
        }
    }

}
