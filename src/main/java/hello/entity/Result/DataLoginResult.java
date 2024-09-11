package hello.entity.Result;

import hello.entity.User;

public class DataLoginResult extends LoginResult{
    User data;
    protected DataLoginResult(String status, boolean isLogin,User data) {
        super(status, isLogin);
        this.data = data;
    }

    public static DataLoginResult success(User data,boolean isLogin){
        return new DataLoginResult("ok", isLogin, data);
    }
    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
