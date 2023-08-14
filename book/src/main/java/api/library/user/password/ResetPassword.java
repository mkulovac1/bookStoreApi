package api.library.user.password;

import lombok.Data;

@Data
public class ResetPassword {
    private String email;
    private String oldPassword;
    private String newPassword;
}
