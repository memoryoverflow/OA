<template>
  <div id="app">
    <!-- <img src="./assets/logo.png"> -->
    <router-view/>
  </div>
</template>
<script>
export default {
  name: 'cn.yj.cloud.App'
}
</script>

<style>
body{
  margin: 0;
  padding: 0;
}


ul li{
  list-style: none;
}
a{
  text-decoration: none;
}
hr{
  border: none;
  border-bottom: 1px solid #ebebeb;
}
#app {
  width: 100%;
  height: 100vh;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background: rgb(246, 246, 246);
}
::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 2px !important; /*高宽分别对应横竖滚动条的尺寸*/
  height: 5px !important;
  background: #ffffff !important;
  cursor: pointer !important;
}
::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 5px !important;
  -webkit-box-shadow: inset 0 0 2px rgba(240, 240, 240, 0.5) !important;
  background: rgba(63, 98, 131, 0.8) !important;
  cursor: pointer !important;
}

::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  -webkit-box-shadow: inset 0 0 2px rgba(240, 240, 240, 0.5) !important;
  border-radius: 0 !important;
  background: rgba(240, 240, 240, 0.5) !important;
  cursor: pointer !important;
}
.search-btn-class {
  background: #20b2aa;
  border: #20b2aa;
  color: white;
}
.search-btn-class:hover{
  background: #23c4b2;
  color: white;
}
.search-btn-class:visited,.search-btn-class:active{
  background: #20b2aa;
  border: #20b2aa;
  color: white;
}
</style>
