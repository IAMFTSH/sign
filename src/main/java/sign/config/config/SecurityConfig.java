package sign.config.config;


import sign.common.contant.ProjectConstant;
import sign.common.contant.SecurityConstant;
import sign.config.security.JwtAuthenticationFilter;
import sign.config.security.MyAccessDeniedHandler;
import sign.config.security.MyAuthenticationEntryPoint;
import sign.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author FTSH
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    //加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    AccountService accountService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 表单登录
        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .and()
                .userDetailsService(accountService)
                //返回上一层
                // 授权配置
                .authorizeRequests()
                // 无需认证
                .antMatchers(
                        SecurityConstant.LOGIN,
                        SecurityConstant.IMAGE_VALIDATE,
                        SecurityConstant.SWAGGER,
                        SecurityConstant.REGISTER_ACCOUNT,
                        SecurityConstant.UPDATEPASSWORD,
                        SecurityConstant.WX_LOGIN,
                        SecurityConstant.WC_REGISTER,
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html/**",
                        "/webjars/**").permitAll()
                // 所有请求
/*                .antMatchers("/account/getAccountInformation").hasAnyAuthority("1")
                .antMatchers("/teacher/**").hasAnyAuthority("1", "2")*/
                .anyRequest()
                // 都需要认证
                .authenticated()
                .and()
                .cors()
                .and()
                //攻击防护
                .csrf()

                // 配置csrf攻击
                .disable();
    }

    //配置AuthenticationManager
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
