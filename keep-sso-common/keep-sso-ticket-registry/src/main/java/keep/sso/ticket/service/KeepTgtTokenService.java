package keep.sso.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import keep.sso.ticket.entity.KeepTgtToken;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
public interface KeepTgtTokenService extends IService<KeepTgtToken> {

    boolean save(KeepTgtToken tgtToken);

}
