package sign.service;

import sign.entity.StudentCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import sign.entity.VO.CourseAndTeacherVo;
import sign.entity.VO.StudentAndCoursesVo;

import java.util.List;

/**
 * <p>
 * 学生与课程 服务类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface StudentCourseService extends IService<StudentCourse> {
    /**
     * 搜索
     * @param id
     * @return
     */
    public List<CourseAndTeacherVo> studentAndCourseAndTeacher(int id);
}
