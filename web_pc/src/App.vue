<template>
  <div id="app">
    <!-- <img src="./assets/logo.png"> -->
    <router-view/>
  </div>
</template>
<script>
export default {
  name: 'App',
  created() {
  }
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
  margin: 50px 0;;
  border: none;
  border-bottom: 1px solid #ebebeb;
}
#app {
  width: 100%;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
