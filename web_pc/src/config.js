/**
 * 服务端的参数结构必须是这样子的
 *
 *   最外一层是这样子的。
 *   所有的数据对象都要在data值里面
 * {
 *    code:0,
 *    msg: '',
 *    data: {}
 * }
 */
const config = {
  // 登陆页面
  loginPage: '/login',
  menuExitRole: false,
  useServerMenu: true,
  // 请求成功的状态码
  res_success_code: 1,
  // 服务端抛出的错误代码
  res_error_code: 0,
  // 未登录响应的状态码
  no_login_code: 9,
  // 访问服务端 错误url 响应的状态码
  no_page: 404,
  autoAlertErrorMsg: true,
  // 响应参数字段
  responseConfig: {
    "response_code": 'code',
    "response_msg": 'msg',
    "response_data": 'data',

    "pageNum": 'current',
    "pageSize": 'pageSize',
    "pages": 'pages',
    "rows": 'rows',
    "size": 'size',
    "total": "total"
  },
  routerIndex: {
    name: "首页",
    path: "/admin/index",
    router: "/admin/index",
    icon: 'el-icon-s-home',
    type: 2,
    perName: "首页",
    parentId: 0,
    outJoin: false,
    href: '',
    children: []
  },
  active: "prod",
  server: {
    dev: {
      context_path: '/oa',
      address: "http://127.0.0.1:8081",
    },
    prod: {
      context_path: '/oa',
      address: "http://thisforyou.cn:180",
    }
  },
};

exports.config = config
