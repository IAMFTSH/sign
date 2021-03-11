package sign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import sign.entity.StudentCourse;
import sign.entity.VO.CourseAndTeacherVo;
import sign.entity.VO.StudentAndCoursesVo;
import sign.mapper.StudentCourseMapper;
import sign.service.StudentCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生与课程 服务实现类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public List<CourseAndTeacherVo> studentAndCourseAndTeacher(int id) {
        return studentCourseMapper.studentAndCourseAndTeacher(id);
    }
}
