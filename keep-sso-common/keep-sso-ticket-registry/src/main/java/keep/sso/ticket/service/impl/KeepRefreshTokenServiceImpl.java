package keep.sso.ticket.service.impl;

import keep.sso.ticket.entity.KeepRefreshToken;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import keep.sso.ticket.mapper.KeepRefreshTokenMapper;
import keep.sso.ticket.service.KeepRefreshTokenService;
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
public class KeepRefreshTokenServiceImpl extends ServiceImpl<KeepRefreshTokenMapper, KeepRefreshToken> implements KeepRefreshTokenService {

    private static String RT_PREF = "RT-";

    @Override
    public boolean save(KeepRefreshToken keepRefreshToken) {
        return this.save(keepRefreshToken);
    }
}
