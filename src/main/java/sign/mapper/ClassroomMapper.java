package sign.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import sign.entity.Classroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sign.entity.Do.ClassroomDo;
import sign.entity.VO.ClassroomVo;

import java.util.List;

/**
 * <p>
 * 课室 Mapper 接口
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface ClassroomMapper extends BaseMapper<Classroom> {

    /**
     * 搜索
     * @param classroomDo
     * @return
     */
    IPage<ClassroomVo> selectClassroomAndTeachingArea(Page<?> page,@Param("classroomDo") ClassroomDo classroomDo);
}
