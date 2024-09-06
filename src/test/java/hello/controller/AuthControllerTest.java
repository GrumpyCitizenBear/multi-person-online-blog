package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //调用测试前执行
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authenticationManager, userService)).build();
    }

    @Test
    void returnNotLoginByDefault() throws Exception {
        mockMvc.perform(get("/auth")).andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("User not logged in")));
    }

    @Test
    void testLogin() throws Exception {
        //未登录时，auth/接口返回未登录状态
        mockMvc.perform(get("/auth")).andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("User not logged in")));
        //使用/auth/login登录
        Map<String,String> usernamePassword = new HashMap<>();
        usernamePassword.put("username","MyUser");
        usernamePassword.put("password","MyPassword");

        Mockito.when(userService.loadUserByUsername("MyUser")).thenReturn(new User("MyUser",bCryptPasswordEncoder.encode("MyPassword"), Collections.emptyList()));
        new ObjectMapper().writeValueAsString(usernamePassword);

        MvcResult response = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(usernamePassword)))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("login success")))
                .andReturn();
        HttpSession httpSession = response.getRequest().getSession();
    }
}