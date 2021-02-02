  <template slot="search">
  <div class="demo-input-size">
    <label>
      模糊搜索
      <el-input size="mini" clearable="true" placeholder="请输入内容" v-model="params.name"></el-input>
    </label>
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
.demo-input-size .el-input {
  width: 150px !important;
}
.demo-input-size label {
  margin: 0 5px;
}
.demo-input-size {
  color: #606266;
  font-size: 12px;
}
</style>
