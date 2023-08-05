package api.book.registration;

import api.book.event.RegistrationCompleteEvent;
import api.book.registration.token.VerificationToken;
import api.book.registration.token.VerificationTokenRepository;
import api.book.user.User;
import api.book.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest request, final HttpServletRequest req) { // @RequestBody - ovo je JSON koji se salje iz Postmana
        User user = userService.registerUser(request);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(req)));
        return "Success! Please, check your e-mail to confirm the registration.";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) { // requestparam je dio ?token=...
        VerificationToken theToken = verificationTokenRepository.findByToken(token);

        if(theToken.getUser().isEnabled())
            return "Your account has already been verified. Please login.";

        String result = userService.validateToken(token);
        if(result.equalsIgnoreCase("ok")) {
            return "Your account has been verified. Please login.";
        }

        return "Invalid verification token. Please register again!";
    }

    private String applicationUrl(HttpServletRequest req) {
        return "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
    }
}
