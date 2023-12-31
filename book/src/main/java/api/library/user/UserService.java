package api.library.user;

import api.library.exception.UserAlreadyExistsException;
import api.library.exception.UserNotFoundException;
import api.library.registration.RegistrationRequest;
import api.library.registration.token.VerificationToken;
import api.library.registration.token.VerificationTokenRepository;
import api.library.role.Role;
import api.library.role.RoleRepository;
import api.library.user.password.ResetPassword;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RoleRepository roleRepository;

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
        newUser.setFirstName(request.firstName()); // you don't need to use getter in records instead just attribte name => .nameOfAttribute()
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        Role role = roleRepository.findByName("USER").get();
        newUser.setRoles(Collections.singletonList(role));
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
        userRepository.save(user); // you need to save changes in databes

        return "ok";
    }
    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        var tokenExpirationTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public User add(User user) {
        Optional<User> checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser.isPresent()) {
            throw new UserAlreadyExistsException("User with e-mail " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // password neeeds to be encoded
        user.setEnabled(true); // admin activates user here
        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    @Transactional
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public User update(User user) {
        User userToUpdate = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User with email " + user.getEmail() + " not found"));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setRoles(user.getRoles());
        return userRepository.save(userToUpdate);
    }

    @Override
    public User activate(User user) {
        User userToActivate = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User with email " + user.getEmail() + " not found"));
        userToActivate.setEnabled(true);
        return userRepository.save(userToActivate);
    }

    @Override
    public User deactivate(User user) {
        User userToDeactivate = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User with email " + user.getEmail() + " not found"));
        userToDeactivate.setEnabled(false);
        return userRepository.save(userToDeactivate);
    }

    @Override
    public String updatePassword(ResetPassword resetPassword) {
        if(!validateOldPassword(resetPassword)) {
            return "Old password is not correct!";
        }
        User userToUpdate = userRepository.findByEmail(resetPassword.getEmail()).orElseThrow(() -> new UserNotFoundException("User with email " + resetPassword.getEmail() + " not found"));
        userToUpdate.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        userRepository.save(userToUpdate);
        return "Password is changed!";
    }

    public boolean validateOldPassword(ResetPassword resetPassword) {
        User userToValidate = userRepository.findByEmail(resetPassword.getEmail()).orElseThrow(() -> new UserNotFoundException("User with email " + resetPassword.getEmail() + " not found"));
        return passwordEncoder.matches(resetPassword.getOldPassword(), userToValidate.getPassword());
    }
}
