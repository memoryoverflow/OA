import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login.vue'
import User from '@/pages/admin/user/List'
import Role from '@/pages/admin/role/List'
import Menu from '@/pages/admin/menu/List'
import Home from '@/pages/Home.vue'
import Department from '@/pages/admin/department/List'
import Position from '@/pages/admin/position/List'

Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/login',
      name: '登陆页面',
      component: Login,
    },
    {
      path: '/',
      component: Home,
      redirect: "admin/user/list",
      children: [
        {
          path: 'admin/user/list',
          name: '用户管理',
          component: User,
        },
        {
          path: 'admin/role/list',
          name: '角色管理',
          component: Role,
        },
        {
          path: 'admin/menu/list',
          name: '权限管理',
          component: Menu,
        },
        {
          path: 'admin/department/list',
          name: '部门管理',
          component: Department,
        },
        {
          path: 'admin/position/list',
          name: '岗位管理',
          component: Position,
        },
      ]
    },
  ]
})