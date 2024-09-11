package hello.entity.Result;

import hello.entity.Result.Result;

public class MsgResult extends Result {
    String msg;
    protected MsgResult(String status, String msg) {
        super(status);
        this.msg = msg;
    }

    public static MsgResult failure(String msg) {
        return new MsgResult("fail",msg);
    }

    public static MsgResult failure(Exception e){
        return new MsgResult("fail",e.getMessage());
    }

    public static MsgResult success(String msg) {
        return new MsgResult("ok",msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
