package cn.yj.user.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * <br>
 *     自定义密码验证
 *
 * @author 永健
 * @since 2020-06-05 21:50
 */
public class ShiroCredentialsMatcher implements CredentialsMatcher
{
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
    {
        UsernamePasswordToken u_pTaken = (UsernamePasswordToken) token;
        String inputPassword = MD5.getInstance().getMD5(u_pTaken.getUsername().concat(String.valueOf(u_pTaken.getPassword())));

        Object credentials = info.getCredentials();
        if (credentials != null)
        {
            String dbPass = String.valueOf(credentials);
            if (dbPass.equals(inputPassword))
            {
                return true;
            }
        }

        return false;
    }
}
