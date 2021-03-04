package Sign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Account对象", description="用户")
public class Account implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户名/学号/工号")
    @TableId(value = "username", type = IdType.AUTO)
    private String username;

    @ApiModelProperty(value = "密码 ")
    private String password;

    @ApiModelProperty(value = "微信用户唯一凭证")
    private String openId;

    @ApiModelProperty(value = "权限  权限")
    private Integer authority;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;


}
