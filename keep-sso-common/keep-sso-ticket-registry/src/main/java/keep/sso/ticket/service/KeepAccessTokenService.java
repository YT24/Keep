package keep.sso.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import keep.sso.ticket.entity.KeepAccessToken;
import keep.sso.ticket.entity.KeepRefreshToken;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
public interface KeepAccessTokenService extends IService<KeepAccessToken> {

    boolean save(KeepAccessToken keepAccessToken);
}
