package api.library.registration.token;

import api.library.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

 @Getter
 @Setter
 @Entity
 @NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    private static final int EXP_TIME = 1;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // foreign key



     public VerificationToken(String token, User user) {
         super();
         this.token = token;
         this.user = user;
         this.expirationTime = this.getTokenExpirationTime();
     }
     public VerificationToken(String token) {
         super();
         this.token = token;
         this.expirationTime = this.getTokenExpirationTime();
     }

     public Date getTokenExpirationTime() {
         /* Date now = new Date();
         long expirationTimeInMilliseconds = 1000 * 60 * 60 * 24; // 24 hours
         return new Date(now.getTime() + expirationTimeInMilliseconds); */
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(new Date().getTime());
         calendar.add(Calendar.MINUTE, EXP_TIME);
         return new Date(calendar.getTime().getTime());
     }
 }
