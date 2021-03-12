package sign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import sign.entity.ClassTime;
import sign.entity.VO.ClassTimeAllInfoVo;
import sign.entity.VO.SignAndClassTimeAndClassroomAndTeachingAreaVo;
import sign.mapper.ClassTimeMapper;
import sign.service.ClassTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private ClassTimeMapper classTimeMapper;

    @Override
    public List<SignAndClassTimeAndClassroomAndTeachingAreaVo> selectSignAndClassTimeVoList(int studentId, int courseId) {
        return classTimeMapper.selectSignAndClassTimeVoList(studentId, courseId);
    }

    @Override
    public List<ClassTimeAllInfoVo> selectClassTimeAllInfo(int classTimeId) {
        return classTimeMapper.selectClassTimeAllInfo(classTimeId);
    }
}
