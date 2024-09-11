package hello.entity.Result;

public class Result{
    public enum ResultStatus {
        OK("ok"),
        FAIL("fail");
        private String status;
        ResultStatus(String status) {
            this.status = status;
        }
    }

    ResultStatus status;
    protected Result(ResultStatus status) {
        this.status = status;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

}
