<template>
  <div class="main-frame">
    <div class="locations">
      <div
        @click="jumpTo(tab)"
        class="tab_item"
        size="mini"
        type="text"
        v-for="(tab, idx) in tabs"
        :key="idx"
      >
        <div :class="{ activity: activity == tab.name }" class="text">{{ tab.name }}</div>
        <div class="cancel" @click="closeTab(tab)" v-if="tab.name!='首页'">x</div>
      </div>
    </div>
    <el-container
      :style="{
        height: contentHeight + 'px',
        'overflow-y': 'scroll',
        'overflow-x': 'hidden',
      }"
    >
      <div class="top">
        <!-- 位置信息 -->
        <slot name="mainFrame"></slot>
      </div>
    </el-container>
  </div>
</template>
<script>
import {getMenus} from "@/menu.js";

export default {
  data() {
    return {
      contentHeight: 500,
      menCookieKey: {
        defaultActive: "meun-default-active",
        defaultOpeneIds: "meun-default-openeds",
        currentPath: "menu-currentPath",
        tab: "tabListEvent",
        defaultOpeneIdsEvent: "defaultOpeneIdsEvent",
        menuList: "menuList",
      },
      activity: "",
      tabs: [],
      IndexMenu: {name: "首页", path: "/admin/index", isHome: true}
    };
  },
  props: ["location"],
  created() {
    this.listenerTabMenus();
    this.listenerDefault();
  },
  mounted() {
    // 取缓存
    this.getTabMenus();
    this.activity = this.$cookies.get(this.menCookieKey.defaultActive);

    // 计算表格高度
    this.getWindowHeight();
    window.onresize = () => {
      return (() => {
        this.getWindowHeight();
      })();
    };
  },
  methods: {
    listenerTabMenus() {
      this.$EventBus.$on(this.menCookieKey.tab, (data) => {
        this.getTabMenus();
        this.activity = this.$cookies.get(this.menCookieKey.defaultActive);
      });
    },
    listenerDefault() {
      this.$EventBus.$on(this.menCookieKey.defaultActive, (data) => {
        this.activity = data;
      });
    },

    closeTab(tab) {
      if (tab.name === "首页") {
        return;
      }

      let tabs = JSON.parse(this.$cookies.get(this.menCookieKey.tab));
      let preTab = '';
      let newTabs = [];
      for (const key in tabs) {
        let ta = tabs[key];
        if (ta.name !== tab.name) {
          newTabs.push(ta);
          preTab = ta
        }
      }
      this.tabs = newTabs;
      if (preTab == "") {
        preTab = this.IndexMenu;
      }
      this.$cookies.set(this.menCookieKey.tab, JSON.stringify(newTabs));
      this.$cookies.remove(this.menCookieKey.defaultActive);
      setTimeout(() => {
        this.jumpTo(preTab);
      }, 50)
    },
    // 点击tab栏的时候
    jumpTo(tab) {
      // 更新缓存
      this.$cookies.set(this.menCookieKey.defaultActive, tab.name, "30d");

      this.activity = tab.name;

      let defaultOpenIds = [];

      // 找到要展开的菜单
      getMenus((menuList) => {
        if (menuList != null && menuList != undefined) {
          A: for (const m of menuList) {
            if (m.perName == tab.name) {
              defaultOpenIds.push(tab.name);
            } else {
              let children = m.children;
              for (const c of children) {
                if (c.perName == tab.name) {
                  defaultOpenIds.push(m.perName);
                  defaultOpenIds.push(tab.name);
                  break A;
                }
              }
            }
          }
          // 更新展开的菜单缓存
          this.$cookies.set(
            this.menCookieKey.defaultOpeneIds,
            defaultOpenIds.join(","),
            "30d"
          );
          // 通知左侧菜单要展开的
          this.$EventBus.$emit(this.menCookieKey.defaultOpeneIdsEvent, defaultOpenIds);
        }
        this.$router.push(tab.path);
      });
    },
    getTabMenus() {
      let tab = this.$cookies.get(this.menCookieKey.tab);
      let arr = [null, undefined, ""];
      let tabList = [];
      if (arr.indexOf(tab) < 0) {
        tab = JSON.parse(tab);
        for (const key in tab) {
          if (tab.hasOwnProperty.call(tab, key)) {
            const element = tab[key];
            tabList.push(element);
          }
        }
        this.tabs = tabList;
      }
    },
    getWindowHeight() {
      let winHeight = document.body.clientHeight;
      let contentHeight = winHeight - 35 - 70;
      this.contentHeight = contentHeight;
      console.log(contentHeight);
    },
  },
};
</script>
<style lang="less" scope>
.main-frame {
  .el-container {
    width: 100%;
    height: 100%;
    background: #f6f6f6;
    margin: 0 auto;
    padding: 10px 10px 0 10px;
  }
}

.activity {
  color: #1890ff;
}

.locations {
  text-align: left;
  //background-color: rgb(248, 244, 244) !important;
  line-height: 20px;
  padding: 4px 8px;
  margin: 0 auto;
  color: rgb(137, 154, 178);
  background: white !important;
  border-bottom: 1px solid #ebebeb;
  border-left: 1px solid #ebebeb;
  display: flex;
  flex-direction: row;
  white-space: nowrap;
  /* padding: 10px 0; */
}

.locations .tab_item {
  display: flex;
  border: 1px solid #c4c9d4;
  flex-direction: row;
  padding: 2px 5px;
  color: #000000a6;
  margin: 0 3px;

}

.tab_item .cancel {
  padding-left: 5px;
  text-align: right;
}

.tab_item .text {
  font-size: 10px !important;
}

.cancel:hover {
  color: red;
}

.tab_item:hover {
  cursor: pointer;

  .text {
    color: #1890ff;
  }
}

.top {
  width: 100%;
  height: 100%;
  display: flex;
  /* height: 100%; */
  flex-direction: column;
}
</style>
