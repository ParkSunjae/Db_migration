<template>
    <transition name="modal-fade">
        <div class="modal-backdrop bx">
            <div class="modal">
                <header class="modal-header">
                    <slot name="header"></slot>
                </header>
                <section class="modal-body">
                    <slot name="body"></slot>
                </section>
                <footer class="modal-footer">
                    <slot name="footer">
                    </slot>
                </footer>
            </div>
        </div>
    </transition>
</template>

<script>
    export default {
        name: "Modal",
        methods: {
            close() {
                this.$emit('close');
            }
        }
    }
</script>

<style scoped>
    .modal-backdrop {
        /*padding-top: env(safe-area-inset-top);*/
        bottom: calc(0px - env(safe-area-inset-bottom));
    }
</style>
