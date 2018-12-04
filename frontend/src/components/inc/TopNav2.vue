<template>
    <div id="A_Header">
        <div class="top_menu">
            <h2>
                <router-link :to="{path : '/MainFeed' }" class="write_icon" tag="a">
                    <img src="../../../static/img/main_logo.png" alt="vara로고">
                </router-link>
            </h2>
            <div class="write_btns">
                <button type="button" class="write_btns01" @click="cancelWriteData">취소</button>
                <button type="button" class="write_btns02" v-if="!showButton" @click="uploadContent">업로드</button>
                <button type="button" class="write_btns02" v-if="showButton" @click="upDateContent">수정</button>
            </div>
        </div>
    </div>
</template>

<script>
    import modal from '../inc/Modal' // 모달 컴포넌트
    import {mapGetters, mapActions} from 'vuex'

    export default {
        name: "TopNav",
        components: {
            modal
        },
        props : {
            showButton : Boolean
        },
        data() {
            return {

            }
        },
        computed : {
            ...mapGetters([
                'getWriteData',
                'getModifyData'
            ])
        },
        methods: {
        	...mapActions([
        		'removeWriteDataSet',
                'saveFeeds',
                'saveModifyFeeds',
                'removeModifyDataSet'
            ]),
	        uploadContent : function () {
        		this.$store.dispatch('saveFeeds', this.getWriteData).then((response) => {
                    this.$router.push({'name': 'MainFeed'});
                }).catch((response) => {

                });
            },
            upDateContent : function () {
                this.$store.dispatch('saveModifyFeeds', this.getModifyData).then((response) => {
                    this.$router.push({'name': 'MainFeed'});
                }).catch((response) => {

                });
            },
            cancelWriteData : function () {
                this.$store.dispatch('removeModifyDataSet');
                this.$store.dispatch('removeWriteDataSet');
                this.$router.push({'name': 'MainFeed'});
            }
        }
    }
</script>

<style scoped>
    @supports (-webkit-overflow-scrolling: touch) {
        .top_menu {
            padding-top: 20px;
        }
    }
    @supports(padding: max(0px)) {
        .top_menu {
            padding-top: env(safe-area-inset-top);
        }
    }
</style>
