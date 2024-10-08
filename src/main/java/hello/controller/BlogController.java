package hello.controller;

import hello.entity.*;
import hello.entity.Result.MsgResult;
import hello.service.AuthService;
import hello.service.BlogService;
import hello.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class BlogController {
    private final BlogService blogService;
    private final AuthService authService;

    @Inject
    public BlogController(BlogService blogService,AuthService authService) {
        this.blogService = blogService;
        this.authService = authService;
    }

    @GetMapping("/blog")
    @ResponseBody
    public MsgResult getBlogs(@RequestParam("page") Integer page, @RequestParam(value="userId",required = false) Integer userId){
        if(page==null||page<0){
            page=1;
        }

        return blogService.getBlogs(page,10,userId);
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public MsgResult getBlog(@PathVariable("blogId") int blogId){
        return blogService.getBlogById(blogId);
    }

    @PostMapping("/blog")
    @ResponseBody
    public MsgResult newBlog(@RequestBody Map<String,String> param){
        try{
            return authService.getCurrentBlogUser().map(user -> blogService.insertBlog(fromParam(param,user))).orElse(MsgResult.failure("登录后才能操作"));
        }catch (Exception e){
            return MsgResult.failure(e);
        }
    }

    @PatchMapping("/blog/{blogId}")
    @ResponseBody
    public MsgResult updateBlog(@PathVariable("blogId") int blogId,@RequestBody Map<String,String> param){
        try{
            return authService.getCurrentBlogUser().map(user -> blogService.updateBlog(blogId, fromParam(param,user))).orElse(MsgResult.failure("登录后才能操作"));
        }catch (Exception e){
            return MsgResult.failure(e);
        }

    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public MsgResult deleteBlog(@PathVariable("blogId") int blogId){
        try{
            return authService.getCurrentUser().map(user -> blogService.deleteBlog(blogId,user)).orElse(MsgResult.failure("登录后才能操作"));
        }catch (Exception e){
            return MsgResult.failure(e);
        }

    }

    private Blog fromParam(Map<String,String> params, BlogUser user) {
        Blog blog = new Blog();
        String title = params.get("title");
        String content = params.get("content");
        String description = params.get("description");

        AssertUtils.assertTrue(StringUtils.isNotBlank(title) && title.length() < 100,"title is invalid!");
        AssertUtils.assertTrue(StringUtils.isNotBlank(content) && content.length() < 10000,"content is invalid!");

        if(StringUtils.isBlank(description)){
            description = content.substring(0,Math.min(content.length(),10))+"......";
        }

        blog.setTitle(title);
        blog.setDescription(description);
        blog.setContent(content);
        blog.setUser(user);
        return blog;

    }

}
