package sign.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import sign.entity.Classroom;
import sign.entity.Do.ClassroomDo;
import sign.entity.VO.ClassroomVo;
import sign.mapper.ClassroomMapper;
import sign.service.ClassroomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课室 服务实现类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {

    @Autowired
    private ClassroomMapper classroomMapper;

    @Override
    public IPage<ClassroomVo> selectClassroomAndTeachingArea(Page<?> page, ClassroomDo classroomDo) {
        return classroomMapper.selectClassroomAndTeachingArea(page,classroomDo);
    }
}
