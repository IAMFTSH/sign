package sign.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import sign.entity.TeachingArea;

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
@ApiModel(value="Classroom对象", description="课室")
public class ClassroomVo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课室号")
    private String classroomNum;

    @ApiModelProperty(value = "所属教学区id")
    private Integer teachingAreaId;

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "范围 ")
    private Double radius;

    @ApiModelProperty(value = "教学区 ")
    private TeachingArea teachingArea;

}
