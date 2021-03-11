package sign.service;

import sign.entity.ClassTime;
import com.baomidou.mybatisplus.extension.service.IService;
import sign.entity.VO.SignAndClassTimeAndClassroomAndTeachingAreaVo;

import java.util.List;

/**
 * <p>
 * 上课时间信息 服务类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface ClassTimeService extends IService<ClassTime> {
    /**
     * 搜索
     * @param studentId
     * @param courseId
     * @return
     */
    public List<SignAndClassTimeAndClassroomAndTeachingAreaVo> selectSignAndClassTimeVoList(int studentId, int courseId);
}
