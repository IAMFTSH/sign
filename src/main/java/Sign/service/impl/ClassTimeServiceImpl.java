package Sign.service.impl;

import Sign.entity.ClassTime;
import Sign.mapper.ClassTimeMapper;
import Sign.service.ClassTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 上课时间信息 服务实现类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Service
public class ClassTimeServiceImpl extends ServiceImpl<ClassTimeMapper, ClassTime> implements ClassTimeService {

}
