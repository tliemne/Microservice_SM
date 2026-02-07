package chien.nguyen.school.student.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Value("${jwt.secret}") private String secret;
  @Value("${jwt.issuer}") private String issuer;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
          throws ServletException, IOException {

    String auth = req.getHeader(HttpHeaders.AUTHORIZATION);
    System.out.println("AUTH HEADER = " + auth);

    if (auth != null && auth.startsWith("Bearer ")) {
      try {
        Claims c = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .requireIssuer(issuer)
                .build()
                .parseClaimsJws(auth.substring(7))
                .getBody();

        Object rawRoles = c.get("roles");
        Object rawPermissions = c.get("permissions");

        List<SimpleGrantedAuthority> auths = new ArrayList<>();

        if (rawRoles instanceof Collection<?> roles) {
          for (Object r : roles) {
            if (r instanceof String s) {
              auths.add(new SimpleGrantedAuthority("ROLE_" + s));
            }
          }
        }

        if (rawPermissions instanceof Collection<?> perms) {
          for (Object p : perms) {
            if (p instanceof String s) {
              auths.add(new SimpleGrantedAuthority(s));
            } else if (p instanceof Map<?, ?> m && m.get("code") != null) {
              auths.add(new SimpleGrantedAuthority(String.valueOf(m.get("code"))));
            }
          }
        }


        UsernamePasswordAuthenticationToken at =
                new UsernamePasswordAuthenticationToken(c.getSubject(), null, auths);

        Map<String,Object> details = new HashMap<>();
        details.put("username", c.get("username"));
        details.put("schoolId", c.get("schoolId"));
        details.put("studentId", c.get("studentId"));
        Object scope = c.get("data_scope");
        if (scope == null) {
          scope = c.get("dataScope");
        }

        details.put("dataScope", scope);

        at.setDetails(details);

        System.out.println("JWT CLAIMS = " + c);
        System.out.println("DETAILS = " + details);


        SecurityContextHolder.getContext().setAuthentication(at);

        System.out.println("AUTH SET = " + auths);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    chain.doFilter(req, res);
  }

}
