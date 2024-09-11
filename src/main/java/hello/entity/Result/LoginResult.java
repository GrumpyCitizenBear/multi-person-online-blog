package hello.entity.Result;

public class LoginResult extends Result {
    boolean isLogin;
    protected LoginResult(ResultStatus status,boolean isLogin) {
        super(status);
        this.isLogin = isLogin;
    }

    public static LoginResult success(boolean isLogin) {
        return new LoginResult(ResultStatus.OK,isLogin);
    }

    public boolean isLogin() {
        return isLogin;
    }
    public void setLogin(boolean islogin) {
        isLogin = islogin;
    }
}
