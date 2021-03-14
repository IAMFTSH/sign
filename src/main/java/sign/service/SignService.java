package sign.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import sign.entity.Sign;
import com.baomidou.mybatisplus.extension.service.IService;
import sign.entity.VO.NumericalVo;
import sign.entity.VO.SignAndAccountVo;

import java.util.List;

/**
 * <p>
 * 签到信息 服务类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface SignService extends IService<Sign> {
    /**
     * 搜
     * @param classTimeId
     * @return
     */
    public IPage<SignAndAccountVo> selectSignList(Page page, int classTimeId, String username, String name, int state);
    /**
     * 搜
     * @param courseId
     * @return
     */
    public List<NumericalVo> numerical(int courseId);
}
