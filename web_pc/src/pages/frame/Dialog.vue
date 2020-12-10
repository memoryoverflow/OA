<template>
  <el-dialog
    :title="title"
    :show-close="false"
    :close-on-press-escape="false"
    :close-on-click-modal="false"
    :visible.sync="dialogVisible"
    :modal="modal"
    :width="width"
  >
    <slot name="dialog-content"></slot>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
    };
  },
  watch: {
    showClose: {
      handler(newVal, oldVal) {
        console.log(newVal);
        console.log(oldVal);
      }
    }
  },
  props: ["dialogCom", "dialogVisible","modal" ,"title", "width", "showClose"],
  created() {}
};
</script>
