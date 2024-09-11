package hello.service;

import hello.dao.BlogDao;
import hello.entity.*;
import hello.entity.Result.BlogListResult;
import hello.entity.Result.BlogMsgResult;
import hello.entity.Result.MsgResult;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Service
public class BlogService {
    private final BlogDao blogDao;
    @Inject
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public MsgResult getBlogs(Integer page, Integer pageSize, Integer userId){
        try {
            List<Blogs> blogs = blogDao.getBlogs(page, pageSize, userId);

            int count = blogDao.count(userId);

            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

            return BlogListResult.success(blogs, count, page, pageCount);
        }catch (Exception e){
            return MsgResult.failure(e);
        }
    }

    public MsgResult getBlogById(int blogId) {
        try{
            return BlogMsgResult.success("获取成功",blogDao.selectBlogById(blogId));
        }catch (Exception e){
            return MsgResult.failure(e);
        }
    }


    public MsgResult updateBlog(int blogId,Blog targetBlog) {
        Blog blogInDb = blogDao.selectBlogById(blogId);
        if(blogInDb == null){
            return MsgResult.failure("博客不存在");
        }
        if(!Objects.equals(blogId,blogInDb.getId())){
            return MsgResult.failure("不能修改别人的博客");
        }
        try{
            targetBlog.setId(blogId);
            return BlogMsgResult.success("修改成功",blogDao.updateBlog(targetBlog));
        }catch (Exception e){
            return MsgResult.failure(e);
        }
    }

    public MsgResult insertBlog(Blog newBlog) {
        try{
            return BlogMsgResult.success("创建成功",blogDao.insertBlog(newBlog));
        }catch (Exception e){
            return MsgResult.failure(e);
        }
    }

    public MsgResult deleteBlog(int blogId, User user) {
        Blog blogInDb = blogDao.selectBlogById(blogId);
        if(blogInDb == null){
            return MsgResult.failure("博客不存在");
        }
        if(!Objects.equals(user.getId(),blogInDb.getUserId())){
            return MsgResult.failure("不能修改别人的博客");
        }
        try{
            blogDao.deleteBlog(blogId);
            return MsgResult.success("删除成功");
        }catch (Exception e){
            return MsgResult.failure(e);
        }
    }
}
