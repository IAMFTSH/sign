package sign.entity.VO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 邝明山
 * on 2021/3/9 16:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value="StudentAndCoursesVo对象", description="学生与课程信息")
public class StudentAndCoursesVo {
    private int id;

    List<CourseAndTeacherVo> courseAndTeacherVoList;
}
