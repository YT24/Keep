package keep.sso.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import keep.sso.ticket.entity.KeepRefreshToken;
import keep.sso.ticket.entity.KeepTgtToken;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
public interface KeepRefreshTokenService extends IService<KeepRefreshToken> {

    boolean save(KeepRefreshToken keepRefreshToken);
}
