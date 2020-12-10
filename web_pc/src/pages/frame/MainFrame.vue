<template>
  <div class="main-frame">
    <div class="lactions">{{location}}</div>
    <el-container
      :style="{'height':contentHeight+'px','overflow-y': 'scroll','overflow-x':'hidden'}"
    >
      <div class="top">
        <!-- 位置信息 -->
        <slot name="mainFrame"></slot>
      </div>
    </el-container>
  </div>
</template>
<script>
let _this = {};
export default {
  data() {
    return {
      contentHeight: 500
    };
  },
  props: ["location"],
  mounted() {
    this.getWindownHeight();
    window.onresize = () => {
      return (() => {
        this.getWindownHeight();
      })();
    };
  },
  methods: {
    getWindownHeight() {
      let winHeight = document.body.clientHeight;
      let contentHeight = winHeight - 50 - 32;
      this.contentHeight = contentHeight;
      console.log(contentHeight);
    }
  }
};
</script>
<style lang="less" scope>
.main-frame {
  .el-container {
    width: 100%;
    height: 100%;
    padding: 0 20px;
    margin: 0 auto;
  }
}
.lactions {
  text-align: left;
  background-color: rgb(248, 244, 244) !important;
  line-height: 32px;
  font-size: 12px;
  padding: 0 23px;
  margin: 0 auto;
  color: rgb(137, 154, 178);
  /* padding: 10px 0; */
}
.top {
  width: 100%;
  height: 100%;
  display: flex;
  /* height: 100%; */
  flex-direction: column;
}
</style>
