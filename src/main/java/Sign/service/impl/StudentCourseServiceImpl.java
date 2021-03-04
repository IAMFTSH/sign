package Sign.service.impl;

import Sign.entity.StudentCourse;
import Sign.mapper.StudentCourseMapper;
import Sign.service.StudentCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
