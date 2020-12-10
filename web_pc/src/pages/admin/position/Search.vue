<template slot="search">
  <div>
    <el-form-item label="岗位编码">
      <el-input
        size="mini"
        clearable
        v-model="params.positionCode"
        placeholder="请输入岗位编码"
      ></el-input>
    </el-form-item>

    <el-form-item label="岗位名称">
      <el-input
        size="mini"
        clearable
        v-model="params.positionName"
        placeholder="请输入岗位名称"
      ></el-input>
    </el-form-item>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        params: {
          positionName: "",
          positionCode:""
        },
      };
    },
  };
</script>
<style scoped>
</style>
