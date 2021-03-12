package sign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import sign.entity.Course;
import sign.entity.VO.CourseVo;
import sign.mapper.CourseMapper;
import sign.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseVo> selectCourseAndNumerical(int teacherId) {
        return courseMapper.selectCourseAndNumerical(teacherId);
    }
}
