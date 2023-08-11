package api.library.security.jwt;

import api.library.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final UserRepository userRepository;

    private static final String secretKey = "C2BD4C82D3DBD80C3CE3E43C23294B142DFAF3A28827BFE311D722C3A423FFEE4CBB3E4466901C3B4F7A0AB58283EDA5A3BFFDE51439D63405B14CDF772342FE"; // wRa=tltriSWa#rlsethIfU=eZuprePRaThO*p5TowlVukUMi9lthAwr*-lD*$fab

    public String getGeneratedToken(String userName) {
        Map<String, Objects> claims = new HashMap<>(); // sve ono sto se treba znati o useru, json objekat
        return generateTokenForUser(claims, userName);
    }

    private String generateTokenForUser(Map<String, Objects> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
}

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationTimeFromToken(token).before(new Date());
    }

    private Date extractExpirationTimeFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String tokenUserName = extractUserNameFromToken(token);
        return (tokenUserName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
