package im.pickside.server.common.dto;


import lombok.Builder;

@Builder
public record JwtReissueResponse(String accessToken, String refreshToken, Long accessTokenExpiresIn) {

}
