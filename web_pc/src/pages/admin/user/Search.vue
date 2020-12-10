<template slot="search">
  <div>
    <el-form-item label="姓名">
      <el-input
        size="mini"
        clearable
        v-model="params.name"
        placeholder="请输入姓名"
      ></el-input>
    </el-form-item>

    <el-form-item label="电话">
      <el-input
        size="mini"
        clearable
        v-model="params.name"
        placeholder="请输入电话"
      ></el-input>
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input
        size="mini"
        clearable
        v-model="params.email"
        placeholder="请输入邮箱"
      ></el-input>
    </el-form-item>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        params: {
          name: "",
          phone:"",
          email:""
        },
      };
    },
  };
</script>
<style scoped>
</style>
