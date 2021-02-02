<template>
  <main-frame :location="location">
    <template slot="mainFrame">
      <div class="main-content">
        <div class="input_">
          <el-input placeholder="请输入内容">
            <template slot="append">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </template>
          </el-input>
        </div>
      </div>
    </template>
  </main-frame>
</template>

<script>
export default {
  data() {
    return {
      location: "首页"
    };
  }
};
</script>

<style scoped>
.main-content {
  display: flex;
  height: 100%;
  justify-content: center;
  align-items: center;
}
.input_{
    width: 80%;
}
</style>
