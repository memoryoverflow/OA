<template slot="search">
  <el-form-item label="姓名">
    <el-input
      clearable
      size="mini"
      clearable
      v-model="params.name"
      placeholder="请输入需要搜索的姓名"
    ></el-input>
  </el-form-item>
</template>
<script>
  export default {
    data() {
      return {
        params: {
          name: "",
        },
      };
    },
  };
</script>
<style scoped>
</style>
