/*
package config.filters;

import graduation.project.mapper.DormAreaMapper;
import graduation.project.pojo.vo.DormAreaVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

*/
/**
 * @author 邝明山
 * on 2020/12/9 12:06
 *//*

@WebFilter(filterName = "DormAreaAndDormUpdateFilter",urlPatterns={"/teacher/dorm","/teacher/dorm-area"})
public class DormAreaAndDormUpdateFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(DormAreaAndDormUpdateFilter.class);

    @Autowired
    DormAreaMapper dormAreaMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        chain.doFilter(request, response);
        if(!HttpMethod.GET.name().equals(httpRequest.getMethod())){
            List<DormAreaVo> dormAreaVos = dormAreaMapper.dormAreaAndDorm();
            redisTemplate.opsForValue().set("dormAreaVos",dormAreaVos);
        }
    }

    @Override
    public void destroy() {

    }
}
*/
