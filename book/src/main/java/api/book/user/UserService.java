package api.book.user;

import api.book.exception.UserAlreadyExistsException;
import api.book.registration.RegistrationRequest;
import api.book.registration.token.VerificationToken;
import api.book.registration.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if(user.isPresent()) {
            throw new UserAlreadyExistsException("User with e-mail " + request.email() + " already exists");
        }
        var newUser = new User();
        newUser.setFirstName(request.firstName()); // kod record-a se ne mora korisiti getter vec samo .imeAtributa()
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User user, String verificationToken) {
        var token = new VerificationToken(verificationToken, user);
        verificationTokenRepository.save(token);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = verificationTokenRepository.findByToken(theToken);
        if(token == null) {
            return "Invalid verification token. Please register again!";
        }

        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();

        if((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            // verificationTokenRepository.delete(token);
            return "Verification token has expired. Please register again!";
        }

        user.setEnabled(true);
        userRepository.save(user); // moraju se sacuvati promjene u bazi

        return "ok";
    }
    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        var tokenExpirationTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());
        log.info("IZMIJENI TOKEN I HOCE DA GA SACUVA");
        return verificationTokenRepository.save(verificationToken);
    }
}
