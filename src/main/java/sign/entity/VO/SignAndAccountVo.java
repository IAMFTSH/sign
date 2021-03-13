package sign.entity.VO;

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
 * 签到信息
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value="SignAndAccountVo对象", description="签到信息")
public class SignAndAccountVo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "课程上课时间")
    private Integer classTimeId;

    @ApiModelProperty(value = "学生id")
    private Integer studentId;

    @ApiModelProperty(value = "状态(未签到，超过最后时间签到，迟到，成功)")
    private Integer state;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "src地址")
    private String imageAddress;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "phone")
    private String phone;

    @ApiModelProperty(value = "username")
    private String username;

}
