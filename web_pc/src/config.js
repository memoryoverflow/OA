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
  menuExitRole: false, // 菜单中是否有角色
  useServerMenu: true, // 使用服务端的菜单
  res_success_code: 1, // 请求成功的状态码
  res_error_code: 0, // 服务端抛出的错误代码
  no_login_code: 9, // 未登录响应的状态码
  no_page: 404, // 访问服务端 错误url 响应的状态码
  autoAlertErrorMsg: true,  // 统一异常响应拦截弹出错误提示。
  // 响应参数字段，数据格式为
  /**
   * 例如分页数据响应对象：如下  可变的是 code ， msg， data，  rows，pageSize，pages，size，total 这几个参数对象
   * 表格分页参数必须是这样子的格式
   * {
   *   "code": 1,
   *   "msg": "操作成功",
   *   "data":{
   *     rows: [{
   *       id:1,
   *       name:'张三',
   *       sex:'男'
   *     }],
   *     pageSize: 10,
   *     pageNum:1
   *   }
   * }
   */
  responseConfig: {
    response_code: 'code', // 响应状态吗
    response_msg: 'msg',
    response_data: 'data',
    // 分页参数
    pageNum: 'current',
    pageSize: 'pageSize',
    pages: 'pages',
    rows: 'rows',
    size: 'size',
    total:'total'
  },
  sysName:"XX系统后台管理", // 系统名称
  routerIndex: { // 路由首页
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
  active: "dev", //dev 环境
  server: {
    dev: {
      context_path: '/oa',
      address: "http://127.0.0.1:8082",
    },
    prod: {
      context_path: '/oa',
      address: "https://thisforyou.cn:1443",
    }
  },
};
exports.config = config
