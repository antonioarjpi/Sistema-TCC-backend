package com.estacio.tcc.config.security;

import com.estacio.tcc.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expiracao}")
    private String expiracao;

    @Value("${jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long exp = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp);
        Instant instant = dataHoraExpiracao.atZone( ZoneId.systemDefault() ).toInstant();
        java.util.Date data = Date.from(instant);

        String token = Jwts
                .builder()
                .setExpiration(data)
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("email", usuario.getEmail())
                .signWith( SignatureAlgorithm.HS512 , chaveAssinatura )
                .compact();

        return token;
    }

    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValido(String token) {
        try {
            Claims claims = getClaims(token);
            java.util.Date dataEx = claims.getExpiration();
            LocalDateTime dataExpiracao = dataEx.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            boolean dataHoraAtualIsAfterDataExpiracao = LocalDateTime.now().isAfter(dataExpiracao);
            return !dataHoraAtualIsAfterDataExpiracao;
        }catch(ExpiredJwtException e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }
}
