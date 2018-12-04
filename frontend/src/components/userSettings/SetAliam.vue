<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="main_inner">
                    <div class="set_content">
                        <SideMenu></SideMenu>
                        <div class="right_con">
                            <h3>보라의 새로운 소식과 알림을 받아보세요.</h3>
                            <div class="notice_wrap_inner">
                                <div id="alarm_list">
                                    <div class="notice_con" v-for="data in aliamList">
                                        <a @click="findMemberByIdx(data.uidx)">
                                            <div class="img">
                                                <img v-bind:src="data.fileThumbnail" alt="프로필 사진">
                                            </div>
                                        </a>
                                        <div class="txt">
                                            <a @click="findMemberByIdx(data.uidx)">
                                                <p v-html="data.message"></p>
                                                <span class="day">{{checkDate(data.createAt)}} ago</span>
                                            </a>
                                        </div>
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
</template>
<script>
import SideMenu from '../../components/inc/SideMenu'
import {mapGetters, mapActions} from 'vuex'

export default {
    name: "SetAliam",
    components:{
        SideMenu
    },
    computed :{
        ...mapGetters([
            'getMemberIdx'
        ])
    },
    data: function () {
        return {
            aliamList: [],
            page : 0,
            limit : 20,
            bottom : false,
        }
    },
    methods :{
        ...mapActions([
            'getMyNoticeAllList',
        ]),
        loadData : function(){
            this.page = this.page+1;
            let data = {uidx: this.getMemberIdx,page:this.page,limit:this.limit}
            this.$store.dispatch("getMyNoticeAllList",data).then((response)=>{
                if(response.code=='OK'){
                    if(response.returnData.alarmList.length > 0){
                        for (obj of response.returnData.alarmList) {
                            this.aliamList.push(obj);
                        }
                    }
                }
            }).catch((error)=>{
            });
        },
        findMemberByIdx: function (idx) {
            this.$router.push({'name': 'MemberFeed', params: {memberIdx: idx}});
        },
        moveDetail: function (idx) {
            this.$router.push({'name': 'FeedView', params: {feedIdx: idx}});
        },
        checkDate: function (timestamps) {
            let temp1 = this.$moment(timestamps);
            let temp2 = this.$moment(new Date());
            return this.$moment.duration(temp1.diff(temp2, "seconds"), "seconds").humanize();
        },
        clearList : function(){
            this.page = 0;
            this.aliamList.splice(0,this.aliamList.length);
        },
        bottomVisible() {
            const scrollY = window.scrollY
            const visible = document.documentElement.clientHeight
            const pageHeight = document.documentElement.scrollHeight
            const bottomOfPage = visible + scrollY >= pageHeight
            return bottomOfPage || pageHeight < visible
        },
    },
    created: function(){
        this.clearList();
        this.loadData();
        window.addEventListener('scroll', () => {
            this.bottom = this.bottomVisible()
        });
    },
    watch: {
        bottom(bottom) {
            if(bottom) this.loadData();
        }
    }
}
</script>

<style lang="css">
</style>
