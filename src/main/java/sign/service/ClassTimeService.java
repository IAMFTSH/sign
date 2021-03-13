package sign.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sign.entity.ClassTime;
import com.baomidou.mybatisplus.extension.service.IService;
import sign.entity.VO.ClassTimeAllInfoVo;
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

    /**
     * 搜索
     *
     * @param courseId
     * @return
     */
    public IPage<ClassTimeAllInfoVo> selectClassTimeAllInfo(Page<?> page, int courseId);

    /**
     * ss
     * @param id
     * @return
     */
    public ClassTimeAllInfoVo selectOneById(int id);
}
