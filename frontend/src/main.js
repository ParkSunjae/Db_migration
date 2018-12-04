import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import 'es6-promise/auto'
import global from "./global"
import VueI18n from "vue-i18n"
import ToggleButton from 'vue-js-toggle-button'
import store from './store'
import http from './http'
import Vuex from 'vuex'
import moment from 'vue-moment'
import VueCollapse from 'vue2-collapse'
import {mapState, mapActions, mapGetters} from 'vuex'
import router from './router/';
import eventHub from "./eventHub";
import PhotoGrid from 'vue-photo-grid'
import VueLazyLoad from 'vue-lazyload'
import VueYoutube from 'vue-youtube'
import Vuetify from 'vuetify'

Vue.use(VueRouter);
Vue.use(VueI18n);
Vue.use(ToggleButton);
Vue.use(Vuex);
Vue.use(moment);
Vue.use(VueCollapse);
Vue.use(PhotoGrid);
Vue.use(VueLazyLoad);
Vue.use(VueYoutube);
Vue.use(Vuetify);

var userLang = navigator.language.substring(0, 2);
// console.log("userLang:" + userLang)

global.locale = userLang;
const i18n = new VueI18n({
    locale: global.locale,
    // messages: {
    //     'ko': require('../static/lang/ko.json'),
    //     'en': require('../static/lang/en.json'),
    // }
});

Vue.prototype.$removeScroll = function () {
    // document.documentElement.style.height = null;
    //
    // let rect = document.body.getBoundingClientRect();
    // let sy = window.scrollY;
    // let wh = window.innerHeight;
    //
    // document.body.style.overflow = "hidden";
    // document.body.style.position = "fixed";
    // document.body.style.top = -sy + "px";
    // document.body.style.left = "0px";
    // document.body.style.right = "0px";
    // document.body.style.bottom = "0px";
    //
    // if (rect.height > wh) {
    //     document.documentElement.style.height = rect.height + "px";
    // } else {
    //     if (CSS.supports('padding-bottom: env(safe-area-inset-bottom)')) {
    //         document.documentElement.classList.add("forX");
    //     } else {
    //         document.documentElement.style.height = wh + "px";
    //     }
    // }
}
Vue.prototype.$addScroll = function () {
    // document.documentElement.classList.remove("forX");
    // let rect = document.body.getBoundingClientRect();
    // let sy = -rect.top;
    // document.documentElement.style.height = null;
    // document.body.style.overflow = "auto";
    // document.body.style.position = "relative";
    // document.body.style.top = "0";
    // window.scrollTo(0, sy);
};
document.addEventListener('backbutton', function(e) {
    e.preventDefault();
    app.views.main.router.back(); // go back manually, and it won't go back after reloadAll call
    return false;
});
new Vue({
    el: '#app',
    i18n: i18n,
    ToggleButton: ToggleButton,
    moment: moment,
    VueCollapse: VueCollapse,
    template: '<App/>',
    components: {
        App
    },
    mounted: function () {
        this.refreshAccessToken();
    },
    methods: {
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
                            router.push({name: 'MainFeed'});
                            // router.push({name: 'StepThree'});
                            // router.push({name: 'StepTwo'});
                            // router.push({name: 'StepOne'});

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
                            router.push({name: 'Intro'});
                        } else {
                            //비정상 로그인 실패 네트워크 에러 시스템 에러 등등
                            //todo 예외처리 정리 필요함.
                            router.push({name: 'Intro'});
                        }
                    });
                return;
            }
        },
        checkType : function () {
            // console.log(navigator.userAgent.toLowerCase());
            let mobile = (/iphone|ipad|ipod|android/i.test(navigator.userAgent.toLowerCase()));
            // console.log(mobile);
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
            // console.log("dispatch Main",this.deviceType);
            this.$store.dispatch('setDeviceInfo', this.deviceType);

        }
    },
    computed: {
        ...mapGetters([
            'getDeviceType'
        ])
    },
    ...mapActions([
        ''
    ]),
    store,
    router
}).$mount('#app');
