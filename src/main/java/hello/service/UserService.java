package hello.service;

import hello.entity.BlogUser;
import hello.entity.User;
import hello.dao.UserMapper;
import hello.entity.UserWithoutTime;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public void save(String username, String password){
        userMapper.save(username,bCryptPasswordEncoder.encode(password));
    }
    public User getUserByUsername(String username){
        return userMapper.findUserByUsername(username);
    }

    public BlogUser getUserByBlogUsername(String username){
        return (BlogUser) userMapper.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username+"不存在！");
        }

        return new org.springframework.security.core.userdetails.User(
                username,user.getEncryptedPassword(), Collections.emptyList());
    }
}
