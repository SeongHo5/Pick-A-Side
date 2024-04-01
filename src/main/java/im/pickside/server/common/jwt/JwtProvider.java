package im.pickside.server.common.jwt;

import im.pickside.server.common.dto.JwtResponse;
import im.pickside.server.common.service.RedisService;
import im.pickside.server.exception.AuthException;
import im.pickside.server.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

import static im.pickside.server.common.constant.AuthConstant.*;
import static im.pickside.server.exception.ExceptionStatus.BLACKLISTED_TOKEN;
import static im.pickside.server.exception.ExceptionStatus.INVALID_AUTH_ERROR;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Setter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final RedisService redisService;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    private Key key;
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // secretKey를 Base64로 인코딩
    @PostConstruct
    private void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * 토큰을 검증하기 위해 Request Header에서 "Bearer "를 제거한다.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * 토큰을 발급한다.
     *
     * @param email 사용자 이메일
     * @return 토큰 발급 결과(액세스 토큰, 리프레시 토큰, 만료 시간)
     */
    public JwtResponse createJwtToken(String email) {
        Authentication authentication = createAuthentication(email);
        String accessToken = createAccessToken(authentication);
        String refreshToken = createRefreshToken();
        return JwtResponse.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(ACCESS_TOKEN_EXPIRE_TIME.toMillis())
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(Authentication authentication) {
        Date accessTokenExpiresIn = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME.toMillis());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORIZATION_KEY, extractAuthorities(authentication))
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken() {
        Date refreshTokenExpiresIn = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME.toMillis());

        return Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    /**
     * 토큰의 유효성을 검증하고, 상황에 따라 예외를 발생시킨다.
     *
     * @throws AuthException           무효화된 토큰을 사용할 경우
     * @throws SecurityException       유효하지 않은 토큰을 사용할 경우
     * @throws UnsupportedJwtException 지원되지 않는 토큰을 사용할 경우
     * @throws ExpiredJwtException     만료된 토큰을 사용할 경우
     */
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            if (redisService.hasKey(BLACKLISTED_KEY_PREFIX + token)) {
                throw new AuthException(BLACKLISTED_TOKEN);
            }
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            throw new AuthException(INVALID_AUTH_ERROR);
        }
    }

    /**
     * 토큰을 parsing하여 사용자 정보를 반환한다.
     *
     * @param token 액세스 토큰
     * @return 사용자 정보
     * @throws ExpiredJwtException 만료된 토큰을 사용할 경우
     */
    public Claims getInfoFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * 사용자에 대한 인증 정보를 담은 Authentication 객체를 생성한다.
     *
     * @param email 사용자 이메일
     */
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // =============== PRIVATE METHODS =============== //
    private String extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

}
