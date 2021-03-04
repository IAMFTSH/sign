package Sign.service.impl;

import Sign.entity.Sign;
import Sign.mapper.SignMapper;
import Sign.service.SignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签到信息 服务实现类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {

}
