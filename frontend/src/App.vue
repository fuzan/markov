<template>
  <div id="app">
    <uploader />
  </div>
</template>

<script>
import uploader from "./components/Uploader";

export default {
  name: "App",
  components: {
    uploader
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
