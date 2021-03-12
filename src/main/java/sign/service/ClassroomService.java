package sign.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sign.entity.Classroom;
import com.baomidou.mybatisplus.extension.service.IService;
import sign.entity.Do.ClassroomDo;
import sign.entity.VO.ClassroomVo;

import java.util.List;

/**
 * <p>
 * 课室 服务类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface ClassroomService extends IService<Classroom> {
    /**
     * 搜索
     * @param classroomDo
     * @return
     */
    public IPage<ClassroomVo> selectClassroomAndTeachingArea(Page<?> page, ClassroomDo classroomDo);

}
