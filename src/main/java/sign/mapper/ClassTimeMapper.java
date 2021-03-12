package sign.mapper;

import org.apache.ibatis.annotations.Param;
import sign.entity.ClassTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sign.entity.VO.ClassTimeAllInfoVo;
import sign.entity.VO.SignAndClassTimeAndClassroomAndTeachingAreaVo;

import java.util.List;

/**
 * <p>
 * 上课时间信息 Mapper 接口
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface ClassTimeMapper extends BaseMapper<ClassTime> {
    /**
     * 搜索
     *
     * @param studentId
     * @param courseId
     * @return
     */
    List<SignAndClassTimeAndClassroomAndTeachingAreaVo> selectSignAndClassTimeVoList(@Param("studentId") int studentId, @Param("courseId") int courseId);
    /**
     * 搜索
     *
     * @param classTimeId
     * @return
     */
    List<ClassTimeAllInfoVo> selectClassTimeAllInfo(int classTimeId);
}
