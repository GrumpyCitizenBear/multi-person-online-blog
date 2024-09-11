package hello.entity.Result;

import hello.entity.User;

public class DataMsgResult extends MsgResult{
    User data;
    protected DataMsgResult(ResultStatus status, String msg,User data) {
        super(status, msg);
        this.data = data;
    }

    public static DataMsgResult success(String msg, User user) {
        return new DataMsgResult(ResultStatus.OK,msg,user);
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
