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

  mounted() {},
  created() {
    console.log(this.code)
    let user = this.$getUser();
    if (
      user == null ||
      user == undefined ||
      user == "null" ||
      user == "undefined"
    ) {
      return;
    }

    let arr = user.permission;
    if (arr.indexOf(this.code) > -1) {
      this.show = true;
    }

    if(this.code==null||this.code==''){
      this.show = false;
      return;
    }

    this.$get(this.url.checkAuth, {code:this.code}).then((res) => {
      if (res.R) {
        this.show = res.data;
      }
    });
  },
};
</script>
