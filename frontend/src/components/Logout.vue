<template lang="html">

</template>

<script>
    import Modal from "./inc/Modal"
    import {mapState, mapActions, mapGetters} from 'vuex'
  export default {
      name: "Logout",
      methods : {
        ...mapActions(['logout'])
      },
      created: function(){
        this.$store.dispatch('logout');
      }
  }
</script>
<style scoped>

</style>
