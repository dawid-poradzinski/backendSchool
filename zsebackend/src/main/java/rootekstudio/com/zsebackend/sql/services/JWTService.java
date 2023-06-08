package rootekstudio.com.zsebackend.sql.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algoritmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;
    private static final String USERNAME_KEY = "USERNAME";
    private static final String USERNAME_ROLE = "ROLE";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC512(algoritmKey);
    }

    public String generateJWT(rootekstudio.com.zsebackend.sql.models.User user) {
        return JWT.create()
        .withClaim(USERNAME_KEY, user.getUsername())
        .withClaim(USERNAME_ROLE, user.getRank().toString())
        .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
        .withIssuer(issuer)
        .sign(algorithm);
    }

    public String getUsername(String token) {
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return jwt.getClaim(USERNAME_KEY).asString();
    }
}
