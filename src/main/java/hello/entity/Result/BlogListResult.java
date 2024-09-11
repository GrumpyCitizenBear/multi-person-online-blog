package hello.entity.Result;

import hello.entity.Blogs;

import java.util.List;

public class BlogListResult extends MsgResult {
    private final List<Blogs> data;
    private int total;
    private int page;
    private int totalPage;

    public BlogListResult(String status, String msg,int total, int page, int totalPage, List<Blogs> data) {
        super(status,msg);
        this.data = data;
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public static BlogListResult success(List<Blogs> data, int total, int page, int totalPage) {
        return new BlogListResult("ok", "获取成功", total, page, totalPage,data);
    }

    public List<Blogs> getData() {
        return data;
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
