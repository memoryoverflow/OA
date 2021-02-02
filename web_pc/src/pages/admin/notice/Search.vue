<template slot="search">
  <div>
    <el-form-item label="名称">
      <el-input size="mini" clearable="true" v-model="params.title" placeholder="公告名称"></el-input>
    </el-form-item>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        params: {
          title: "",
        }
      };
    }
  };
</script>
<style scoped>
</style>
