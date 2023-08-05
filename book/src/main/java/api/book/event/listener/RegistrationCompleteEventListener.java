package api.book.event.listener;


import api.book.event.RegistrationCompleteEvent;
import api.book.user.User;
import api.book.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

        private final UserService userService;
        private final JavaMailSender mailSender;
        private User user;

        @Override
        public void onApplicationEvent(RegistrationCompleteEvent registrationCompleteEvent) {
            // User user = registrationCompleteEvent.getUser();
            user = registrationCompleteEvent.getUser();
            String verificationToken = UUID.randomUUID().toString();

            userService.saveUserVerificationToken(user, verificationToken);

            String url = registrationCompleteEvent.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;

            try {
                sendVerificationEmail(url);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            log.info("Verification URL: " + url);
        }

        public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
            String subject = "Email Verification";
            String senderName = "Service Verification";
            String content = "<p>Click the link below to verify your email address:</p>"
                    + "<p><a href=\"" + url + "\">Verify my email address</a></p>"
                    + "<br>"
                    + "<p>Ignore this email if you do remember registering your email address.</p>";
            MimeMessage message = mailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("micurug@gmail.com", senderName);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            mailSender.send(message);
        }
}
