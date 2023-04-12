<template>
  <MainMenu />
  <router-view />
</template>

<script>
  import MainMenu from "@/components/MainMenu";

  export default {
    name: 'IndexView',
    components: {MainMenu},
  }
</script>
