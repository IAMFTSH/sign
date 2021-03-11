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
import sign.entity.Sign;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 邝明山
 * on 2021/3/10 9:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value="SignAndClassTimeAndClassroomAndTeachinAreaVo", description="学生签到信息与上课时间")
public class SignAndClassTimeAndClassroomAndTeachingAreaVo implements Serializable {
    private Integer classTimeId;

    @ApiModelProperty(value = "课室id ")
    private Integer classroomId;

    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    @ApiModelProperty(value = "签到开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "签到迟到时间")
    private LocalDateTime  lateTime;

    @ApiModelProperty(value = "最后签掉时间")
    private LocalDateTime  deadline;

    @ApiModelProperty(value = "Sign表")
    private Sign sign;

    @ApiModelProperty(value = "Sign表")
    private ClassroomVo classroomVo;
}
