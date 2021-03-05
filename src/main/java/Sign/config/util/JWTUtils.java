package Sign.config.util;

import Sign.entity.VO.OpenId;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {

    public final Algorithm SIGN = Algorithm.HMAC256("123456789");

    public String createToken(UserDetails user){
        Map<String,String> claims= new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
/*        StringBuffer stringBuffer=new StringBuffer();
        for(Object authority:user.getAuthorities().toArray()){
            if (stringBuffer.length()>0){
                stringBuffer.append(",");
            }
            stringBuffer.append(authority.toString());
        }
        claims.put("authorities", stringBuffer.toString());*/
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 24*60 * 60 * 1000))  //设置过期时间
                .withClaim("principal",claims)
                .sign(SIGN);
    }

    public String createOpenIdToken(OpenId openId){
        Map<String,String> claims= new HashMap<>();
        claims.put("openId", openId.getOpenid());
        claims.put("sessionKey", openId.getSession_key());
/*        StringBuffer stringBuffer=new StringBuffer();
        for(Object authority:user.getAuthorities().toArray()){
            if (stringBuffer.length()>0){
                stringBuffer.append(",");
            }
            stringBuffer.append(authority.toString());
        }
        claims.put("authorities", stringBuffer.toString());*/
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 24*60 * 60 * 1000))  //设置过期时间
                .withClaim("principal",claims)
                .sign(SIGN);
    }
    public Map<String, Object> parseToken(String token){
        try{
            DecodedJWT verify = JWT.require(SIGN).build().verify(token);
            if (verify.getExpiresAt().after(new Date())){
                return verify.getClaim("principal").asMap();
            }
        }catch (JWTVerificationException ignored){
            return null;
        }
        return null;
    }

}