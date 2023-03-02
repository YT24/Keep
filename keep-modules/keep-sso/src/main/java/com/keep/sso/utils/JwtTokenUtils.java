package com.keep.sso.utils;


import com.keep.common.core.domain.constants.CommanConstants;
import com.keep.common.core.expection.CustomExpection;
import com.keep.common.core.expection.TokenExpection;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.keep.common.core.domain.constants.CommanConstants.APP_SECRET_KEY;
import static io.jsonwebtoken.Jwts.parser;

/**
 * @Description: jwt工具
 * @author: yangte
 * @Date: 2021/7/6 12:07 上午
 */
@Slf4j
public class JwtTokenUtils {

    private final static Clock CLOCK = DefaultClock.INSTANCE;




    /**
     * 生成token
     *
     * @param
     * @return
     */
    public static String generatorToken(Map<String, Object> claims, String subject, long expiration) {
        final Date createdDate = CLOCK.now();
        final Date expirationDate = calculateExpirationDate(createdDate, expiration);
        return Jwts.builder()
                .setClaims(claims)             // 如果使用setClaims去设置需要移到最上面，不然会覆盖掉之前设置的内容
                .setSubject(subject)           // (面向的用户)存储 用户ID
                .setIssuedAt(createdDate)      // 签发时间
                .setIssuer("com.keep.app")     // 颁发者
                .setExpiration(expirationDate) //过期时间
                .setAudience("test.com")               // 接收方
                .signWith(SignatureAlgorithm.HS256, APP_SECRET_KEY)
                .compact();

    }

    private static Date calculateExpirationDate(Date createdDate, long expiration) {
        return new Date(createdDate.getTime() + expiration);
    }

    /**
     * 判断token是否过期
     * <p>
     * todo 修改这个方法，过期了其实是会抛异常的 ExpiredJwtException
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        Claims claims = parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    /**
     * 检查token有效性
     *
     * @param token
     * @return
     */
    public static boolean checkJwt(String token) {
        try {
            parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("token is expired", e);
            throw new TokenExpection(401, "token is expired");

        } catch (Exception e) {
            log.error("token check expection：",e);
            throw new CustomExpection(500, "token check expection：" + e.getMessage());

        }
        return true;
    }

    /**
     * 获取token内部信息
     *
     * @param token
     * @return
     */
    public static Map<String, Object> parserToken(String token) {
        Claims claims = null;
        Map<String, Object> map = new HashMap<>(4);

        try {

            parser().setClock(DefaultClock.INSTANCE);

            claims = parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            //  需要前端重新登录了。
            //throw new TokenExpection(401, "token已经过期");
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomExpection(500, "token解析异常:" + e.getMessage());
        }

        map.put(CommanConstants.USER_ID, claims.getSubject());
        return map;
    }
}

