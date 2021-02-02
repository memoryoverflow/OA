<template>
  <el-dialog
    :title="title"
    :show-close="showClose"
    :close-on-press-escape="true"
    :close-on-click-modal="false"
    :visible.sync="dialogVisible"
    :modal="modal"
    :width="width"
    @close="closeDialog"
  >
    <slot name="dialog-content"></slot>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {};
  },
  watch: {

    dialogVisible: {
      handler(newVal, oldVal) {
        this.dialogVisible=newVal;
      }
    }
  },
  props1: ["dialogVisible", "modal", "title", "width", "showClose", "closeFun"],
  props: {
    closeFun: {
      required:false,
      type: Function,
      default: () => {
        return Function;
      }
    },
    dialogVisible: {
      required: false,
      type: Boolean,
      default: false,
    },
    modal: {
      required: false,
      type: Boolean,
      default: true,
    },
    title: {
      required: true,
      default: ""
    },
    width: {
      required: false,
      default: '40%'
    },
    showClose: {
      required: false,
      type: Boolean,
      default: false
    }
  },
  created() {
  },
  methods:{
    closeDialog() {
      this.dialogVisible=false
      this.closeFun();
    },
  }
};
</script>
