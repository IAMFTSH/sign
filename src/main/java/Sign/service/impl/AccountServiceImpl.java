package Sign.service.impl;

import Sign.entity.Account;
import Sign.mapper.AccountMapper;
import Sign.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public Account accountSelectOne(String username) {
        QueryWrapper<Account> wrapper=new QueryWrapper();
        wrapper.eq("username",username);
        Account account = accountMapper.selectOne(wrapper);
        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        QueryWrapper<Account> wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        Account account = accountMapper.selectOne(wrapper);
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),
                true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(Integer.toString(account.getAuthority()))
        );
    }

}
