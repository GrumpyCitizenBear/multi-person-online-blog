package hello.entity;

public class Result{

    String status;
    String msg;
    boolean isLogin;
    Object data;

    public static Result failure(String msg){
        return new Result("fail",msg,false);
    }

    public static Result success(String msg, User user){
        return new Result("ok",msg,true,user);
    }

    private Result(String status, String msg, boolean isLogin) {
        this(status,msg,isLogin,null);
    }
    private Result(String status, String msg, boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
