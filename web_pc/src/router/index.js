import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login.vue'
import Index from '@/pages/Index.vue'
import User from '@/pages/admin/user/List'
import Role from '@/pages/admin/role/List'
import Menu from '@/pages/admin/menu/List'
import Home from '@/pages/Home.vue'
import Department from '@/pages/admin/department/List'
import Position from '@/pages/admin/position/List'
import Model from '@/pages/admin/activiti/model/List'
import Form from '@/pages/admin/activiti/form/List'
import DeployMent from '@/pages/admin/activiti/process/List'
import NodeSetting from '@/pages/admin/activiti/process/NodeSetting'
import SysLog from '@/pages/admin/syslog/list'
import Notice from '@/pages/admin/notice/List'
import Druid from '@/pages/admin/druid/List'

Vue.use(Router)

//获取原型对象上的push函数
const originalPush = Router.prototype.push
//修改原型对象中的push方法
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
  routes: [
    {
      path: '/login',
      name: '登陆页面',
      component: Login,
    },
    {
      path: '/activiti/process/nodeSetting',
      name: '节点设置',
      component: NodeSetting
    },
    {
      path: '/',
      component: Home,
      redirect: "admin/index",
      children: [
        {
          path: 'admin/index',
          name: '首页',
          component: Index,
        },
        {
          path: 'admin/user/list',
          name: '用户列表',
          component: User,
        },
        {
          path: 'admin/role/list',
          name: '角色列表',
          component: Role,
        },
        {
          path: 'admin/menu/list',
          name: '菜单列表',
          component: Menu,
        },
        {
          path: 'admin/department/list',
          name: '部门列表',
          component: Department,
        },
        {
          path: 'admin/position/list',
          name: '岗位列表',
          component: Position,
        },
        {
          path: 'admin/activiti/model/list',
          name: '模型列表',
          component: Model,
        },
        {
          path: 'admin/activiti/form/list',
          name: '表单申请',
          component: Form,
        },
        {
          path: 'admin/activiti/process/list',
          name: '已发布流程',
          component: DeployMent
        },
        {
          path: 'admin/syslog/list',
          name: '操作日志',
          component: SysLog
        },
        {
          path: 'admin/notice/list',
          name: '公告管理',
          component: Notice
        }, {
          path: 'admin/druid',
          name: '数据源监控',
          component: Druid
        }
      ]
    },
  ]
})
