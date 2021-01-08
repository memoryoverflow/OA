<template slot="search">
  <div>
    <el-form-item label="名称">
      <el-input size="mini" v-model="params.roleName" placeholder="角色名称"></el-input>
    </el-form-item>
    <el-form-item label="名称">
      <el-input size="mini" v-model="params.roleCode" placeholder="角色编码"></el-input>
    </el-form-item>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        params: {
          roleName: "",
          roleCode: ""
        }
      };
    }
  };
</script>
<style scoped>
</style>
