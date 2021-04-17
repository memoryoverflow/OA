package cn.yj.admin.verificationCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-04-02 13:49
 */
class LocalVerificationCodeSaveType extends VerificationCodeSaveType{
    private static Logger logger = LoggerFactory.getLogger(LocalVerificationCodeSaveType.class);

    private static final long EXPIRE_TIME = 1 * 30 * 1000;

    private static final Map<String, String> CODE_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Long> TIME_MAP = new ConcurrentHashMap<>();

    public static void save(String code, HttpServletRequest request) {
        String id = request.getSession().getId();
        CODE_MAP.put(id, code);
        TIME_MAP.put(code, System.currentTimeMillis());
    }

    public static String get(HttpSession session) {
        if (session != null) {
            String id = session.getId();
            String code = CODE_MAP.get(id);
            if (code == null || !TIME_MAP.containsKey(code)) {
                return null;
            }
            CODE_MAP.remove(id);
            TIME_MAP.remove(code);
            return code;
        }
        return null;
    }

    static {
        initMonitor();
    }

    private static void initMonitor() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1 * 1000);
                    TIME_MAP.forEach((key, value) -> {
                        long currentTimeMillis = System.currentTimeMillis();
                        // 过期
                        if (currentTimeMillis - value > EXPIRE_TIME) {
                            TIME_MAP.remove(key);
                        }
                    });
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }

            }
        }).start();
    }
}
