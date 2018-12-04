<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container">
                        <div class="set_content">
                            <SideMenu></SideMenu>
                            <div class="right_con">
                                <h3>보라의 새로운 소식과 알림을 받아보세요.</h3>
                                <div class="faq">
                                    <div class="onoff">
                                        <ul class="jq_onoff">
                                            <li v-for="item in noticeList">
                                                <p class="jq_toggle" @click="item.toggle = !item.toggle"
                                                   v-bind:class="{'on':item.toggle}">
                                                    {{ item.title }}
                                                    <span>{{item.createAt | moment('YYYY.MM.DD')}}</span>
                                                </p>
                                                <transition name="fade">
                                                    <p class="jq_hide" v-if="item.toggle">{{ item.content }}</p>
                                                </transition>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="intro_txt">
                            <p><a href="#">VORA소개</a> · <a href="#">설정</a> · <a href="#">개인정보처리방침</a> · <a
                                href="#">이용약관</a></p>
                            <p class="app_icon"><a href="#" class="iphone">iPhone 앱</a><a href="#" class="android">Android
                                앱</a></p>
                            <p class="copyright">Copyright @ KYOBO BOOK CENTRE.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import {mapState, mapActions, mapGetters} from 'vuex'
    import global from '../../global'
    import SideMenu from '../../components/inc/SideMenu'

    export default {
        name: 'SetNotice',
        components: {
            SideMenu
        },
        computed: {
            ...mapGetters(['getNoticeList', 'getNoticeTotal'])
        },
        data: function () {
            return {
                show: true,
                noticeList: [],
                bottom: false
            }
        },
        created: function () {
            this.clearNoticeListAct()
                .then((response) => {
                    this.noticeList = this.getNoticeList;
                    this.total = this.getNoticeTotal;
                }).catch((error) => {
            });
            window.addEventListener('scroll', () => {
                this.bottom = this.bottomVisible()
            })
        },
        methods: {
            ...mapActions(['clearNoticeListAct', 'loadNoticeListAct']),
            bottomVisible() {
                const scrollY = window.scrollY
                const visible = document.documentElement.clientHeight
                const pageHeight = document.documentElement.scrollHeight
                const bottomOfPage = visible + scrollY >= pageHeight
                return bottomOfPage || pageHeight < visible
            }
        },
        watch: {
            bottom(bottom) {
                if (bottom) {
                    this.loadNoticeListAct().then((response) => {
                        if (response == true) {
                            this.noticeList = this.noticeList.concat(this.getNoticeList);
                        }
                    }).catch((error) => {
                    });
                }
            }
        }

    }
</script>

<style scoped>

</style>
