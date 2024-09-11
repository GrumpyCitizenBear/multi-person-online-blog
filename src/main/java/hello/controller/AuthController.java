package hello.controller;

import hello.entity.Result.DataLoginResult;
import hello.entity.Result.DataMsgResult;
import hello.entity.Result.LoginResult;
import hello.entity.Result.MsgResult;
import hello.entity.Result.Result;
import hello.entity.User;
import hello.service.AuthService;
import hello.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    @Inject
    public AuthController(AuthenticationManager authenticationManager, UserService userService,AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Result auth(){
//        return authService.getCurrentUser().map(user -> DataLoginResult.success(user, true)).orElse(LoginResult.success(false));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = userService.getUserByUsername(authentication == null ? null : authentication.getName());
        if(loggedInUser == null){
            return LoginResult.success(false);
        }else{
            return DataLoginResult.success(loggedInUser,true);
        }

    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, Object> usernameAndPassword, HttpServletRequest request){
        String username = (String) usernameAndPassword.get("username");
        String password = (String) usernameAndPassword.get("password");
        if(username==null || password == null){
            return MsgResult.failure("username/password ==null");
        }
        if(username.isEmpty() || username.length()>15){
            return MsgResult.failure("invalid username");
        }
        if(password.isEmpty() || password.length()>15){
            return MsgResult.failure("invalid password");
        }
        try{
            userService.save(username,password);
        }catch (DuplicateKeyException e){
            return MsgResult.failure("user already exists!");
        }
        login(usernameAndPassword,request);
        return DataMsgResult.success("success!",userService.getUserByUsername(username));
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Object login(@RequestBody Map<String, Object> usernameAndPassword,HttpServletRequest request){
        if(request.getHeader("User-Agent")==null || !request.getHeader("User-Agent").contains("Mozilla")){
            return "本站禁止爬虫";
        }
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password, Collections.emptyList());
        try{
            authenticationManager.authenticate(token);
            //把用户信息保存在一个地方
            //cookie
            SecurityContextHolder.getContext().setAuthentication(token);

            return DataMsgResult.success("登录成功", userService.getUserByUsername(username));
        }catch (BadCredentialsException e){
            return MsgResult.failure("密码不正确");
        }catch (UsernameNotFoundException e){
            return MsgResult.failure("用户不存在");
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public MsgResult logout(){
        MsgResult ret = authService.getCurrentUser()
                .map(user -> MsgResult.success("success"))
                .orElse(MsgResult.failure("用户没有登录"));
        SecurityContextHolder.clearContext();
        return ret;
    }

}
