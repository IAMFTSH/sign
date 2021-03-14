package sign.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sign.entity.Sign;

import java.time.LocalDateTime;

/**
 * @author 邝明山
 * on 2021/3/12 16:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "NumericalVo", description = "学生签到信息与上课时间")
public class NumericalVo {
    private Integer id;

    private String username;

    private String name;

    private String phone;

    @ApiModelProperty(value = "nullNum")
    private Integer nullNum;

    @ApiModelProperty(value = "failNum")
    private Integer failNum;

    @ApiModelProperty(value = "lateNum")
    private Integer lateNum;

    @ApiModelProperty(value = "successNum")
    private Integer successNum;

    @ApiModelProperty(value = "allNum")
    private Integer allNum;

    @ApiModelProperty(value = "notPassNum")
    private Integer notPassNum;

}
