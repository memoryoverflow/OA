<style>
  ::-webkit-scrollbar {
    /*滚动条整体样式*/
    width: 2px !important; /*高宽分别对应横竖滚动条的尺寸*/
    height: 5px !important;
    background: #ffffff !important;
    cursor: pointer !important;
  }

  ::-webkit-scrollbar-thumb {
    /*滚动条里面小方块*/
    border-radius: 5px !important;
    -webkit-box-shadow: inset 0 0 2px rgba(240, 240, 240, 0.5) !important;
    background: rgba(63, 98, 131, 0.8) !important;
    cursor: pointer !important;
  }

  ::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    -webkit-box-shadow: inset 0 0 2px rgba(240, 240, 240, 0.5) !important;
    border-radius: 0 !important;
    background: rgba(240, 240, 240, 0.5) !important;
    cursor: pointer !important;
  }

  .wrapper {
    width: 100%;
    height: 100vh;
  }

  .showBtn {
    background: none;
    border: none;
    font-size: 20px;
    color: gray;
    cursor: pointer;
  }

  .el-main {
    padding-top: 0;
    background: rgb(246, 246, 246);
  }

  .router {
    height: 100%;
  }
</style>
<template>
  <el-container class="wrapper">
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
          <el-button size="mini" type="primary" @click="saveCargoBtn"
          >确 定
          </el-button
          >
        </div>
      </template>
    </yj-dialog>

    <!-- <el-aside :width="asideWidth"> -->
    <el-menu
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      :unique-opened="true"
      :collapse-transition="true"
      :router="false"
      text-color="#fff"
      :default-active="defaultActive"
      :default-openeds="defaultOpeneds"
      active-text-color="#409eff"
      :menu-trigger="menuTrigger"
      @select="menuSelect"
      @open="handOpen"
    >
      <div v-if="!isCollapse" class="left-lager-head">
        <img class="logo" src="@/assets/kafei_white.png"/>
        <span class="titleMsg">{{ blogName }}</span>
      </div>
      <div v-else class="left-mini-head">
        <img class="logo" src="@/assets/kafei_white.png"/>
      </div>

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
                :href="chmenu.url"
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
    </el-menu>
    <!-- </el-aside> -->
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="18">
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
          <el-col style="text-align: center" :span="3">
            <!-- <el-link :href="URL.note" target="_blank" type="primary">访问笔记</el-link>&nbsp;
            <el-link :href="URL.blog" target="_blank" type="primary">访问博客</el-link>-->
          </el-col>
          <el-col style="text-align: center" :span="3">
            <el-dropdown trigger="click">
              <span class="el-dropdown-link">
                <img class="user" src="@/assets/avear.gif"/>
                <span class="userName">{{ loginUser }}</span>
                <i class="el-icon-caret-bottom el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  icon="el-icon-turn-off"
                  @click.native="updatePwd"
                >修改密码
                </el-dropdown-item
                >
                <el-dropdown-item icon="el-icon-turn-off" @click.native="logout"
                >退出
                </el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <div style="height: 100vh">
        <router-view/>
      </div>
    </el-container>
  </el-container>
</template>
<style scoped>
  .userName {
    position: relative;
    top: -10px;
  }
</style>
<script>
  let _this = {};
  import menuList from "@/menu.js";

  export default {
    data() {
      return {
        dialog: {
          width: "40%",
          title: "信息修改",
          visible: false,
          modal: true,
        },

        user: {
          id: "",
          newPwd: "",
          oldPwd: "",
        },

        isCollapse: false,
        asideWidth: "300px",
        mainHeight: 600,
        menuTrigger: "",
        loginUserRole: "",
        loginUser: "",
        blogName: "系统后台",
        defaultActive: "",
        defaultRoute: "/admin/index",
        defaultOpeneds: [],
        meuns: [],
        loading: false,
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
      _this = this;
      _this.getMenu();
      // _this.getLoginUser();
    },
    mounted() {
      this.getdefaultActive();
      // 获取浏览器可视区域高度
      let clientHeight = `${document.documentElement.clientHeight}`;
      this.mainHeight = clientHeight - 60;
    },
    watch: {
      $route(to, from) {
      },
    },
    methods: {
      handOpen(key, keyPath) {
      },
      jumpTo(menu) {
        if (menu.type == "目录") {
          return;
        }
        if (!menu.outJoin) {
          if (menu.router == "") {
            this.$warning("💪 开发中");
            return;
          }
          this.$router.push(menu.router);
        }else {

        }
      },
      getMenu() {
        // this.meuns = menuList;
        let active = this.$cookies.get("defaultActive");
        if (active == undefined || active == null) {
          this.defaultActive = menuList[0].perName;
        }

        // 获取菜单列表
        this.loading = true;
        var menus = this.$cookies.get("menus");
        if (
          menus != undefined &&
          menus != "undefined" &&
          menus != null &&
          menus != ""
        ) {
          this.loading = false;
          _this.meuns = JSON.parse(menus);
          let active = this.$cookies.get("defaultActive");
          if (active != undefined && active != null) {
            this.defaultActive = active;
          } else {
            this.defaultActive = menus.perName;
          }
        } else {
          this.$get(this.URL.menu, {}).then((res) => {
            this.loading = false;
            this.meuns = res.data;
            this.$cookies.set("menus", JSON.stringify(res.data), "7d");
            let active = this.$cookies.get("defaultActive");
            if (active != undefined && active != null) {
              this.defaultActive = active;
            } else {
              this.defaultActive = res.data[0].perName;
            }
          });
        }
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
            this.$router.push("/login");
          }
        });
      },
      cancelCargoBtn() {
        this.dialog.visible = false;
      },
      showHide() {
        if (!this.isCollapse) {
          this.isCollapse = true;
          this.asideWidth = "64px";
        } else {
          this.isCollapse = false;
          this.asideWidth = "200px";
        }
      },
      logout() {
        this.$removeToken();
        this.$cookies.remove("defaultActive");
        this.$cookies.remove("defaultOpenIds");
        this.$router.push({
          path: "/login",
        });
        this.$post(this.URL.logOut, {}).then((res) => {
        });
      },
      menuSelect(index, indexPath) {
        this.defaultActive = index;
        this.$cookies.set("defaultActive", index, "30d");
        this.$cookies.set("defaultOpenIds", indexPath.join(","), "30d");
      },
      getdefaultActive() {
        let openIds = this.$cookies.get("defaultOpenIds");
        if (openIds != undefined && openIds != null) {
          this.defaultOpeneds = openIds.split(",");
        }

        let active = this.$cookies.get("defaultActive");
        console.log(openIds);
        console.log(active);
        if (active != undefined && active != null) {
          this.defaultActive = active;
        }
        // this.$router.push(this.defaultRoute);
      },
      getLoginUser() {
      },
    },
  };
</script>
<style lang="less" scope>
  .wrapper {
    .el-aside {
      color: #fff;
      text-align: left;
    }

    .el-header {
      height: 50px !important;
      line-height: 50px;
      border-bottom: 1px solid #ebebeb;
    }

    .el-menu-vertical-demo {
      flex-shrink: 0;
    }

    .el-menu-vertical-demo:not(.el-menu--collapse) {
      width: 200px;
      min-height: 400px;
    }

    .el-menu {
      border-right: none !important;
      overflow-x: hidden;
    }
  }

  .el-dropdown-link {
    cursor: pointer;

    .user {
      width: 40px;
      position: relative;
      top: 5px;
    }

    i {
      position: relative;
      top: -10px;
    }
  }

  .left-lager-head {
    text-align: center;
    background: rgb(65, 72, 92);
    line-height: 3em;

    .logo {
      width: 10%;
      vertical-align: middle;
      position: relative;
      top: -3px;
    }

    .titleMsg {
      font-size: 14px;
      font-weight: 500;
      color: #fff !important;
      position: relative;
      padding-left: 10px;
    }
  }

  .left-mini-head {
    padding-top: 5px;
    text-align: center;

    .logo {
      width: 50%;
    }

    .titleMsg {
      color: #fff !important;
      position: relative;
      top: -10px;
      padding-left: 10px;
    }
  }

  .wrapper {
    .el-menu-vertical-demo,
    .el-aside,
    .el-menu-item {
      background-color: rgb(37, 49, 68);
    }
  }

  .wrapper {
    .ul li.el-menu-item {
      color: #409eff;
      background-color: rgb(23, 33, 49);
    }

    .el-menu--inline li.el-menu-item {
      padding-left: 40px !important;
      background-color: rgb(23, 33, 49);
    }

    .el-menu--inline li.is-active,
    .el-menu-item.is-active {
      border-right: 4px solid #409eff;
      color: #409eff;
      background-color: rgb(12, 14, 19);
    }

    .el-submenu__title {
      border-right: none !important;
      background-color: rgb(37, 49, 68) !important;
    }

    .el-submenu__title:hover {
      background-color: rgb(37, 49, 68);
    }

    .el-menu--inline li.el-menu-item:hover {
      background-color: rgb(12, 14, 19); // 深
      border-right: 4px solid #409eff;
      color: #409eff !important;
    }

    .el-menu-item:hover {
      background-color: rgb(12, 14, 19); // 深
      border-right: 4px solid #409eff;
      color: #409eff !important;
    }
  }

  .el-menu--vertical {
    .el-menu--popup {
      background-color: rgb(37, 49, 68) !important;
    }

    .el-menu-item.is-active {
      border-right: 4px solid #409eff;
      color: #409eff;
      background-color: rgb(12, 14, 19);
    }

    li.el-menu-item:hover {
      background-color: rgb(12, 14, 19); // 深
      border-right: 4px solid #409eff;
      color: #409eff !important;
    }
  }

  .el-link--inner {
    width: 100%;
  }
</style>
