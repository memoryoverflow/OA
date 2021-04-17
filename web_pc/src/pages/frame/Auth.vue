<template>
  <span v-if="show">
    <slot name="auth"></slot>
  </span>
</template>
<script>
let _this = {};
export default {
  data() {
    return {
      show: true,
      url: {
        checkAuth: "/permission/auth/check",
      },
    };
  },
  props: {
    code: {  // 必须提供字段
      required: true,
      type: Boolean,
    },
  },

  mounted() {
  },
  created() {
    console.log(this.code)
    if (this.code == "none") {
      return;
    }

    if (this.code == null || this.code == '') {
      this.show = false;
      return;
    }

    // 从本地缓存拿
    let userNullArr = [null, undefined, "null", "undefined"]
    let user = this.$getUser();
    if (userNullArr.indexOf(user) > -1) {
      this.$get(this.url.checkAuth, {code: this.code}).then((res) => {
        if (res.R) {
          this.show = res.data;
        }
      });
      return;
    }

    let arr = user.permission;
    if (arr.indexOf("*:*:*") > -1 || arr.indexOf(this.code) > -1) {
      this.show = true;
      return;
    }
  },
};
</script>
