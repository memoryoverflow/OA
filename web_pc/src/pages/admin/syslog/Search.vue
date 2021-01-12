<template slot="search">
  <div>
    <el-form-item label="操作用户">
      <el-input clearable="" size="mini" v-model="params.operName" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item label="操作类型">
      <el-select size="mini" v-model="params.type" placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item"
          :label="item"
          :value="item">
        </el-option>
      </el-select>
    </el-form-item>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        options: ["正常", "异常"],
        params: {
          operUser: "",
          type: ""
        }
      };
    }
  };
</script>
<style scoped>
</style>
