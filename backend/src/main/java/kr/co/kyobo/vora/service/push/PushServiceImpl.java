package kr.co.kyobo.vora.service.push;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PushServiceImpl implements PushService {
    @Override
    public boolean sendPushService() {
        return false;
    }
}
