/*
package config.filters;

import graduation.project.mapper.CollegeMapper;
import graduation.project.pojo.vo.CollegeVo;
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

@WebFilter(filterName = "CollegeToClassUpdateFilter",urlPatterns={"/teacher/college","/teacher/specialty","/teacher/class-table"})
public class CollegeToClassUpdateFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CollegeToClassUpdateFilter.class);

    @Autowired
    CollegeMapper collegeMapper;
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
            List<CollegeVo> collegeVos = collegeMapper.selectCollegeToClass();
            redisTemplate.opsForValue().set("collegeVos",collegeVos);
        }
    }

    @Override
    public void destroy() {

    }
}
*/
