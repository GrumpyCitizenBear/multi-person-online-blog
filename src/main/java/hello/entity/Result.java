package hello.entity;

public abstract class Result<T>{
    public enum ResultStatus {
        OK("ok"),
        FAIL("fail");
        private String status;
        ResultStatus(String status) {
            this.status = status;
        }
    }

    ResultStatus status;
    String msg;
    T data;
    protected Result(ResultStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
