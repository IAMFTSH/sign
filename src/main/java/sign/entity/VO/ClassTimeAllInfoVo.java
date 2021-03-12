package sign.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ClassTimeAllInfo", description = "学生签到信息与上课时间")
public class ClassTimeAllInfoVo {
    private Integer classTimeId;

    @ApiModelProperty(value = "课室id ")
    private Integer classroomId;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "签到开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "签到迟到时间")
    private LocalDateTime lateTime;

    @ApiModelProperty(value = "最后签掉时间")
    private LocalDateTime deadline;

    @ApiModelProperty(value = "Sign表")
    private Sign sign;

    @ApiModelProperty(value = "nullNum")
    private Integer nullNum;

    @ApiModelProperty(value = "nullNum")
    private Integer failNum;

    @ApiModelProperty(value = "lateNum")
    private Integer lateNum;

    @ApiModelProperty(value = "successNum")
    private Integer successNum;

    @ApiModelProperty(value = "allNum")
    private Integer allNum;

    @ApiModelProperty(value = "classroomVo")
    private ClassroomVo classroomVo;
}
