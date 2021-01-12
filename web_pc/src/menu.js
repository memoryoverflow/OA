import {
  getRequest
} from "./HttpRequest";
let config = require("@/config.js").config;


const menuList = [
  {
    id: 1,
    perName: "首页",
    parentId: 0,
    parentName: '',
    router: '/admin/index',
    icon: 'el-icon-s-home',
    href: '',
    sort: '',
    type: 2,
    role: "admin|user",
    outJoin: false,
    children: []
  },
  {
    id: 2,
    perName: "用户管理",
    parentId: 0,
    role: "admin|user",
    router: '',
    parentName: '',
    icon: 'el-icon-user-solid',
    href: '',
    sort: '',
    type: 1,
    outJoin: false,
    children: [{
      id: 128317,
      perName: "所有用户",
      parentId: 2,
      role: "admin",
      router: '/admin/user/list',
      parentName: '',
      icon: 'el-icon-s-order',
      href: '',
      sort: '',
      type: 2,
      outJoin: false,
      children: []
    },
    {
      id: 19,
      perName: "未通过用户",
      parentId: 2,
      role: "admin",
      router: '/admin/user/noPending',
      parentName: '',
      icon: 'el-icon-s-order',
      href: '',
      sort: '',
      type: 2,
      outJoin: false,
      children: []
    },
    {
      id: 2,
      perName: "待审核用户",
      parentId: 2,
      router: '/admin/user/pending',
      parentName: '',
      icon: 'el-icon-s-order',
      href: '',
      sort: '',
      type: 2,
      role: "admin",
      outJoin: false,
      children: []
    },
    {
      id: 2,
      perName: "我的信息",
      parentId: 2,
      router: '/admin/user/myInfo',
      parentName: '',
      icon: 'el-icon-s-order',
      href: '',
      sort: '',
      type: 2,
      role: "admin|user",
      outJoin: false,
      children: []
    }]
  },
  {
    id: 2,
    perName: "专利管理",
    parentId: 0,
    role: "admin|user",
    router: '',
    parentName: '',
    icon: 'el-icon-s-flag',
    href: '',
    sort: '',
    type: 1,
    outJoin: false,
    children: [
      {
        id: 5,
        perName: "所有专利",
        parentId: 2,
        router: '/admin/apply/list',
        parentName: '',
        icon: 'el-icon-s-order',
        href: '',
        role: "admin",
        sort: '',
        type: 2,
        outJoin: false,
        children: []
      },
      {
        id: 7,
        perName: "待审批专利",
        parentId: 2,
        role: "admin",
        router: '/admin/apply/pending',
        parentName: '',
        icon: 'el-icon-s-order',
        href: '',
        sort: '',
        type: 2,
        outJoin: false,
        children: []
      },
      {
        id: 17,
        perName: "未通过专利",
        parentId: 2,
        role: "admin",
        router: '/admin/apply/noPending',
        parentName: '',
        icon: 'el-icon-s-order',
        href: '',
        sort: '',
        type: 2,
        outJoin: false,
        children: []
      },
      {
        id: 18,
        perName: "我的专利",
        parentId: 2,
        router: '/admin/apply/mypatent',
        parentName: '',
        icon: 'el-icon-s-order',
        href: '',
        sort: '',
        role: "user",
        type: 2,
        outJoin: false,
        children: []
      }
    ]
  },
  {
    id: 3,
    perName: "系统管理",
    parentId: 0,
    role: "admin",
    router: '',
    parentName: '',
    icon: 'el-icon-s-tools',
    href: '',
    sort: '',
    type: 1,
    outJoin: false,
    children: [
      {
        id: 4,
        perName: "系统配置",
        parentId: 3,
        role: "admin",
        router: '/admin/config/list',
        parentName: '',
        icon: 'el-icon-s-order',
        href: '',
        sort: '',
        type: 2,
        outJoin: false,
        children: []
      }
    ]
  },
  {
    id: 20,
    perName: "专利统计",
    parentId: 0,
    parentName: '',
    router: '/admin/apply/count',
    icon: 'el-icon-odometer',
    href: '',
    sort: '',
    type: 2,
    role: "admin|user",
    outJoin: false,
    children: []
  },
  {
    id: 21,
    perName: "数据备份",
    parentId: 0,
    parentName: '',
    router: '/admin/apply/backUp',
    icon: 'el-icon-attract',
    href: '',
    sort: '',
    type: 2,
    role: "admin",
    outJoin: false,
    children: []
  },
  {
    id: 22,
    perName: "操作日志",
    parentId: 0,
    parentName: '',
    router: '/admin/sysLog/list',
    icon: 'el-icon-discount',
    href: '',
    sort: '',
    type: 2,
    role: "admin",
    outJoin: false,
    children: []
  },
];

// 获取菜单
export const getMenus = (callback) => {

  if (config.useServerMenu) {
    let list=[config.routerIndex];
    getRequest("/permission/userMenuTree", {}).then((res) => {
      if (res.R) {
        let menus=res.data;
        for (let i = 0; i < menus.length; i++) {
          let menu = menus[i];
           list.push(menu);
        }
        callback(list);
      }
    });
    return;
  }
  callback(menuList);
}
