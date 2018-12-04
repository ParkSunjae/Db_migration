<template>
    <div id="app" v-cloak>
        <gnb v-if="showGnb" :showAlarm="showGnb4"></gnb>
        <gnb2 v-if="showGnb2" :showButton="showGnb3"></gnb2>
        <modal v-if="social" @close="close">
            <div slot="body">
                <div class="login_view">
                    <div class="view_wrap">
                        <span class="close"><img src="../static/img/esc_icon.png" alt="닫기"></span>
                        <div class="sns_login_btn">
                            <button type="button" class="facebook">Facebook 로그인</button>
                            <button type="button" class="google">Google 로그인</button>
                            <button type="button" class="naver">네이버 로그인</button>
                            <button type="button" class="kakao">Kakao 로그인</button>
                        </div>
                    </div>
                </div>
            </div>
        </modal>
        <modal v-if="getLoadings" v-cloak>
            <div slot="body">
                <LoadingSpinner></LoadingSpinner>
            </div>
        </modal>
        <router-view :key="$route.fullPath"></router-view>
    </div>
</template>

<script>
    import Modal from './components/inc/Modal'
    import gnb from './components/inc/TopNav'
    import gnb2 from './components/inc/TopNav2'
    import LoadingSpinner from './components/inc/Loading' // 로딩 애니
    import eventHub from "./eventHub"
    import {mapGetters, mapActions} from 'vuex'
    import http from './http'
    import global from './global'

    export default {
        name: 'app',
        components: {
            Modal, gnb, gnb2, LoadingSpinner,
        },
        computed : {
            ...mapGetters([
                'getLoadings',
            ])
        },
        data: function () {
            return {
                social: false,
                showGnb: false,
                showGnb2: false,
                showGnb3: false,
                showGnb4: false
            }
        },
        created: function () {
            eventHub.$on('before-request', this.showSpinner);
            eventHub.$on('request-error', this.hideSpinner);
            eventHub.$on('after-response', this.hideSpinner);
            eventHub.$on('response-error', this.hideSpinner);
            this.$router.push({'name': 'Intro'});
        },
        beforeDestroy: function () {
            eventHub.$off('before-request', this.showSpinner);
            eventHub.$off('request-error', this.hideSpinner);
            eventHub.$off('after-response', this.hideSpinner);
            eventHub.$off('response-error', this.hideSpinner);
        },
        methods : {
            ...mapActions([
                'showYnLoading',
                'showGnb4Act',
                'login',
                'setDeviceInfo'
            ]),
            showSpinner() {
                // console.log('show spinner');
                this.$store.dispatch("showYnLoading", true);
            },
            hideSpinner() {
                // console.log('hide spinner');
                this.$store.dispatch("showYnLoading", false);
            },
            refreshAccessToken: function () {
                const {accessToken} = localStorage;

                // console.log("localStorage=====", localStorage);
                let os = this.checkType;
                // console.log("accessToken:", accessToken);
                if (accessToken != null) {

                    // console.log("accessToken:", accessToken);
                    http.createAxios().post(global.baseURI + global.api.post.member.signinT, {accessToken})
                        .then((response) => {
                            // console.log('Main refreshAccessToken response!!!:', response);
                            //인증성공시
                            if (response.data.code == 'OK') {
                                response.data.returnData.deviceType = this.checkType();
                                this.$store.commit('login', response.data.returnData);
                                this.$router.push({name: 'MainFeed'});
                                // router.push({name: 'StepThree'});
                                // router.push({name: 'StepTwo'});

                            } else if (response.data.code == "COMMON_LOGIN_FAIL") {

                                this.$store.commit('logout');
                            }
                            else {

                            }

                        })
                        .catch((error) => {
                            console.log(error);
                            if (error.response.data.errCode == '401') {
                                //로그인 실패 정상처리 (토큰만료 토큰검증 실패 등등)
                                this.$store.commit('logout');
                                this.$router.push({name: 'Intro'});
                            } else {
                                //비정상 로그인 실패 네트워크 에러 시스템 에러 등등
                                //todo 예외처리 정리 필요함.
                                this.$router.push({name: 'Intro'});
                            }
                        });
                    return;
                }
            },
            checkType : function () {
                let mobile = (/iphone|ipad|ipod|android/i.test(navigator.userAgent.toLowerCase()));
                console.log(mobile);
                if (mobile) {

                    // 유저에이전트를 불러와서 OS를 구분합니다.
                    let userAgent = navigator.userAgent.toLowerCase();

                    if (userAgent.search("android") > -1)

                        this.deviceType = "MOBILE(AOS)";

                    else if ((userAgent.search("iphone") > -1) || (userAgent.search("ipod") > -1)

                        || (userAgent.search("ipad") > -1))

                        this.deviceType = "MOBILE(IOS)";
                    else
                        this.deviceType = "MOBILE(ETC)";

                } else {
                    // 모바일이 아닐 때
                    this.deviceType = "WEB";
                }
                this.$store.dispatch('setDeviceInfo', this.deviceType);

            }
        },
        watch: {
            '$route'(to, from) {

                console.log('to', to);
                console.log('from', from);
                if(from.name == 'MainFeed' && to.name == 'Intro'){
                    this.refreshAccessToken();
                }
                if(from.name == 'MainFeed' && to.name == 'Login'){
                    this.refreshAccessToken();
                }

                if(from.name == to.name){
                    this.$router.go(this.$router.currentRoute);
                    this.$forceUpdate();
                }


                this.$store.dispatch('showGnb4Act', false);
                if (to.name != 'Intro' && to.name != 'Join' && to.name != 'StepOne' && to.name != 'StepTwo' && to.name != 'StepThree' && to.name != 'Login' && to.name != 'PasswordSend') {
                    if (to.name == 'Write') {
                        this.showGnb2 = true;
                        this.showGnb = false;
                        this.showGnb3 = false;
                    } else if (to.name == 'Modify') {
                        this.showGnb2 = true;
                        this.showGnb = false;
                        this.showGnb3 = true;
                    } else {
                        this.showGnb = true;
                        this.showGnb2 = false;
                        this.showGnb3 = false;
                    }
                } else {
                    this.showGnb = false;
                    this.showGnb2 = false;
                    this.showGnb3 = false;
                }
            }
        }


    }
</script>
<style scoped>
    .modal {
        margin-top: 350px;
    }
</style>
