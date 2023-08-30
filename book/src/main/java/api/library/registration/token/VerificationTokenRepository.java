package api.library.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository // you don't need to annotate it with @Repository cause it already has @Repository in JpaRespository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
