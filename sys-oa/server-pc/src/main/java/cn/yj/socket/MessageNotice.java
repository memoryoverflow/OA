package cn.yj.socket;

import cn.yj.common.BeanFactoryWrapper;
import cn.yj.commons.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-01-25 14:41
 */
@ServerEndpoint("/message")
public class MessageNotice extends BeanFactoryWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(MessageNotice.class);


    /**
     * 记录在线socket
     */
    private static int onlineCount = 0;

    /**
     * socket集合
     */
    private static CopyOnWriteArraySet<MessageNotice> SOCKET_LIST = new CopyOnWriteArraySet();

    /**
     * 当前socket 会话
     */
    private Session session;

    /**
     * 连接参数
     */
    private HashMap<String, String> PARAMS = new HashMap();

    @OnOpen
    public void onOpen(Session session)
    {
        this.session = session;
        SOCKET_LIST.add(this);
        addOnlineCount();
        this.setsParams(session.getQueryString());
    }


    @OnClose
    public void onClose()
    {
        logger.error("socket onClose");
        SOCKET_LIST.remove(this);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message, Session session)
    {
        logger.error("socket onMessage:{}" + message);
    }

    @OnError
    public void onError(Session session, Throwable error)
    {
        logger.error("socket error:{}", error);
    }

    public void sendMessage(String message) throws IOException
    {
        logger.error("socket sendMessage:{}", message);
        this.session.getBasicRemote().sendText(message);
    }


    public void setsParams(String params)
    {
        if (StringUtils.isNotNull(params))
        {
            String[] arrParam = params.split("&");
            String[] var3 = arrParam;
            int var4 = arrParam.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                String sparam = var3[var5];
                String[] arrKeyValue = sparam.split("=");
                this.PARAMS.put(arrKeyValue[0], arrKeyValue[1]);
            }
        }
    }

    public static synchronized int getOnlineCount()
    {
        return onlineCount;
    }

    public static synchronized void addOnlineCount()
    {
        ++onlineCount;
    }

    public static synchronized void subOnlineCount()
    {
        --onlineCount;
    }

}
