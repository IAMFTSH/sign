package sign.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import sign.entity.Sign;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sign.entity.VO.NumericalVo;
import sign.entity.VO.SignAndAccountVo;

import java.util.List;

/**
 * <p>
 * 签到信息 Mapper 接口
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface SignMapper extends BaseMapper<Sign> {

    /**
     * 牛逼了
     *
     * @param classTimeId
     * @return
     */
    IPage<SignAndAccountVo> selectSignList(Page page, @Param("classTimeId") int classTimeId, @Param("username") String username, @Param("name") String name, @Param("state") int state);

    /**
     * ssasd
     * @param courseId
     * @return
     */
    List<NumericalVo> numerical(int courseId);
}
