<style>
.showBtn {
  background: none;
  border: none;
  font-size: 23px;
  color: white;
  cursor: pointer;
}
</style>
<template>
  <el-container class="wrapper first_container">
    <yj-dialog
      :modal="dialog.modal"
      :width="dialog.width"
      :title="dialog.title"
      :dialogVisible="dialog.visible"
    >
      <template slot="dialog-content">
        <el-form :model="user" size="mini" label-width="80px">
          <el-form-item label="旧密码">
            <el-input type="password" v-model="user.oldPwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码">
            <el-input type="password" v-model="user.newPwd"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" style="text-align: center" class="dialog-footer">
          <el-button size="mini" @click="cancelCargoBtn">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveCargoBtn">确 定</el-button>
        </div>
      </template>
    </yj-dialog>
    <el-header :style="bg">
      <el-row>
        <el-col :span="left_lager_head_span">
          <div v-if="!isCollapse" class="left-lager-head">
            <img class="logo" src="@/assets/kafei_white.png" />
            <span class="titleMsg">{{ blogName }}</span>
          </div>
          <div v-else class="left-mini-head">
            <img class="logo" src="@/assets/kafei_white.png" />
          </div>
        </el-col>
        <el-col :span="showBtnSpan">
          <div class="showBtn">
            <i
              size="mini"
              v-if="!isCollapse"
              @click="showHide(isCollapse)"
              class="el-icon-s-fold"
            ></i>
            <i
              size="mini"
              v-else
              @click="showHide(isCollapse)"
              class="el-icon-s-unfold"
            ></i>
          </div>
        </el-col>
        <el-col style="text-align: right" :span="24 - left_lager_head_span - showBtnSpan">
          <div class="right_operate_main">
            <div class="right_operate">
              <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                  <div class="user"></div>
                  <span class="userName">欢迎您，{{ loginUser }}</span>
                  <i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-turn-off" @click.native="updatePwd"
                    >修改密码
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>

            <div class="outSys">
              <el-button type="text" icon="el-icon-switch-button" @click.native="logout">
                退出登陆
              </el-button>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-header>
    <el-container class="main_container">
      <el-aside :width="asideWidth">
        <el-menu
          class="el-menu-vertical-demo"
          :collapse="isCollapse"
          :unique-opened="true"
          :collapse-transition="true"
          :router="false"
          :default-active="defaultActive"
          :default-openeds="defaultOpeneIds"
          @select="menuSelect"
          @open="handOpen"
        >
          <!-- text-color="#fff" -->
          <!-- active-text-color="#409eff" -->
          <template v-if="menuExitRole">
            <template v-for="(menu, idx) in meuns">
              <!-- 第一级菜单，不带第二层的，也带链接的 不是外链-->
              <template
                v-if="
                  menu.parentId == 0 &&
                  !menu.outJoin &&
                  (menu.href != null || menu.href != '') &&
                  menu.type == 2 &&
                  menu.children.length == 0 &&
                  menu.role.indexOf(role) >= 0
                "
              >
                <el-menu-item @click="jumpTo(menu)" :key="idx" :index="menu.perName">
                  <i :class="menu.icon"></i>
                  <span slot="title">{{ menu.perName }}</span>
                </el-menu-item>
              </template>

              <!-- 第一级菜单，不带第二层的，是外链-->
              <template
                v-if="
                  menu.parentId == 0 &&
                  menu.outJoin &&
                  (menu.href != null || menu.href != '') &&
                  menu.type == 2 &&
                  menu.children.length == 0
                "
              >
                <el-link
                  :underline="false"
                  target="_blank"
                  style="color: white; width: 100%; margin: 0"
                  :key="idx"
                  :href="menu.href"
                  v-if="menu.role.indexOf(role) >= 0"
                >
                  <el-menu-item :index="menu.perName">
                    <i :class="menu.icon"></i>
                    {{ menu.perName }}
                    <span style="font-size: 10px">(外链)</span>
                  </el-menu-item>
                </el-link>
              </template>

              <!-- 二级菜单，第一层不带链接的 -->
              <el-submenu
                v-if="
                  menu.parentId == 0 && menu.type == 1 && menu.role.indexOf(role) >= 0
                "
                :key="idx"
                :index="menu.perName"
              >
                <template slot="title">
                  <i :class="menu.icon"></i>
                  <span @click="jumpTo(menu)" slot="title">{{ menu.perName }}</span>
                </template>

                <template v-for="(chmenu, idxx) in menu.children">
                  <!-- 内链 -->
                  <template v-if="!chmenu.outJoin && chmenu.role.indexOf(role) >= 0">
                    <el-menu-item
                      :key="idxx"
                      @click="jumpTo(chmenu)"
                      :index="chmenu.perName"
                    >
                      <i :class="chmenu.icon"></i>
                      {{ chmenu.perName }}
                    </el-menu-item>
                  </template>

                  <!-- 外链 -->
                  <template v-else>
                    <el-link
                      :underline="false"
                      target="_blank"
                      style="color: white"
                      :key="idxx"
                      :href="chmenu.href"
                      v-if="chmenu.role.indexOf(role) >= 0"
                    >
                      <el-menu-item :index="chmenu.perName">
                        <i :class="chmenu.icon"></i>
                        {{ chmenu.perName }}
                        <span style="font-size: 10px">(外链)</span>
                      </el-menu-item>
                    </el-link>
                  </template>
                </template>
              </el-submenu>
            </template>
          </template>

          <template v-else>
            <template v-for="(menu, idx) in meuns">
              <!-- 第一级菜单，不带第二层的，也带链接的 不是外链-->
              <template
                v-if="
                  menu.parentId == 0 &&
                  !menu.outJoin &&
                  (menu.href != null || menu.href != '') &&
                  menu.type == 2 &&
                  menu.children.length == 0
                "
              >
                <el-menu-item @click="jumpTo(menu)" :key="idx" :index="menu.perName">
                  <i :class="menu.icon"></i>
                  <span slot="title">{{ menu.perName }}</span>
                </el-menu-item>
              </template>

              <!-- 第一级菜单，不带第二层的，是外链-->
              <template
                v-if="
                  menu.parentId == 0 &&
                  menu.outJoin &&
                  (menu.href != null || menu.href != '') &&
                  menu.type == 2 &&
                  menu.children.length == 0
                "
              >
                <el-link
                  :underline="false"
                  target="_blank"
                  style="color: white; width: 100%; margin: 0"
                  :key="idx"
                  :href="menu.href"
                >
                  <el-menu-item :index="menu.perName">
                    <i :class="menu.icon"></i>
                    {{ menu.perName }}
                    <span style="font-size: 10px">(外链)</span>
                  </el-menu-item>
                </el-link>
              </template>

              <!-- 二级菜单，第一层不带链接的 -->
              <el-submenu
                v-if="menu.parentId == 0 && menu.type == 1"
                :key="idx"
                :index="menu.perName"
              >
                <template slot="title">
                  <i :class="menu.icon"></i>
                  <span @click="jumpTo(menu)" slot="title">{{ menu.perName }}</span>
                </template>

                <template v-for="(chmenu, idxx) in menu.children">
                  <!-- 内链 -->
                  <template v-if="!chmenu.outJoin">
                    <el-menu-item
                      :key="idxx"
                      @click="jumpTo(chmenu)"
                      :index="chmenu.perName"
                    >
                      <i :class="chmenu.icon"></i>
                      {{ chmenu.perName }}
                    </el-menu-item>
                  </template>

                  <!-- 外链 -->
                  <template v-else>
                    <el-link
                      :underline="false"
                      target="_blank"
                      style="color: white"
                      :key="idxx"
                      :href="chmenu.href"
                    >
                      <el-menu-item :index="chmenu.perName">
                        <i :class="chmenu.icon"></i>
                        {{ chmenu.perName }}
                        <span style="font-size: 10px">(外链)</span>
                      </el-menu-item>
                    </el-link>
                  </template>
                </template>
              </el-submenu>
            </template>
          </template>
        </el-menu>
      </el-aside>
      <el-container class="center_first">
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>
<style scoped></style>
<script>
let _this = {};
import {getMenus} from "@/menu.js";

let config = require("@/config.js").config;

export default {
  data() {
    return {
      bg: {
        backgroundImage: "url(" + require("@/assets/nav-bg.png") + ")",
      },
      loading: false,
      dialog: {
        width: "40%",
        title: "信息修改",
        visible: false,
        modal: true,
      },
      left_lager_head_span: 4,
      showBtnSpan: 4,
      user: {
        id: "",
        newPwd: "",
        oldPwd: "",
      },
      role: "user",

      isCollapse: false,
      asideWidth: "200px",
      loginUserRole: "",
      loginUser: "",
      blogName: "办公OA",

      defaultActive: "",
      defaultOpeneIds: [],
      meuns: [],
      menCookieKey: {
        defaultActive: "meun-default-active",
        defaultOpeneIds: "meun-default-openeds",
        currentPath: "menu-currentPath",
        tab: "tabListEvent",
        defaultOpeneIdsEvent: "defaultOpeneIdsEvent",
        menuList: "menuList",
      },
      menuExitRole: true,
      URL: {
        menu: "/permission/userMenuTree",
        blog: "http://thisforyou.cn:180/blog/",
        note: "http://thisforyou.cn:180/",
        updatePwd: "/user/updatePwd",
        logOut: "/logout",
      },
    };
  },

  components: {},
  created() {
    this.menuExitRole = config.menuExitRole;
    _this = this;
    this.settingMenu();
    this.getLoginUser();
  },
  mounted() {
    this.getdefaultActive();

    // 监听tab栏的点击
    this.$EventBus.$on(this.menCookieKey.defaultOpeneIdsEvent, (defaultOpeneIds) => {
      this.defaultOpeneIds = defaultOpeneIds;
      if (defaultOpeneIds.length > 1) {
        this.defaultActive = defaultOpeneIds[1];
      } else {
        this.defaultActive = defaultOpeneIds[0];
      }
    });

    // 获取浏览器可视区域高度
    // let clientHeight = `${document.documentElement.clientHeight}`;
    // this.mainHeight = clientHeight - 60;
  },
  watch: {
    $route(to, from) {
      this.tabPush(to);
      this.$cookies.set(this.menCookieKey.currentPath, to.fullPath, "30d");
    },
  },
  methods: {
    tabPush(to) {
      let tab = this.$cookies.get(this.menCookieKey.tab);
      let arr = [null, undefined, ""];
      let index = arr.indexOf(tab);
      if (index >= 0) {
        tab = [];
      } else {
        tab = JSON.parse(tab);
      }
      let newTab=[];
      let isExit = false;
      for (var i = 0; i < tab.length; i++) {
        let meun = tab[i];
        if (meun.name == to.name) {
          isExit = true;
          meun['path']=to.fullPath;
        }
        newTab.push(meun);
      }
      if (!isExit) {
        let m = {
          path: to.fullPath,
          name: to.name,
        };
        newTab.push(m);
      }
      this.$cookies.set(this.menCookieKey.tab, JSON.stringify(newTab), "30d");
      this.$EventBus.$emit(this.menCookieKey.tab, newTab);
    },
    toClient() {
      this.$router.push({
        path: "/",
      });
    },
    // 展开指定的 sub-menu
    handOpen() {},
    jumpTo(menu) {
      if (menu.type == 1) {
        return;
      }
      if (!menu.outJoin) {
        if (menu.router == "") {
          this.$warning("💪 开发中");
          return;
        }
        this.$router.push(menu.router);
      }
    },
    getMeunList(callback) {
      getMenus(callback);
    },
    settingMenu() {
      this.getMeunList((menus) => {
        this.meuns=menus;
        let active = this.$cookies.get(this.menCookieKey.defaultActive);
        let arr = [undefined, null, "null"];
        let to = {};
        if (arr.indexOf(active) >= 0) {
          let menu = this.meuns[0];
          if (menu.parentId == 0 && menu.children.length > 0) {
            this.defaultActive = menu.children[0].perName;
            this.$router.push(menu.children[0].router);
            to = {
              name: menu.children[0].perName,
              path: menu.children[0].router,
            };
          } else {
            this.defaultActive = menu.perName;
            to = {
              name: menu.perName,
              path: "",
            };
          }
          this.tabPush(to);
        }
      });
    },
    updatePwd() {
      this.dialog.visible = true;
      // this.$warning("努力💪君，开发中");
    },

    saveCargoBtn() {
      let url = this.URL.updatePwd;
      this.$put(url, this.user).then((res) => {
        if (res.R) {
          this.$success("修改成功，需要重新登陆");
          this.$router.push(config.loginPage);
        }
      });
    },
    cancelCargoBtn() {
      this.dialog.visible = false;
    },
    showHide() {
      if (!this.isCollapse) {
        this.isCollapse = true;
        this.left_lager_head_span = 1;
        this.showBtnSpan = 1;
        this.asideWidth = "64px";
      } else {
        this.left_lager_head_span = 4;
        this.showBtnSpan = 4;
        this.isCollapse = false;
        this.asideWidth = "200px";
      }
    },
    logout() {
      this.$removeToken();
      //this.$cookies.remove("defaultActive");
      //this.$cookies.remove("defaultOpenIds");
      this.$router.push({
        path: "/login",
      });
      this.$post(this.URL.logOut, {}).then((res) => {});
    },
    menuSelect(index, indexPath) {
      this.defaultActive = index;
      this.$cookies.set(this.menCookieKey.defaultActive, index, "30d");
      this.$cookies.set(this.menCookieKey.defaultOpeneIds, indexPath.join(","), "30d");
    },
    getdefaultActive() {
      let openIds = this.$cookies.get(this.menCookieKey.defaultOpeneIds);
      if (openIds != undefined && openIds != null) {
        this.defaultOpeneIds = openIds.split(",");
      }
      let active = this.$cookies.get(this.menCookieKey.defaultActive);
      if (active != undefined && active != null) {
        this.defaultActive = active;
      }
      // 当前路由页面
      let currentPath = this.$cookies.get(this.menCookieKey.currentPath);
      if (currentPath == null || currentPath == undefined || currentPath == "undefined") {
        return;
      }
      this.$router.push(currentPath);
    },
    getLoginUser() {
      let user = this.$getUser();
      if (user != null && user != undefined && user != "") {
        this.loginUser = user.loginName;
        this.role = user.role;
      }
    },
  },
};
</script>
<style lang="less" scope>
.wrapper {
  height: 100vh;

  .main_container,
  .center_first {
    height: 92vh !important;
    // display: flex;
    // flex-direction: column;
  }

  .el-submenu__icon-arrow {
    font-weight: 800;
    color: rgba(0, 0, 0, 0.65);
  }

  .el-main {
    padding-top: 0;
    padding: 0 !important;
  }

  .el-aside {
    height: 100%;
    color: rgba(0, 0, 0, 0.65);
    text-align: left;
  }

  .el-header {
    height: 8vh !important;
    line-height: 8vh;
    color: white;
    border-bottom: 1px solid #ebebeb;
    background: #1890ff;
  }

  .el-menu-vertical-demo {
    flex-shrink: 0;
  }

  .el-menu--collapse,
  .el-menu-vertical-demo {
    height: 100%;
    overflow-x: hidden !important;
  }

  .el-menu {
    border-right: none !important;
    overflow-x: hidden !important;
  }

  .right_operate_main {
    display: flex;
    color: white;
    text-align: right !important;
    float: right;

    .outSys,
    .right_operate {
      padding: 0 10px;
      height: 8vh;
    }

    .client_link {
      padding: 0 10px;
      height: 8vh;
      cursor: pointer;
    }

    .outSys:hover,
    .right_operate:hover,
    .client_link:hover {
      background-color: rgb(103, 179, 252);
    }

    .outSys .el-button {
      color: white;
      font-size: 15px;
    }

    .el-dropdown-link {
      color: white;
      height: 8vh;
      cursor: pointer;
      width: 100%;
      display: flex;
      align-items: center;

      .user {
        width: 25px;
        height: 25px;
        border-radius: 30px;
        position: relative;
        background: white;
        margin-right: 10px;
      }

      .userName {
        position: relative;
        font-size: 15px;
        position: relative;
        font-weight: 500;
        color: white;
      }
    }
  }

  .left-lager-head {
    line-height: 8vh;
    display: flex;
    align-content: center;
    .logo {
      width: 50px;
      height: 50px;
      position: relative;
      top: 5px;
    }

    .titleMsg {
      font-size: 18px;
      font-weight: 500;
      color: #fff !important;
      position: relative;
      padding-left: 10px;
    }
  }

  .left-mini-head {
    line-height: 8vh;

    .logo {
      width: 50px;
      height: 50px;
      position: relative;
      top: 5px;
    }

    .titleMsg {
      color: #fff !important;
      position: relative;
      padding-left: 10px;
    }
  }

  // 7----------------------------------------------------
  // 左侧菜单栏的 一整块背景颜色
  .el-menu-vertical-demo,
  .el-aside {
    //background-color: #409EFF;//rgb(37, 49, 68);
    box-shadow: 0 10px 8px rgba(0, 0, 0, 0.15);
    transition: background 0.3s, width 0.3s cubic-bezier(0.2, 0, 0, 1) 0s;
  }

  .ul li.el-menu-item {
    color: black;
    //background-color: rgb(23, 33, 49);
  }

  //   // 展开的二级菜单背景色
  //   .el-menu--inline li.el-menu-item {
  //     padding-left: 40px !important;
  //     //background-color: #409EFF;// rgb(23, 33, 49);
  //   }

  // 被选中的菜单颜色
  .el-menu--inline li.is-active,
  .el-menu-item.is-active {
    border-right: 2px solid #1890ff;
    color: #1890ff; //#409eff;
    background-color: rgb(231, 247, 255);
  }

  // .el-submenu__title {
  // border-right: none !important;
  //background-color: rgb(37, 49, 68) !important;
  // }

  //   // 鼠标悬停颜色 一级菜单：有二级菜单的
  //   .el-submenu__title:hover {
  //     background-color: rgb(37, 49, 68);
  //   }

  //   // 二级菜单悬停颜色
  //   .el-menu--inline li.el-menu-item:hover {
  //     background-color: rgb(12, 14, 19); // 深
  //     border-right: 4px solid #409eff;
  //     color: #409eff !important;
  //   }

  //   // 一级菜单悬停颜色 就是没有二级菜单的一级连接菜单
  //   .el-menu-item:hover {
  //     background-color: rgb(12, 14, 19); // 深
  //     border-right: 4px solid #409eff;
  //     color: #409eff !important;
  //   }
  // }

  // // 这个是菜单缩小后的
  // .el-menu--vertical {
  //   .el-menu--popup {
  //     background-color: rgb(37, 49, 68) !important;
  //   }
  //   .el-menu-item.is-active {
  //     border-right: 4px solid #409eff;
  //     color: #409eff;
  //     background-color: rgb(12, 14, 19);
  //   }
  //   li.el-menu-item:hover {
  //     background-color: rgb(12, 14, 19); // 深
  //     border-right: 4px solid #409eff;
  //     color: #409eff !important;
  //   }
}

// .el-link--inner {
//   width: 100%;
// }
</style>
