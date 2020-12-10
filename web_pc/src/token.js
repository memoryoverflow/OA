import VueCookies from 'vue-cookies'

const USER_KEY = "userInfo"
const TOKEN_KEY = "token"
/**
 * 移除token
 */
export const removeToken = () => {
  VueCookies.remove(TOKEN_KEY);
  VueCookies.remove(USER_KEY)
}

/**
 *  存储token
 * @param {*} token
 */
export const saveToken = (token) => {
  VueCookies.set(TOKEN_KEY, token, "30min");
}

/**
 * 获取token
 */
export const getToken = () => {
  return VueCookies.get(TOKEN_KEY);
}

export const getUser = () => {
  return VueCookies.get(USER_KEY);
}

export const saveUser = (user) => {
  VueCookies.set(USER_KEY, user, '30min');
}

export const vueCookies = VueCookies;
