package keep.sso.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import keep.sso.ticket.entity.KeepTgtToken;
import keep.sso.ticket.mapper.KeepTgtTokenMapper;
import keep.sso.ticket.service.KeepTgtTokenService;
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
public class KeepTgtTokenServiceImpl extends ServiceImpl<KeepTgtTokenMapper, KeepTgtToken> implements KeepTgtTokenService {

    private static String AT_PREF = "TGT-";

    @Override
    public boolean save(KeepTgtToken tgtToken) {

        return this.save(tgtToken);
    }
}
