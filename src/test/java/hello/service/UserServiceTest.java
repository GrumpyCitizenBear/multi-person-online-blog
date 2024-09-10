package hello.service;

import hello.entity.User;
import hello.dao.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockBCryptPasswordEncoder;
    @Mock
    UserMapper mockUserMapper;
    @InjectMocks
    UserService userService;

    @Test
    public void testSave(){
        //调用userservice，验证userservice将请求转发给了usermapper
        when(mockBCryptPasswordEncoder.encode("mypassword")).thenReturn("myencodedpassword");
        userService.save("myuser","mypassword");
        verify(mockUserMapper).save("myuser","myencodedpassword");
    }

    @Test
    public void testGetUserByUsername(){
        userService.getUserByUsername("myuser");
        verify(mockUserMapper).findUserByUsername("myuser");
    }

    @Test
    public void throwExceptionWhenUserNotFound(){
        //Mockito.when(mockUserMapper.findUserByUsername("myUserName")).thenReturn(null);
        //测试抛出异常
        Assertions.assertThrows(UsernameNotFoundException.class,
                ()->userService.loadUserByUsername("myUserName"));
    }

    @Test
    public void returnUserDetailsWhenUserFound(){
        when(mockUserMapper.findUserByUsername("myUserName")).thenReturn(new User("myUserName","myEncodedPassword"));
        UserDetails userDetails = userService.loadUserByUsername("myUserName");
        Assertions.assertEquals("myUserName",userDetails.getUsername());
        Assertions.assertEquals("myEncodedPassword",userDetails.getPassword());
    }

}