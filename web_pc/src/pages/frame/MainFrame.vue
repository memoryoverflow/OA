<template>
  <div class="main-frame">
    <div class="lactions">
      <div
        @click="jumpTo(tab)"
        class="tab_item"
        size="mini"
        type="text"
        v-for="(tab, idx) in tabs"
        :key="idx"
      >
        <div :class="{ activity: activity == tab.name }" class="text">{{ tab.name }}</div>
        <div class="cancel">x</div>
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
    };
  },
  props: ["location"],
  created() {
    this.$EventBus.$on(this.menCookieKey.tab, (data) => {
      this.getTabs();
      this.activity = this.$cookies.get(this.menCookieKey.defaultActive);
    });
  },
  mounted() {
    this.getTabs();
    this.activity = this.$cookies.get(this.menCookieKey.defaultActive);
    this.getWindownHeight();
    window.onresize = () => {
      return (() => {
        this.getWindownHeight();
      })();
    };
  },
  methods: {
    jumpTo(tab) {
      this.$cookies.set(this.menCookieKey.defaultActive, tab.name, "30d");
      this.activity = tab.name;
      let defaultOpeneIds = [];

      getMenus((menuList) => {

        if (menuList != null && menuList != undefined) {
          A: for (const m of menuList) {
            if (m.perName == tab.name) {
              defaultOpeneIds.push(tab.name);
            } else {
              let childs = m.children;
              for (const c of childs) {
                if (c.perName == tab.name) {
                  defaultOpeneIds.push(m.perName);
                  defaultOpeneIds.push(tab.name);
                  break A;
                }
              }
            }
          }
          this.$cookies.set(
            this.menCookieKey.defaultOpeneIds,
            defaultOpeneIds.join(","),
            "30d"
          );
          this.$EventBus.$emit(this.menCookieKey.defaultOpeneIdsEvent, defaultOpeneIds);
        }

        this.$router.push(tab.path);
      });
    },
    getTabs() {
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
    getWindownHeight() {
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

.lactions {
  text-align: left;
  //background-color: rgb(248, 244, 244) !important;
  line-height: 20px;
  font-size: 12px;
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

.lactions .tab_item {
  display: flex;
  border: 1px solid #c3c9d6;
  flex-direction: row;
  padding: 2px 5px;
  color: #000000a6;
  font-size: 14px;
  border-radius: 2px;
  margin: 0 3px;
}

.tab_item .cancel {
  padding-left: 5px;
  text-align: right;
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
