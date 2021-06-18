package com.challenge.blog.config;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    //recuperar correo del token jwt
    public String getMailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getIdFromToken(String token){
        return getClaimFromToken(token, Claims::getId);
    }

    //recuperar la fecha de vencimiento del token jwt
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //para recuperar cualquier información del token, necesitaremos la clave secreta
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //comprobar si el token ha caducado
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //Generar token para el usuario
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //al crear el token -
    // 1. Definir reclamos del token, como Emisor, Vencimiento, Asunto e ID
    // 2. Firme el JWT utilizando el algoritmo HS512 y la clave secreta.
    // 3. De acuerdo con la serialización compacta de JWS (https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compactación del JWT a una cadena segura para URL
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validar token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String mail = getMailFromToken(token);
        return (mail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //Decodificar token
    public void decodeToken(String token){
        Base64.Decoder decoder = Base64.getDecoder();
        String[] tokenAr = token.split("\\.");
        String header = new String(decoder.decode(tokenAr[0]));
        String payload = new String(decoder.decode(tokenAr[1]));
        System.out.println(header);
        System.out.println(payload);
    }
}
