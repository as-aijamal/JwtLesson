package peaksoft.jwtlessontest.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import peaksoft.jwtlessontest.enitity.User;

import java.time.ZonedDateTime;

@Service
public class JwtService {


    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    //create jwt

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return JWT.create()
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(1).toInstant())
                .sign(algorithm);

    }

    //verify jwt

    public String verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier jwtVerifier=JWT.require(algorithm).build();
        DecodedJWT decodedJwt = jwtVerifier.verify(token);
       return decodedJwt.getClaim("email").asString();
    }


}
