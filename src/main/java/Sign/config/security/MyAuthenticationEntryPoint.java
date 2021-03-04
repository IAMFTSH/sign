package Sign.config.security;

import Sign.common.result.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 邝明山
 * on 2020/11/22 12:37
 * 用来解决匿名用户访问无权限资源时的异常
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(ResultCode.FORBIDDEN.getCode(), "没有访问权限,请登录");
    }
}
