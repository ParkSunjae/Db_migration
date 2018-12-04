<template>
    <transition v-on:enter="afterEnter" v-on:after-leave="afterLeave">
        <div class="loading_back">
            <div class="spinner_box">
                <div class="spinner_img"></div>
            </div>
        </div>
    </transition>
</template>

<script>
    export default {
        name: "Loading",
        methods: {
            afterEnter: function () {
                this.$removeScroll();
            },
            afterLeave: function () {
                this.$addScroll();
            }
        }
    }
</script>

<style scoped>

</style>
