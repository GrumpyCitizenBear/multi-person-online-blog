package hello.entity;

import java.util.List;

public class BlogListResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;

    public BlogListResult(ResultStatus status, String msg,int total, int page, int totalPage, List<Blog> data) {
        super(status, msg, data);
        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public static BlogListResult success(List<Blog> data, int total, int page, int totalPage) {
        return new BlogListResult(ResultStatus.OK, "获取成功", total, page, totalPage,data);
    }

    public static BlogListResult failure(String msg) {
        return new BlogListResult(ResultStatus.FAIL, msg, 0, 0, 0,null);
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
