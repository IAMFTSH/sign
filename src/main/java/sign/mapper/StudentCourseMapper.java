package sign.mapper;

import org.apache.ibatis.annotations.Param;
import sign.entity.StudentCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sign.entity.VO.CourseAndTeacherVo;
import sign.entity.VO.StudentAndCoursesVo;

import java.util.List;

/**
 * <p>
 * 学生与课程 Mapper 接口
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    /**
     * 搜索
     * @param id
     * @return
     */

    List<CourseAndTeacherVo> studentAndCourseAndTeacher(@Param("id") int id);
}
