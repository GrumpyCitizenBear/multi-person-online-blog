package hello.entity.Result;

import hello.entity.Blog;

public class BlogMsgResult extends MsgResult{
    Blog blog;
    protected BlogMsgResult(String status, String msg,Blog blog) {
        super(status, msg);
        this.blog = blog;
    }

    public static BlogMsgResult success(String msg,Blog blog){
        return new BlogMsgResult("ok",msg,blog);
    }
    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
