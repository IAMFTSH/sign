package sign.entity.VO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sign.entity.Account;
import sign.entity.Course;

/**
 * @author 邝明山
 * on 2021/3/9 16:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value="CourseAndTeacherVo对象", description="课程与老师信息")
public class CourseAndTeacherVo {
    private String courseId;
    private String courseName;
    private String teacherUsername;
    private String className;
    private String teacherName;
    private String teacherId;
}
