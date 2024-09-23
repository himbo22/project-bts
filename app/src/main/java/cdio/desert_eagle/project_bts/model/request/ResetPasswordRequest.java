package cdio.desert_eagle.project_bts.model.request;

public class ResetPasswordRequest {
    private String password;
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public ResetPasswordRequest(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
