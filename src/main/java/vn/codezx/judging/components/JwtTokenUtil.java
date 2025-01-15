package vn.codezx.judging.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import vn.codezx.judging.entity.User;

import java.security.InvalidParameterException;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretkey}")
    private String secretkey;


    public  String generateToken(User user){
        Map<String, String > claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("account", user.getAccount());
        try{
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getAccount())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (Exception e) {
            throw new InvalidParameterException("Cannot create jwt token, error: "+e.getMessage());
        }
    }
    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(bytes);
    }
//    private String  generateSecretKey(){
//        SecureRandom random =  new SecureRandom();
//        byte[] KeyBytes = new byte[32];
//        random.nextBytes(KeyBytes);
//        String secretKey = Encoders.BASE64.encode(KeyBytes);
//        return secretKey;
//    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public  <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims =  this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //Kiểm tra expiration
    public boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaims(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractAccount(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public boolean valiadteToken(String token, UserDetails userDetails){
        String account = extractAccount(token);
        return (account.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }
}
