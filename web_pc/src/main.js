// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import router from './router'
import config from '../config/index'
import App from './App'
// element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import commUtils from "@/utils/CommonUtils.js";
// 编辑器
import MavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// 弹窗提示
import { success, info, warning, error } from '@/utils/alertUtils';

// token
import { removeToken, saveToken, getToken,saveUser,getUser,vueCookies} from '@/token';
Vue.prototype.$removeToken = removeToken;
Vue.prototype.$saveToken = saveToken;
Vue.prototype.$getToken = getToken;
Vue.prototype.$getUser = getUser;
Vue.prototype.$saveUser = saveUser;

Vue.config.devtools = true;


import {
  deleteRequest,
  postRequest,
  putRequest,
  getRequest,
  uploadRequest,
  postRequestJSON
} from "./HttpRequest";



Vue.config.productionTip = false


// 请求
Vue.prototype.$put = putRequest
Vue.prototype.$del = deleteRequest
Vue.prototype.$post = postRequest
Vue.prototype.$get = getRequest
Vue.prototype.$postJson = postRequestJSON
Vue.prototype.$filePost = uploadRequest
Vue.prototype.$utils = commUtils


Vue.use(ElementUI);
Vue.use(MavonEditor)
Vue.use(vueCookies)


//注册提示全局属性
Vue.prototype.$error = error
Vue.prototype.$success = success
Vue.prototype.$info = info
Vue.prototype.$warning = warning


Vue.prototype.$doMain = config.domain;


import ListFrame from "@/pages/frame/ListFrame.vue";
import MainFrame from "@/pages/frame/MainFrame.vue";
import Dialog from "@/pages/frame/Dialog.vue";
import Auth from "@/pages/frame/Auth.vue";
Vue.component("table-frame", ListFrame);
Vue.component("main-frame", MainFrame);
Vue.component("yj-dialog", Dialog);
Vue.component("auth", Auth);


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

