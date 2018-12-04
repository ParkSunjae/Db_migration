<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="main_inner">
                    <div class="data">
                        <div class="data_tabs">
                            <ul>
                                <li class="on">
                                    <router-link :to="{name : 'TermsView' }" class="write_icon" tag="a">
                                        개인정보처리방침
                                    </router-link>
                                </li>
                                <li>
                                    <router-link :to="{name : 'PrivacyView' }" class="write_icon" tag="a">
                                        이용약관
                                    </router-link>
                                </li>
                            </ul>
                        </div>
                        <div class="data_con" v-html="contents"></div>
                        <div v-if="nonSelect">
                            <p>개인정보처리방침 이전 버전 확인하기</p>
                            <div v-for="(con, index) in contentList" class="aTags">
                                <p @click="clickLink(con, index)" v-if="index != tempIndex" class="aTags">
                                    {{checkDate(con.createAt)}} 이용약관 보기</p>
                            </div>
                        </div>
                    </div>

                    <div class="intro_txt">
                        <p><a href="#">VORA소개</a> · <a href="#">설정</a> ·
                            <router-link :to="{name : 'TermsView' }" class="write_icon" tag="a">
                                개인정보처리방침
                            </router-link>
                            ·
                            <router-link :to="{name : 'PrivacyView' }" class="write_icon" tag="a">
                                이용약관
                            </router-link>
                        </p>
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
    import {mapGetters, mapActions} from 'vuex'
    import global from "../global";

    export default {
        name: "TermsView",
        data: function () {
            return {
                contentDate: '',
                contents: '',
                contentList: [],
                nonSelect: false,
                tempIndex: 0
            }
        },
        computed: {
            ...mapGetters([
                'getMemberIdx'
            ])
        },
        created: function () {
            this.getTerms();
        },
        methods: {
            ...mapActions([
                'findAllTerms'
            ]),
            getTerms: function () {
                let send = {
                    type: 't'
                }
                this.$store.dispatch('findAllTerms', send).then((response) => {
                    console.log("aaa", response);
                    this.contentList = response;
                    this.contents = this.contentList[0].termsContents;
                    this.contentDate = this.checkDate(this.contentList[0]);
                    if (this.contentList.length > 1) {
                        this.nonSelect = true;
                    }
                }).catch((response) => {

                });
            },
            clickLink: function (con, index) {
                this.contents = con.termsContents;
                this.contentDate = this.checkDate(con);
                this.tempIndex = index;
            },
            checkDate: function (timestamps) {
                let temp1 = this.$moment(timestamps).format("YYYY년MM월DD일");
                return temp1;
            },
        }
    }
</script>

<style scoped>
    .aTags {
        margin-left: 15px;
        color: #253cff;
        font-family: helvetica;
        text-decoration: underline;
        text-transform: uppercase;
    }

    .aTags:hover {
        text-decoration: underline;
        cursor: pointer;
    }

    .aTags:active {
        color: black;
    }

    .aTags:visited {
        color: purple;
    }

</style>
<style scoped>

</style>
