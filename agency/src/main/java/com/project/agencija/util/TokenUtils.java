package com.project.agencija.util;

import com.project.agencija.model.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TokenUtils {

    @Value("smart-home")
    private String APP_NAME;

    public String secret = "somesecret";

    private final int expiresIn = 1800000;

    private static final String AUDIENCE_WEB = "web";

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateToken(String username) {
        String appName = "SmartHome";
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setAudience(generateAudience())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secret).compact();
    }

    public String generateToken(Client user, String fingerprint) {
        // moguce je postavljanje proizvoljnih podataka u telo JWT tokena pozivom funkcije .claim("key", value), npr. .claim("role", user.getRole())

        // Kreiranje tokena sa fingerprint-om
        String fingerprintHash = generateFingerprintHash(fingerprint);
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(user.getUsername())
                .setAudience(generateAudience())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .claim("userFingerprint", fingerprintHash)
                .claim("role", user.getRole())
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    private String generateAudience() {
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn);
    }

    public String getToken(HttpServletRequest request) {
        String authorizationHeader = getAuthHeaderFromHeader(request);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    public String getUsernameFromToken(String token){
        String username = null;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if(claims != null)
                username = claims.getSubject();
        } catch (ExpiredJwtException | NullPointerException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }

        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt = null;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if(claims != null)
                issueAt = claims.getIssuedAt();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience = null;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if(claims != null)
                audience = claims.getAudience();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }
        return audience;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration = null;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            if(claims != null)
                expiration = claims.getExpiration();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            return null;
        }

        return expiration;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }


    public Boolean validateToken(String token, UserDetails userDetails, String fingerprint) {
        Client user = (Client) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

        // Token je validan kada:
        boolean isUsernameValid = username != null // korisnicko ime nije null
                && username.equals(userDetails.getUsername()); // korisnicko ime iz tokena se podudara sa korisnickom imenom koje pise u bazi


        // Validiranje fingerprint-a
        System.out.println("FGP ===> " + fingerprint);
        boolean isFingerprintValid = false;
        boolean isAlgorithmValid = false;
        if (fingerprint != null) {
            isFingerprintValid = validateTokenFingerprint(fingerprint, token);
            isAlgorithmValid = SIGNATURE_ALGORITHM.getValue().equals(getAlgorithmFromToken(token));
        }
        return isUsernameValid && isFingerprintValid && isAlgorithmValid;
    }

    private String getAlgorithmFromToken(String token) {
        String algorithm;
        try {
            algorithm = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getHeader()
                    .getAlgorithm();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            algorithm = null;
        }
        return algorithm;
    }

    private boolean validateTokenFingerprint(String fingerprint, String token) {
        // Hesiranje fingerprint-a radi poređenja sa hesiranim fingerprint-om u tokenu
        String fingerprintHash = generateFingerprintHash(fingerprint);
        String fingerprintFromToken = getFingerprintFromToken(token);
        return fingerprintFromToken.equals(fingerprintHash);
    }

    private String getFingerprintFromToken(String token) {
        String fingerprint;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            fingerprint = claims.get("userFingerprint", String.class);
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            fingerprint = null;
        }
        return fingerprint;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public int getExpiredIn() {
        return expiresIn;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        String authHeader = "Authorization";
        return request.getHeader(authHeader);
    }

    public String generateFingerprint() {
        // Generisanje random string-a koji ce predstavljati fingerprint za korisnika
        byte[] randomFgp = new byte[50];
        this.secureRandom.nextBytes(randomFgp);
        return DatatypeConverter.printHexBinary(randomFgp);
    }

    private String generateFingerprintHash(String userFingerprint) {
        // Generisanje hash-a za fingerprint koji stavljamo u token (sprecavamo XSS da procita fingerprint i sam postavi ocekivani cookie)
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(userFingerprintDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getFingerprintFromCookie(HttpServletRequest request) {
        String userFingerprint = null;
        if (request.getCookies() != null && request.getCookies().length > 0) {
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> "Fingerprint".equals(c.getName())).findFirst();

            if (cookie.isPresent()) {
                userFingerprint = cookie.get().getValue();
            }
        }
        return userFingerprint;
    }


}
