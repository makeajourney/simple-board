package kr.makeajourney.board.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("jwt.secret")
    private String secret;

    public String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String generateToken(String username, Collection<GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role", authorities);

        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Set<GrantedAuthority> extractAuthorities(Claims claims) {
        List<Map<Object, String>> auth = (List<Map<Object, String>>) claims.get("role");
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (!auth.isEmpty()) {
            Map<Object, String> roleMap = auth.get(0);
            authorities.addAll(roleMap.values().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        }
        return authorities;
    }
}
