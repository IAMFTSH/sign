package Sign.service.impl;

import Sign.entity.Course;
import Sign.mapper.CourseMapper;
import Sign.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}
