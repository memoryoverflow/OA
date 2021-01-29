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
import {error, info, success, warning} from '@/utils/alertUtils';

// token
import {getToken, getUser, removeToken, saveToken, saveUser, vueCookies} from '@/cookie';
import {deleteRequest, getRequest, postRequest, postRequestJSON, putRequest, uploadRequest} from "./HttpRequest";
import ListFrame from "@/pages/frame/ListFrame.vue";
import MainFrame from "@/pages/frame/MainFrame.vue";
import Dialog from "@/pages/frame/Dialog.vue";
import Auth from "@/pages/frame/Auth.vue";


Vue.prototype.$removeToken = removeToken;
Vue.prototype.$saveToken = saveToken;
Vue.prototype.$getToken = getToken;
Vue.prototype.$getUser = getUser;
Vue.prototype.$saveUser = saveUser;

Vue.config.devtools = true;


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
Vue.prototype.$serverContextPath = config.serverContextPath;
Vue.prototype.$server = config.domain + config.serverContextPath;


Vue.component("table-frame", ListFrame);
Vue.component("main-frame", MainFrame);
Vue.component("yj-dialog", Dialog);
Vue.component("auth", Auth);


/* eslint-disable no-new */
Vue.prototype.$EventBus = new Vue();

/** 投机取巧 修改父组建的值 不让它报错 */
Vue.config.warnHandler = function (msg) {
  if (!msg.includes('Avoid mutating a prop directly since the value will be overwritten whenever the parent component re-renders.')) { // uniApp bug: https://ask.dcloud.net.cn/question/71966
    return console.warn && console.warn(msg)
  }
}

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})

