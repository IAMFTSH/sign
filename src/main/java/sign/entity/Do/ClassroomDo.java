package sign.entity.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 课室
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ClassroomDo对象", description="课室")
public class ClassroomDo implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String classroomNum;

    private Integer teachingAreaId;

    private String TeachingAreaName;


}
