<template>
    <div id="A_Header">
        <div class="top_menu">
            <h2>
                <router-link to="/Mainfeed" class="write_icon" tag="a" @click="location.reload()">
                    <img src="../../../static/img/main_logo.png" alt="vara로고">
                </router-link>
            </h2>
            <div class="search_box">
                <form>
                    <fieldset>
                        <!--<legend>상단 검색바</legend>-->
                        <div class="search_con">
                            <input type="text" title="검색" v-model="searchKey"
                                    @focus="showSearchMenu"
                                    @blur="closeSearchMenu"
                                    @keyup.enter="searchActive">
                            <input type="buttin" class="search_icon" @click="searchActive">
                            <template v-if="searchMenu">
                                <div class="search_wrap02" v-if="getSearchHistory.length > 0">
                                    <p class="tit">
                                        <span>최근검색어</span>
                                        <button type="button" @mousedown="deleteAllHistory">전체삭제</button>
                                    </p>
                                    <p v-for="history in getSearchHistory">
                                        <a @mousedown="searchFromButton(history.search)">{{history.search}}</a>
                                        <button type="button" class="esc_btn" @mousedown="deleteHistory(history)" @></button>
                                    </p>
                                </div>
                                <div class="search_wrap03" v-else>
                                    <p class="tit"><span>최근검색어</span>
                                        최근 검색내역이 없습니다.<br>
                                        많은 분들이 인기 태그를<br>검색하고 있습니다</p>
                                    <p class="tag">
                                        <button type="button" v-for="history in getPopularHistory"
                                                @mousedown="searchFromButton(history.search)">
                                            <span>{{history.search}}</span>
                                        </button>
                                    </p>
                                </div>
                            </template>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="top_icons">
                <router-link :to="{name : 'Write' }" class="write_icon" tag="button"></router-link>
                <v-badge color="red" class="notice_icon">
                    <span slot="badge" v-if="alarmCount > 0">{{alarmCount}}</span>
                    <button type="button" @click="noticeToggle"></button>
                </v-badge>
                <div class="notice_icon">
                    <div class="notice_wrap02" v-if="getShowGnb4 && noticeBool">
                        <p class="tit">알림</p>
                        <div class="notice_wrap_inner">
                            <div class="notice_con" v-for="notice in noticeList">
                                <a @click="moveDetail(notice)" v-if="notice.type != 'w'">
                                    <div class="img">
                                        <img :src=" notice.fileThumbnail|| '../../../static/img/no_image.png'"
                                             alt="프로필 사진">
                                    </div>
                                    <div class="txt">
                                        <p v-html="notice.message"></p>
                                        <span class="day">{{checkDate(notice.createAt)}}일전</span>
                                    </div>
                                </a>
                                <a @click="findMemberByIdx(notice)" v-if="notice.type =='w'">
                                    <div class="img">
                                        <img :src=" notice.fileThumbnail|| '../../../static/img/no_image.png'"
                                             alt="프로필 사진">
                                    </div>
                                    <div class="txt">
                                        <p v-html="notice.message"></p>
                                        <span class="day">{{checkDate(notice.createAt)}}일전</span>
                                    </div>
                                    <div class="notice_btn">
                                        <button type="button" class="notice_btn_01">팔로우</button>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="notice_wrap01" v-if="getShowGnb4 && !noticeBool">
                        <p class="tit">알림</p>
                        <p class="txt">새로운 알림이 없어요.</p>
                    </div>
                </div>
                <router-link :to="{path : '/MyFeed' }" class="mypage_icon" tag="button"></router-link>
                <ul class="menubar" @click="triggerMenu()">
                    <li v-bind:class="{'transition-act':!sideMenu}"></li>
                    <li v-bind:class="{'transition-act':!sideMenu}"></li>
                    <li v-bind:class="{'transition-act':!sideMenu}"></li>
                </ul>
            </div>
        </div>
        <Modal v-if="sideOnOff">
            <div slot="body">
                <div class="top_menu">
                    <div class="full_wrap">
                        <div class="full_gnb">
                            <div class="top_menu">
                                <h2>
                                    <router-link :to="{name : 'MainFeed' }" class="write_icon" tag="a">
                                        <img src="../../../static/img/main_logo.png" alt="vara로고">
                                    </router-link>
                                </h2>
                            </div>
                            <div class="search_box">
                                <form>
                                    <fieldset>
                                        <!--<legend>상단 검색바</legend>-->
                                        <div class="search_con">
                                            <input type="text" title="검색" v-model="searchKey"
                                                    @focus="showSearchMenu"
                                                    @blur="closeSearchMenu"
                                                    @keyup.enter="searchActive">
                                            <input type="buttin" class="search_icon" @click="searchActive">
                                            <template v-if="searchMenu">
                                                <div class="search_wrap02" v-if="getSearchHistory.length > 0">
                                                    <p class="tit">
                                                        <span>최근검색어</span>
                                                        <button type="button" @mousedown="deleteAllHistory">전체삭제</button>
                                                    </p>
                                                    <p v-for="history in getSearchHistory">
                                                        <a>{{history.search}}</a>
                                                        <button type="button" class="esc_btn" @mousedown="deleteHistory(history)" @></button>
                                                    </p>
                                                </div>
                                                <div class="search_wrap03" v-else>
                                                    <p class="tit"><span>최근검색어</span>
                                                        최근 검색내역이 없습니다.<br>
                                                        많은 분들이 인기 태그를<br>검색하고 있습니다</p>
                                                    <p class="tag">
                                                        <button type="button" v-for="history in getPopularHistory"
                                                                @mousedown="searchFromButton(history.search)">
                                                            <span>{{history.search}}</span>
                                                        </button>
                                                    </p>
                                                </div>
                                            </template>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                            <div class="myinfo_box">
                                <LoginUserInfo :showTrigger = "false">
                                </LoginUserInfo>
                            </div>
                            <ul class="lnb">
                                <li @click="triggerMenu()">
                                    <router-link to="/SetProfile" tag="a">프로필 수정</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/SetAccount" tag="a" class="on">계정관리</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/SetBusiness" tag="a">비즈니스계정전환</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/SetBlock" tag="a">차단 사용자 관리</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/SetNotice" tag="a">공지사항</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/SetHelp" tag="a">도움말</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/SetInquiry" tag="a">문의 및 제안하기</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/SetList" tag="a">활동이력</router-link>
                                </li>
                                <li @click="triggerMenu()">
                                    <router-link to="/Logout" tag="a">로그아웃</router-link>
                                </li>
                            </ul>
                            <div class="bot_txt">
                                <div class="intro_txt">
                                    <p><a href="https://www.vora.co.kr/feed/post.asp?idx=1679">VORA소개</a> · <a
                                        href="/inquiry/">문의 및 제안</a> · <a href="../data/data01.asp">개인정보처리방침</a> · <a
                                        href="../data/data02.asp">이용약관</a></p>
                                    <p class="app_icon"><a href="#" class="iphone">iPhone 앱</a><a href="#"
                                                                                                  class="android">Android
                                        앱</a></p>
                                    <p class="copyright">Copyright @ KYOBOBOOK CENTRE.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Modal>
    </div>
</template>

<script>
    import Modal from '../inc/Modal' // 모달 컴포넌트
    import {mapGetters, mapActions} from 'vuex'
    import 'vuetify/dist/vuetify.min.css'
    import LoginUserInfo from '../models/LoginUserInfo'
    export default {
        name: "TopNav",
        components: {
            Modal,
            LoginUserInfo
        },
        props: {
            showAlarm: Boolean
        },
        data() {
            return {
                noticeList: [],
                noticeBool: false,
                alarmCount: 0,
                showSearch: false,
                sideMenu: true,
                sideOnOff : false,
                turnSide : false,
                searchKey : "",
                searchMenu : false
            }
        },
        computed: {
            ...mapGetters([
                'getMemberIdx',
                'getShowGnb4',
                'getEmailAccoutDeviceType',
                'getSearchHistory',
                'getPopularHistory',
            ])
        },
        created: function () {
            this.findNoticeCount();
            this.loadSearchHistory();
        },
        methods: {
            ...mapActions([
                'getMyNoticeList',
                'getMyNoticeCount',
                'showGnb4Act',
                'changeAlarmShowYn',
                'setSearchQueryAct',
                'getSearchHistoryAct',
                'deleteSearchHistoryAct'
            ]),

            triggerMenu: function () {
                this.sideOnOff = !this.sideOnOff;
                this.turnSide = !this.turnSide;
                this.sideMenu = !this.sideMenu;

            },
            moveDetail: function (notice) {
                this.alarmCount = this.alarmCount - 1;
                if (this.alarmCount < 0) {
                    this.alarmCount = 0;
                }

                for (let i = 0; i < this.noticeList.length; i++) {
                    if (notice.idx == this.noticeList[i].idx) {
                        this.noticeList.splice(i, 1);
                    }
                }

                this.$store.dispatch('showGnb4Act', false);
                this.$store.dispatch('changeAlarmShowYn', {idx: notice.idx}).then((response) => {
                    if (response.code == "OK") {
                        this.$router.push({'name': 'FeedView',params:{feedIdx:notice.feedIdx}});
                    }
                }).catch((response) => {
                });
            },
            findMemberByIdx: function (notice) {
                this.alarmCount = this.alarmCount - 1;
                if (this.alarmCount < 0) {
                    this.alarmCount = 0;
                }

                for (let i = 0; i < this.noticeList.length; i++) {
                    if (notice.idx == this.noticeList[i].idx) {
                        this.noticeList.splice(i, 1);
                    }
                }
                this.$store.dispatch('showGnb4Act', false);
                this.$store.dispatch('changeAlarmShowYn', {idx: notice.idx}).then((response) => {
                    if (response.code == "OK") {
                        this.$router.push({'name': 'MemberFeed', params: {memberIdx: notice.uidx}});
                    }
                }).catch((response) => {

                });

            },
            findNoticeCount: function () {
                let data = {uidx: this.getMemberIdx}
                this.$store.dispatch('getMyNoticeCount', data).then((response) => {
                    this.alarmCount = response.returnData.alarmCount;
                }).catch((response) => {

                })
            },
            noticeToggle: function () {
                this.$store.dispatch('showGnb4Act', !this.getShowGnb4);
                if (this.getShowGnb4) {
                    let data = {uidx: this.getMemberIdx}
                    this.$store.dispatch('getMyNoticeList', data).then((response) => {
                        this.noticeList = response.returnData;
                        if (this.noticeList.length > 0) {
                            this.noticeBool = true;
                        } else {
                            this.noticeBool = false;
                        }
                    }).catch((response) => {

                    })
                } else {
                    this.noticeList = [];
                }
            },
            loadSearchHistory : function(){
                this.$store.dispatch('getSearchHistoryAct');
            },
            searchActive : function(){
                if(this.searchKey.trim().length>=2){
                    this.$store.dispatch('setSearchQueryAct',this.searchKey).then((response)=>{
                        if(this.$router.currentRoute.name=='SearchFeed'){
                            this.$store.dispatch('searchFeedAct');
                        }
                        else
                            this.$router.push('SearchFeed');
                    }).catch((error)=>{
                    });
                }else{
                    alert("최소 2자 이상이여야 합니다.")
                }
            },
            searchFromButton : function(item){
                this.searchKey = item;
                this.$store.dispatch('setSearchQueryAct',this.searchKey).then((response)=>{
                    if(this.$router.currentRoute.name=='SearchFeed'){
                        this.$store.dispatch('searchFeedAct');
                    }
                    else
                        this.$router.push('SearchFeed');
                }).catch((error)=>{
                });
            },
            deleteHistory : function(history){
                this.$store.dispatch('deleteSearchHistoryAct',history);
            },
            deleteAllHistory : function(){
                this.$store.dispatch('deleteAllSearchHistoryAct');
            },
            showSearchMenu : function(){
                this.searchMenu = true;
            },
            closeSearchMenu : function(){
                this.searchMenu = false;
            },
            checkDate: function (timestamps) {
                let temp1 = this.$moment(timestamps);
                let temp2 = this.$moment(new Date());
                return this.$moment.duration(temp1.diff(temp2, "seconds"), "seconds").humanize();
            },
        }
    }
</script>

<style scoped>
    @supports (-webkit-overflow-scrolling: touch) {
        .top_menu {
            padding-top: 20px;
        }
    }

    @supports (padding: max(0px)) {
        .top_menu {
            padding-top: env(safe-area-inset-top);
        }
    }
</style>
