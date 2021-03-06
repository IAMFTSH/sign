package sign.config.security;

import sign.entity.Account;
import sign.service.AccountService;
import sign.common.util.StringUtils;
import sign.config.util.JWTUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 邝明山
 * on 2020/10/30 13:55
 * 请求JWT拦截认证
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        //获取Http头中的Authentication
        String authentication = httpServletRequest.getHeader("Authentication");
        if (!StringUtils.isBlank(authentication)) {
            //解析JWT
            Map<String, Object> claims = jwtUtils.parseToken(authentication);
            //如果解析后不为空，并且Security上下文中没有获取到验证信息（说明没有登录过，因为是无状态的，所以不会保存验证信息。）
            if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = (String) claims.get("username");
                String password = (String) claims.get("password");
                String jwt = (String) redisTemplate.opsForValue().get("jwt:" + username);
                //判断token是否很久未使用，判断jwt是否有效
                if ((!StringUtils.isBlank(jwt)) && authentication.equals(jwt)) {
                    //
                    Account account = (Account) redisTemplate.opsForValue().get("account:" + username);
                    if (account == null) {
                        account = accountService.accountSelectOne("username", username);
                        account.setOpenId(null);
                        redisTemplate.opsForValue().set("account:" + username, account, 1, TimeUnit.DAYS);
                    }
                    /*if (passwordEncoder.matches(password, account.getPassword())) {*/
                    if (password.equals(account.getPassword())) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, account.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(Integer.toString(account.getAuthority())));
                        //在上下文中写入jwt解析后的信息，经过UsernamePasswordAuthenticationFilter时就会认为验证通过
                        SecurityContextHolder.getContext().setAuthentication(token);
                        redisTemplate.expire(username, 1, TimeUnit.HOURS);
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
