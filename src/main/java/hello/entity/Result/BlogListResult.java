package hello.entity.Result;

import hello.entity.Blogs;

import java.util.List;

public class BlogListResult extends MsgResult {
    private final List<Blogs> blogs;
    private int total;
    private int page;
    private int totalPage;

    public BlogListResult(ResultStatus status, String msg,int total, int page, int totalPage, List<Blogs> blogs) {
        super(status,msg);
        this.blogs = blogs;
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public static BlogListResult success(List<Blogs> data, int total, int page, int totalPage) {
        return new BlogListResult(ResultStatus.OK, "获取成功", total, page, totalPage,data);
    }

    public List<Blogs> getBlogs() {
        return blogs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
