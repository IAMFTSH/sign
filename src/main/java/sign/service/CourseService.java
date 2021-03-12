package sign.service;

import sign.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import sign.entity.VO.CourseVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface CourseService extends IService<Course> {
    /**
     * 搜索
     * @param teacherId
     * @return
     */
    public List<CourseVo> selectCourseAndNumerical(int teacherId);
}
