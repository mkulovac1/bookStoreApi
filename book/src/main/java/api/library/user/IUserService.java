package api.library.user;

import api.library.registration.RegistrationRequest;
import api.library.registration.token.VerificationToken;
import api.library.user.password.ResetPassword;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User user, String verificationToken);

    String validateToken(String theToken);

    VerificationToken generateNewVerificationToken(String oldToken);

    User add(User user);

    User getUser(String email);

    void delete(String email);

    User update(User user);

    User activate(User user);

    User deactivate(User user);

    String updatePassword(ResetPassword resetPassword);
}
