package api.library.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository // ne mora se staviti jer je JpaRepository vec anotiran sa @Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
