package hello.entity.Result;

public class LoginResult extends Result {
    boolean islogin;
    protected LoginResult(String status,boolean isLogin) {
        super(status);
        this.islogin = isLogin;
    }

    public static LoginResult success(boolean isLogin) {
        return new LoginResult("ok",isLogin);
    }

    public boolean isIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }
}
