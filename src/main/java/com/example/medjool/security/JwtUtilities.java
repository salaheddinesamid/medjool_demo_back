package com.example.medjool.security;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.function.Function;
import java.util.logging.Logger;


@Service
public class JwtUtilities {

    private static String SECRET_KEY = "385e7e7bf9074b975ccfb147a035696893be210c823250bf824cf61538176eda";
    private static Long expiration = 36000000L;
    Logger logger = Logger.getLogger(JwtUtilities.class.getName());

    // Generate a token:
    public String generateToken(String email, String role){
        return Jwts
                .builder()
                .setSubject(email)
                .claim("role" , role)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getToken(HttpServletRequest httpServletRequest){
        final String header = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("bearer")){
            return header.substring(7);
        }
        return null;
    }


    // Validate token:

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }
        catch (MalformedJwtException e) {
            logger.info("Invalid JWT token.");
            //logger.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            logger.info("Expired JWT token.");
            //logger.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT token compact of handler are invalid.");
            //log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
