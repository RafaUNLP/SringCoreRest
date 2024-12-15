package com.example.demo.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public String generarToken(String email, int segundos) {
        Date expiracion = getExpiration(new Date(), segundos);
        return Jwts.builder().setSubject(email).signWith(key).setExpiration(expiracion).compact();
    }

    public boolean validateToken(String token) {
        String prefijo = "Bearer";
        try {
            if (token.startsWith(prefijo)) //saco el "Bearer" del inicio del token
                token = token.substring(prefijo.length()).trim();
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) //aca es donde se chequea la expiración y se tira la ExpiredJwtException
                    .getBody();

            return true;
        } catch (ExpiredJwtException exp) {
            System.out.println("El jwt es valido pero expiró");
            return false;
        } catch (JwtException e) {
            System.out.println("Error con el JWT: " + e.getMessage());
            return false;
        }
    }
    
    private Date getExpiration(Date date, int segundos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, segundos);

        return calendar.getTime();
    }
}