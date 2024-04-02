package im.pickside.server.common.dto;

import lombok.Builder;

@Builder
public record JwtResponse(
        String grantType,
        String accessToken,
        String refreshToken,
        Long accessTokenExpiresIn
) {

}
