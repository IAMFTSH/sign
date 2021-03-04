package Sign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生与课程
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StudentCourse对象", description="学生与课程")
public class StudentCourse implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "学生id")
    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;


}
