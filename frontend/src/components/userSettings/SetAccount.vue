<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <modal v-if="changePassword">
                    <div slot="body">
                        <!-- 비밀번호변경 팝업 -->
                        <div class="change_pw_view popup">
                            <div class="change_pw_wrap popup_wrap">
                                <div class="tit">
                                    <p>비밀번호 변경</p>
                                    <span class="close" @click="changePasswordClose"><img
                                        src="../../../static/img/esc_icon.png" alt="닫기"></span>
                                </div>
                                <form>
                                    <fieldset>
                                        <!--<legend>비밀번호 변경</legend>-->
                                        <div id="pw_change_Div1" class="join_input">
                                            <input type="password" placeholder="현재 비밀번호" title="현재 비밀번호"
                                                   class="mb20 account_pwd" v-model="oldPwd">
                                            <input type="password" class="bb_none account_pwd" placeholder="새 비밀번호"
                                                   title="새 비밀번호" v-model="newPwd">
                                            <input type="password" class="account_pwd" placeholder="새 비밀번호 재입력"
                                                   title="새 비밀번호 재입력" v-model="newCheck">
                                        </div>
                                        <div class="btns">
                                            <button type="button" class="btn01" @click="changePasswordClose">취소</button>
                                            <button type="button" class="btn02" @click="changePasswordAction">완료
                                            </button>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                    </div>
                </modal>
                <modal v-if="passwordSend">
                    <div slot="body">
                        <!-- 임시 비밀번호 발송 팝업 -->
                        <div class="pw_send_view popup">
                            <div class="popup_wrap">
                                <div class="tit">
                                    <p>임시 비밀번호 발송</p>
                                    <span class="close" @click="passwordSendClose"><img
                                        src="../../../static/img/esc_icon.png" alt="닫기"></span>
                                </div>
                                <p class="link_txt">{{accountData.email}} (으)로<br>
                                    임시 비밀번호를 발송해드렸어요.</p>
                                <div class="btns">
                                    <button type="button" class="btn02" @click="passwordSendClose">확인</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </modal>
                <modal v-if="userExit">
                    <div slot="body">
                        <!-- 계정 탈퇴 신청 팝업 -->
                        <div class="withdarw_view popup">
                            <div class="popup_wrap">
                                <div class="tit">
                                    <p>탈퇴하기</p>
                                    <span class="close" @click="userExitClose"><img
                                        src="../../../static/img/esc_icon.png" alt="닫기"></span>
                                </div>
                                <p class="link_txt">더이상 VORA를 사용하지 않으시는 건가요?<br>
                                    탈퇴하시면 기록하신 모든 내용이 삭제되며 복구가 불가합니다.<br>
                                    정말로 떠나실 건가요?</p>
                                <div class="btns">
                                    <button type="button" class="btn01" @click="userExitClose">취소</button>
                                    <button type="button" class="btn02" @click="memberExitGo">탈퇴하기</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </modal>
                <modal v-if="emailCert">
                    <div slot="body">
                        <!-- 이메일 인증 팝업 -->
                        <div class="email_certification popup">
                            <div class="popup_wrap">
                                <div class="tit">
                                    <p>이메일 인증</p>
                                    <span class="close" @click="emailCertClose"><img
                                        src="../../../static/img/esc_icon.png" alt="닫기"></span>
                                </div>
                                <p class="link_txt">
                                    <span class="email_pop_txt">{{accountData.email}} (으)로</span>
                                    등록된 이메일로 인증을 받으시면<br>
                                    VORA 서비스를 자유롭게 이용하실 수 있어요</p>
                                <div class="btns">
                                    <button type="button" class="btn02" @click="sendVerificationMail">이메일 인증하기</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </modal>
                <div class="main_inner">
                    <div class="set_content">
                        <SideMenu></SideMenu>
                        <div class="right_con">
                            <h3>계정관리</h3>
                            <div class="join_day">
                                <p>최초 가입일 {{changeDate(accountData.accountCreateAt)}}</p>
                            </div>
                            <h4>이메일 주소</h4>
                            <div class="email_add">
                                <p class="txt01">{{accountData.email}}</p>
                                <div class="confirm_01" v-if="accountData.emailCertYn == 'N'">
                                    <p>인증이 필요해요</p>
                                </div>
                                <button type="button" v-if="accountData.emailCertYn != 'Y'"
                                        @click="emailCert = !emailCert">이메일 인증하기
                                </button>
                                <p class="confirm_days" v-if="accountData.emailCertYn == 'Y'">인증완료
                                    {{changeDate(accountData.emailCertDate)}}</p>
                            </div>
                            <div class="pw_change">
                                <h4>비밀번호</h4>
                                <button type="button" class="on" @click="changePassword = !changePassword">비밀번호 변경
                                </button>
                                <button type="button" class="pw_send" @click="sendTempPwd">임시 비밀번호
                                    발송
                                </button>
                            </div>
                            <div class="sns_linkage">
                                <h4>SNS 계정연동</h4>
                                <button type="button" class="facebook">페이스북 계정 로그인 연동 {{accountData.facebook ? '해제' :
                                    '활성'}}
                                </button>
                                <button type="button" class="google">구글 계정 로그인 연동 {{accountData.google ? '해제' : '활성'}}
                                </button>
                                <button type="button" class="naver">네이버 계정 로그인 연동 {{accountData.naver ? '해제' : '활성'}}
                                </button>
                                <button type="button" class="kakao">카카오 계정 로그인 연동 {{accountData.kakao ? '해제' : '활성'}}
                                </button>
                            </div>
                            <a href="#" class="withdraw_btn" @click="userExit = !userExit">계정탈퇴</a>
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
    import Modal from '../../components/inc/Modal'
    import SideMenu from '../../components/inc/SideMenu'
    import {mapGetters, mapActions} from 'vuex'

    export default {
        name: 'SetProfile',
        components: {
            Modal,
            SideMenu
        },
        created: function () {
            this.findAccountData();
        },
        computed: {
            ...mapGetters([
                'getAccountIdx',
                'getMemberIdx',
            ])
        },
        data: function () {
            return {
                changePassword: false,
                passwordSend: false,
                userExit: false,
                emailCert: false,
                accountData: {},
                oldPwd: "",
                newPwd: "",
                newCheck: ""
            }
        },
        methods: {
            ...mapActions([
                'getAccountDataAct',
                'callCertMail',
                'sendTempPassword',
                'checkPwdAndChange',
                'memberExitAct',
                'exitAfterLogout'
            ]),
            changePasswordClose: function () {
                this.changePassword = false;
                this.oldPwd = "";
                this.newPwd = "";
                this.newCheck = "";
            },
            passwordSendClose: function () {
                this.passwordSend = false;
            },
            userExitClose: function () {
                this.userExit = false;
            },
            emailCertClose: function () {
                this.emailCert = false;
            },
            findAccountData: function () {
                this.$store.dispatch('getAccountDataAct', {idx: this.getAccountIdx}).then((response) => {
                    this.accountData = response.returnData;
                }).catch((response) => {

                })
            },
            changeDate: function (str) {
                return this.$moment(str).format("YYYY년 MM월 DD일");
            },
            sendVerificationMail: function () {
                this.$store.dispatch('callCertMail', {idx: this.getMemberIdx}).then((response) => {
                    if (response.code == "OK") {
                        this.emailCertClose()
                    }
                }).catch((response) => {
                    console.log(response)
                })
            },
            sendTempPwd: function () {
                this.passwordSend = !this.passwordSend;
                this.$store.dispatch('sendTempPassword', {email: this.accountData.email}).then((response) => {
                    if (response.code == "OK") {
                        this.emailCertClose()
                    }
                }).catch((response) => {
                    console.log(response)
                })
            },
            changePasswordAction: function () {
                if (!this.validatePassword(this.oldPwd)) {
                    console.log("error1")
                    return;
                }
                if (!this.validatePassword(this.newPwd)) {
                    console.log("error2")
                    return;
                }
                if (this.newPwd != this.newCheck) {
                    console.log("error3")
                    return;
                }
                console.log("allpass");
                this.$store.dispatch('checkPwdAndChange', {
                    email: this.accountData.email,
                    userPwd: this.oldPwd,
                    newPassword: this.newPwd
                }).then((response) => {
                    console.log(response.code);
                    if (response.code == "OK") {
                        this.changePassword = !this.changePassword;
                    }
                }).catch((response) => {

                })
            },
            validatePassword: function (pass) {
                const re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[$@!%*#?&])[a-z0-9$@!%*#?&]{8,}$/;
                return re.test(String(pass).toLowerCase());
            },
            memberExitGo : function () {
                this.$store.dispatch('memberExitAct', {
                    idx : this.getMemberIdx
                }).then((response) => {
                    if (response.code == "OK") {
                        this.userExit = !this.userExit;
                        this.$store.dispatch('exitAfterLogout');
                    }
                }).catch((response) => {

                })
            }
        }
    }
</script>

<style scoped>
    .account_pwd {
        color: #111111;
    }
</style>
