package sign.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import sign.entity.Sign;
import sign.entity.VO.SignAndAccountVo;
import sign.mapper.SignMapper;
import sign.service.SignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 签到信息 服务实现类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {

    @Autowired
    private SignMapper signMapper;
    @Override
    public IPage<SignAndAccountVo> selectSignList(Page page, int classTimeId, String username, String name, int state) {
        return signMapper.selectSignList(page,classTimeId,username,name,state);
    }
}
