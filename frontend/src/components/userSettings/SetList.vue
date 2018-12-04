<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container">
                        <div class="set_content">
                            <SideMenu></SideMenu>
                            <div class="right_con">
                                <h3>활동이력</h3>
                                <div class="set_list">
                                    <div class="set_tabs">
                                        <ul class="tabs">
                                            <li><a @click="changeTab('tab1')">로그인 기록</a></li>
                                            <li><a @click="changeTab('tab2')">정보 수정 이력</a></li>
                                        </ul>
                                    </div>
                                    <div id="tab1" class="tab_content" v-if="tab1">
                                        <h4>로그인 기록은 개인정보처리방침에 명시된<br>
                                            통신비밀보호법에 의해 최대 90일 까지만 보관됩니다</h4>
                                        <span class="last_login">최종 로그인 : {{getLastLogin.loginTime}}<br>
										접속 IP : {{getLastLogin.ip}}</span>
                                        <form action="#" method="post">
                                            <fieldset>
                                                <table class="set_list_table01">
                                                    <colgroup>
                                                        <col style="width:25%">
                                                        <col style="width:*">
                                                    </colgroup>
                                                    <tbody>
                                                    <tr>
                                                        <th>기간검색</th>
                                                        <td>
                                                            <datepicker class="select_01" v-model="logSdate" :format="'yyyy-MM-dd'"
                                                                placeholder="Select Date" @input="logDateSelected"
                                                                :calendar-button="true"
                                                                calendar-button-icon="calendar_btn">
                                                            </datepicker>
                                                            ~
                                                            <datepicker class="select_01" v-model="logEdate" :format="'yyyy-MM-dd'"
                                                                placeholder="Select Date" @input="logDateSelected"
                                                                :calendar-button="true"
                                                                calendar-button-icon="calendar_btn">
                                                            </datepicker>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </fieldset>
                                        </form>
                                        <table class="set_list_table01">
                                            <colgroup>
                                                <col style="width:30%">
                                                <col style="width:15%">
                                                <col style="width:20%">
                                                <col style="width:20%">
                                                <col style="width:15%">
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>일시</th>
                                                <th>구분</th>
                                                <th>로그인 IP</th>
                                                <th>로그인 국가</th>
                                                <th>로그인 정보</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="log in logList">
                                                <td>{{$moment(log.loginTime).utc("Asia/Seoul").format("YYYY년 MM월 DD일 hh:mm:ss")}}</td>
                                                <td>{{log.logType == 'o'?'로그아웃':'로그인'}}</td>
                                                <td>{{log.ip}}</td>
                                                <td>{{log.location}}</td>
                                                <td>{{log.loginType}}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div class="list_page">
                                            <a class="first2_btn" v-if="(logStartPage-1)>0" @click="setLogPage(logStartPage-1)">이전</a>
                                            <ul>
                                                <li v-for="i in logEndPage" v-if="i>=logStartPage">
                                                  <a v-bind:class="{'on':i==logPage}" @click="setLogPage(i)">{{i}}</a>
                                                </li>
                                            </ul>
                                            <a class="last2_btn" v-if="(logEndPage+1)<=logPerPage" @click="setLogPage(logStartPage+actPageLimit)">다음</a>
                                        </div>
                                    </div>
                                    <div id="tab2" class="tab_content" v-if="tab2">
                                        <h4>최근 6개월 동안의<br>비밀번호 변경 이력이 제공됩니다.</h4>
                                        <span class="last_login">최종 로그인 : YYYY-MM-DD 00:00:00<br>
										접속 IP : 123.123.123.123</span>
                                        <form action="#" method="post">
                                            <fieldset>
                                                <table class="set_list_table01">
                                                    <colgroup>
                                                        <col style="width:25%">
                                                        <col style="width:*">
                                                    </colgroup>
                                                    <tbody>
                                                    <tr>
                                                        <th>기간검색</th>
                                                        <td>
                                                            <datepicker class="select_01" v-model="actSdate" :format="'yyyy-MM-dd'"
                                                                placeholder="Select Date" @input="actDateSelected"
                                                                :calendar-button="true"
                                                                calendar-button-icon="calendar_btn">
                                                            </datepicker>
                                                            ~
                                                            <datepicker class="select_01" v-model="actEdate" :format="'yyyy-MM-dd'"
                                                                placeholder="Select Date" @input="actDateSelected"
                                                                :calendar-button="true"
                                                                calendar-button-icon="calendar_btn">
                                                            </datepicker>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </fieldset>
                                        </form>
                                        <table class="set_list_table01">
                                            <colgroup>
                                                <col style="width:45%">
                                                <col style="width:25%">
                                                <col style="width:25%">
                                            </colgroup>
                                            <thead>
                                            <tr>
                                                <th>일시</th>
                                                <th>변경 IP</th>
                                                <th>변경내용</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="act in changeList">
                                                <td>{{$moment(act.createAt).utc("Asia/Seoul").format("YYYY년 MM월 DD일 hh:mm:ss")}}</td>
                                                <td>{{act.ip}}</td>
                                                <td>{{act.activityComment}}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <div class="list_page">
                                            <a class="first2_btn" v-if="(actStartPage-1)>0" @click="setActPage(actStartPage-1)">이전</a>
                                            <ul>
                                                <li v-for="i in actEndPage" v-if="i>=actStartPage">
                                                  <a v-bind:class="{'on':i==actPage}" @click="setActPage(i)">{{i}}</a>
                                                </li>
                                            </ul>
                                            <a class="last2_btn" v-if="(actEndPage+1)<=actPerPage" @click="setActPage(actStartPage+actPageLimit)">다음</a>
                                        </div>
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
    import {mapGetters, mapActions} from 'vuex';
    import Datepicker from 'vuejs-datepicker';
    import SideMenu from '../../components/inc/SideMenu'
    export default {
        name: 'SetList',
        components:{
            Datepicker, SideMenu
        },
        computed : {
            ...mapGetters([
                'getMemberIdx',
                'getLastLogin'
            ])
        },
        data : function() {
            return {
                tab1 : true,
                tab2 : false,
                logList : [],
                logPage : 1,
                logTotal : 0,
                logPerPage : 0,
                logStartPage : 1,
                logPageLimit : 5,
                logEndPage : 1,
                changeList : [],
                actPage : 1,
                actTotal : 0,
                actPerPage : 0,
                actStartPage : 1,
                actPageLimit : 5,
                actEndPage : 1,
                logSdate:"",
                logEdate:"",
                actSdate:"",
                actEdate:"",
            }
        },
        created : function () {
            this.getActList();
            this.getLogList();
        },
        methods : {
            ...mapActions([
                'getActivityList',
                'getLoginLogList',
            ]),
            getLogList : function() {
                let sDate,eDate;
                if(this.logSdate)
                    sDate = this.$moment(this.logSdate).format('YYYY-MM-DD');
                if(this.logEdate)
                    eDate = this.$moment(this.logEdate).format('YYYY-MM-DD');

                this.$store.dispatch('getLoginLogList',{memberIdx : this.getMemberIdx, startDate: sDate, endDate: eDate, page : this.logPage, limit : 3}).then((response)=>{
                    this.logList = response.returnData.loginHistoryList;
                    this.logTotal = response.returnData.total;
                    this.calcLogPageNumber();
                }).catch((error)=>{
                });
            },
            setLogPage : function(page){
                this.logPage = page;
                this.getLogList();
            },
            logDateSelected : function(){
                this.logPage = 1;
                this.getLogList();
            },
            calcLogPageNumber(){
                this.logPerPage = Math.ceil(this.logTotal/3);
                if(this.logPerPage<1)this.logPerPage = 1;
                if(this.logPage>this.logPerPage) this.logPage = this.logPerPage;
                if(this.logPage<1)this.logPage = 1;

                let buff = this.logPage%this.logPageLimit;
                this.logStartPage = this.logPage - buff;
                this.logEndPage = this.logStartPage+this.logPageLimit;
                if(this.logEndPage > this.logPerPage)this.logEndPage = this.logPerPage;
            },
            getActList : function () {
                let sDate,eDate;
                if(this.actSdate)
                    sDate = this.$moment(this.actSdate).format('YYYY-MM-DD');
                if(this.actEdate)
                    eDate = this.$moment(this.actEdate).format('YYYY-MM-DD');

                this.$store.dispatch('getActivityList',{memberIdx : this.getMemberIdx, page : this.actPage, startDate: sDate, endDate: eDate, limit : 3}).then((response)=>{
                    this.changeList = response.returnData.activitiys;
                    this.actTotal = response.returnData.total;
                    this.calcActPageNumber();
                }).catch((response)=>{
                })
            },
            setActPage : function(page){
                this.actPage = page;
                this.getActList();
            },
            actDateSelected : function(){
                this.actPage = 1;
                this.getActList();
            },
            calcActPageNumber(){
                this.actPerPage = Math.ceil(this.actTotal/3);
                if(this.actPerPage<1)this.actPerPage = 1;
                if(this.actPage>this.actPerPage) this.actPage = this.actPerPage;
                if(this.actPage<1)this.actPage = 1;

                let buff = this.actPage%this.actPageLimit;
                this.actStartPage = this.actPage - buff;
                this.actEndPage = this.actStartPage+this.actPageLimit;
                if(this.actEndPage > this.actPerPage)this.actEndPage = this.actPerPage;
            },
            changeTab : function(tab){
                this.tab1 = false;
                this.tab2 = false;
                switch (tab) {
                    case 'tab1':
                        this.tab1 = true;
                        break;
                    case 'tab2':
                        this.tab2 = true;
                        break;
                }
            }
        }
    }
</script>

<style scoped>

</style>
