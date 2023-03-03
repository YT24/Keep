package keep.sso.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import keep.sso.ticket.entity.KeepAccessToken;
import keep.sso.ticket.mapper.KeepAccessTokenMapper;
import keep.sso.ticket.service.KeepAccessTokenService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
@Service
public class KeepAccessTokenServiceImpl extends ServiceImpl<KeepAccessTokenMapper, KeepAccessToken> implements KeepAccessTokenService {

    private static String AT_PREF = "AT-";

    @Override
    public boolean save(KeepAccessToken keepAccessToken) {
        return this.save(keepAccessToken);
    }
}
