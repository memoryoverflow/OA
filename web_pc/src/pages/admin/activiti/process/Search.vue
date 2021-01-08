<template slot="search">
  <div>
    <el-form-item label="名称">
      <el-input size="mini" v-model="params.name" placeholder="名称"></el-input>
    </el-form-item>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        params: {
          name: ""
        }
      };
    }
  };
</script>
<style scoped>
</style>
