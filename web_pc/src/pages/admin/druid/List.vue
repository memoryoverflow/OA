<template>
  <div>
    <main-frame ref="frame">
      <template slot="mainFrame">
        <iframe
          style="width: 100%; height: 1000px;border: none"
          ref="iframe"
          :src="URL.druid"
        />
      </template>
    </main-frame>
  </div>
</template>
<script>

let _this = {};
export default {
  data() {
    return {

      URL: {
        druid: this.$server + "/druid",
      },
    };
  },
  created() {
  },
  methods: {}
};
</script>
<style scoped>
</style>
