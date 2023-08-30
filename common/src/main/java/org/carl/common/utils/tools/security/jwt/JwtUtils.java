package org.carl.common.utils.tools.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.carl.common.utils.tools.security.jwt.enums.AuthData;
import org.carl.common.utils.tools.security.jwt.enums.AuthRole;

import java.util.Date;

/**
 * Written by Mr. Carl
 * @description: TODO jwt工具类
 * @version: 1.0
 */
public class JwtUtils {

    /**
     * @author carl
     * @description TODO 发行人名称
     */
    private static final String ISS = "Carl";

    /**
     * @author carl
     * @description TODO 盐值
     */
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("open-data");

    /**
     * @author carl
     * @description TODO 获取 AccessToken
     */
    public static String createAccessToken(String id, String role, String active, String applicationName) {
        return JWT.create()
                .withClaim(AuthData.ID.toString(), id)
                .withClaim(AuthData.ROLE.toString(), role)
                .withClaim(AuthData.ACTIVE.toString(), active)
                .withClaim(AuthData.APPLICATION.toString(), applicationName)
                .withIssuer(ISS)
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.addHours(new Date(), 2))
                .sign(ALGORITHM);
    }

    /**
     * @author carl
     * @description TODO 获取 RefreshToken
     */
    public static String createRefreshToken(String id, String active, String applicationName) {
        return JWT.create()
                .withClaim(AuthData.ID.toString(), id)
                .withClaim(AuthData.ACTIVE.toString(), active)
                .withClaim(AuthData.APPLICATION.toString(), applicationName)
                .withIssuer(ISS)
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.addDays(new Date(), 7))
                .sign(ALGORITHM);
    }

    /**
     * @author carl
     * @description TODO 直接获取载体（过期不会报错）
     */
    private static DecodedJWT getTokenBody(String token) {
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            throw new JWTDecodeException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * @author carl
     * @description TODO 获取ID
     */
    public static Long getId(String token) {
        Claim claim = getTokenBody(token).getClaim(AuthData.ID.toString());
        if (claim.isNull()) {
            throw new JWTDecodeException("访问凭证已失效，请重新登录：缺少 ID");
        }
        return Long.parseLong(claim.asString());
    }

    /**
     * @author carl
     * @description TODO 获取环境
     */
    public static String getActive(String token) {
        Claim claim = getTokenBody(token).getClaim(AuthData.ACTIVE.toString());
        if (claim.isNull()) {
            throw new JWTDecodeException("访问凭证已失效，请重新登录：缺少 ACTIVE");
        }
        return claim.asString();
    }

    /**
     * @author carl
     * @description TODO 获取应用
     */
    public static String getApplication(String token) {
        Claim claim = getTokenBody(token).getClaim(AuthData.APPLICATION.toString());
        if (claim.isNull()) {
            throw new JWTDecodeException("访问凭证已失效，请重新登录：缺少 APPLICATION");
        }
        return claim.asString();
    }

    /**
     * @author carl
     * @description TODO 获取role
     */
    public static AuthRole getRole(String token) {
        Claim claim = getTokenBody(token).getClaim(AuthData.ROLE.toString());
        if (claim.isNull()) {
            throw new JWTDecodeException("访问凭证已失效，请重新登录：缺少 ROLE");
        }
        return AuthRole.valueOf(claim.asString());
    }

}
