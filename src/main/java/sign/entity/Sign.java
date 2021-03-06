package sign.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 签到信息
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Sign对象", description="签到信息")
public class Sign implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "课程上课时间")
    private Integer id;

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "状态")
    private String reason;

    @ApiModelProperty(value = "状态")
    private String imageAddress;

}
