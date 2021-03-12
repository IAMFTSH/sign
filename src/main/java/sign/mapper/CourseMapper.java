package sign.mapper;

import sign.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sign.entity.VO.CourseVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface CourseMapper extends BaseMapper<Course> {


    /**
     * 搜索
     * @param teacherId
     * @return
     */
    List<CourseVo> selectCourseAndNumerical(int teacherId);
}
