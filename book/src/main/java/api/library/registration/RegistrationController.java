package api.library.registration;

import api.library.event.RegistrationCompleteEvent;
import api.library.event.listener.RegistrationCompleteEventListener;
import api.library.registration.token.VerificationToken;
import api.library.registration.token.VerificationTokenRepository;
import api.library.user.User;
import api.library.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenRepository verificationTokenRepository;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest servletRequest;
    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest request, final HttpServletRequest req) { // @RequestBody - JSON which we are sending from the frontend/Postman
        User user = userService.registerUser(request);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(req)));
        return "Success! Please, check your e-mail to confirm the registration.";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) { // requestparam is part of ?token=...
        String url = applicationUrl(servletRequest) + "/register/resendRegistrationToken?token=" + token;
        VerificationToken theToken = verificationTokenRepository.findByToken(token);

        if(theToken.getUser().isEnabled())
            return "Your account has already been verified. Please login.";

        String result = userService.validateToken(token);

        if(result.equalsIgnoreCase("ok")) {
            return "Your account has been verified. Please login.";
        }

        return "Invalid verification token. <a href=\"" + url + "\">Click here</a> to resend the verification link.";
    }

    @GetMapping("/resendRegistrationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest req) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken =  userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser, applicationUrl(req), verificationToken);

        return "A new verification link has been sent to your email, please, check to complete your registration";
    }

    private void resendVerificationTokenEmail(User newUser, String applicationUrl, VerificationToken token) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl + "/register/verifyEmail?token=" + token.getToken();
        eventListener.sendVerificationEmail(url);

        log.info("Click the link to verify your registration :  {}", url);
    }

    private String applicationUrl(HttpServletRequest req) {
        return "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
    }
}
