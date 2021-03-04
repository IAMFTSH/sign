package Sign.service;

import Sign.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
public interface AccountService extends IService<Account> , UserDetailsService {

    Account accountSelectOne(String username);
}
