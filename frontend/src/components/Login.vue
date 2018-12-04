<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <modal v-if="duplicationId">
                <div slot="body">
                    <div class="id_notice_view">
                        <div class="id_notice_wrap">
                            <div class="tit">
                                <p>동일 아이디 동시 사용 알림</p>
                                <span class="close"><img src="../../static/img/esc_icon.png" alt="닫기"></span>
                            </div>
                            <p class="link_txt">회원님과 동일한 아이디로 로그인한 이력이 있어요.<br>
                                본인이 아닌 경우 악의적인 사용일 수 있으니 비밀번호를<br>
                                변경하시고 문의사항이 있으신 경우 도움말 또는<br>
                                문의 및 제안하기를 통해 문의해주세요</p>
                            <div class="btns">
                                <button type="button" class="btn01">접속 IP 조회</button>
                                <button type="button" class="btn02">비밀번호 변경</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <!-- 비활성화 계정 팝업-->
            <modal v-if="nonSignUp">
                <div slot="body">
                    <div class="id_stop_view">
                        <div class="id_stop_wrap">
                            <div class="tit">
                                <p>비활성화 계정</p>
                                <span class="close"><img src="../../static/img/esc_icon.png" alt="닫기"></span>
                            </div>
                            <p class="link_txt">계정 사용이 중지되었어요.<br>
                                문의사항이 있으신 경우 도움말 또는<br>
                                문의 및 제안하기를 통해 문의해주세요</p>
                            <div class="btns">
                                <button type="button" class="btn01">확인</button>
                                <button type="button" class="btn02">문의 및 제안하기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <!-- 계정사용 정지 팝업 -->
            <modal v-if="stopId">
                <div slot="body">
                    <div class="pw_send_view popup">
                        <div class="popup_wrap">
                            <div class="tit">
                                <p>계정 정지</p>
                                <span class="close" @click="closeStopId"><img src="../../static/img/esc_icon.png"
                                                                              alt="닫기"></span>
                            </div>
                            <p class="link_txt">회원님의 계정 사용이 정지되었습니다.<br>
                                관련해서 질문이 있으시면 고객센터로 문의해주세요.</p>
                            <div class="btns">
                                <button type="button" class="btn02">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <modal v-if="idPasswordCheck">
                <div slot="body">
                    <div class="pw_send_view popup">
                        <div class="popup_wrap">
                            <div class="tit">
                                <p>로그인 실패</p>
                                <span class="close" @click="closeFail">
                                    <img src="../../static/img/esc_icon.png" alt="닫기">
                                </span>
                            </div>
                            <p class="link_txt">로그인에 실패하였습니다.<br>
                                아이디 또는 비밀번호를 확인하세요</p>
                            <div class="btns">
                                <button type="button" class="btn02" @click="closeFail">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <div class="bg_grey">
                <div class="login_contents">
                    <h1 class="logo">
                        <span class="logo_txt01">취향맞춤 커뮤니티</span>
                        <img src="../../static/img/login_logo.png" alt="vora로고">
                        <span class="logo_txt02">당신의 취향을 이야기 합니다.</span>
                    </h1>
                    <form>
                        <fieldset>
                            <!--<legend>vora login</legend>-->
                            <input type="text" placeholder="이메일 주소" title="이메일 주소" v-model="email">
                            <input type="password" placeholder="비밀번호" title="비밀번호" v-model="userPwd"
                                   @keyup.enter="goLogin">
                        </fieldset>
                    </form>
                    <div class="login_btns">
                        <button type="button" class="btn01" @click="goLogin">로그인</button>
                    </div>
                    <div class="join_password">
                        <p>
                            <router-link :to="{path : '/Join' }" class="write_icon" tag="a"
                                         v-text="'회원가입'"></router-link>
                            <router-link :to="{path : '/PasswordSend' }" class="write_icon" tag="a"
                                         v-text="'비밀번호 찾기'"></router-link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import http from '../http'
    import Modal from "./inc/Modal"
    import {mapState, mapActions, mapGetters} from 'vuex'
    import global from '../global'

    export default {
        name: "Login",
        components: {
            Modal
        },
        data: function () {
            return {
                duplicationId: false,
                nonSignUp: false,
                stopId: false,
                idPasswordCheck: false,
                email: "",
                userPwd: "",
                deviceType: "",
                wrongCount : 1
            }
        },
        created: function () {
            this.checkType();
            console.log(this.deviceType);
        },
        methods: {
            ...mapActions([
                'setDeviceInfo'
            ]),
            closeFail: function () {
                this.idPasswordCheck = !this.idPasswordCheck;
            },
            goLogin: function () {
                if (this.email != "" && this.userPwd != "") {
                    let account = {
                        email: this.email,
                        userPwd: this.userPwd,
                        deviceType: this.deviceType
                    }

                    http.createAxios().post(global.baseURI + global.api.post.member.signin, account, global.config).then((response) => {
                        console.log(response.data);
                        if (response.data.code == "OK") {
                            //인증코드 검증 성공시
                            if (response.data.returnData.accessToken) {
                                this.$store.commit('login', response.data.returnData);
                            }
                            this.$router.push({'name': 'MainFeed'});
                            // this.$router.push({'name': 'StepTwo'});
                        } else if (response.data.code == "SYSTEM_DB_FIND_FAIL") {
                            this.idPasswordCheck = true;
                            this.wrongCount = this.wrongCount + 1;
                            if(this.wrongCount == 5){
                                this.$router.push({name : 'PasswordSend'})
                            }
                        } else if (response.data.code == "COMMON_LOGIN_FAIL_EXPIRE_CERT_NUM") {

                        } else {

                        }
                    }).catch(response => {
                        console.log(response);
                    });
                }
            },
            closeStopId: function () {
                this.stopId = false;
            },
            checkType: function () {
                let mobile = (/iphone|ipad|ipod|android/i.test(navigator.userAgent.toLowerCase()));
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
                console.log("dispatch Login", this.deviceType);
                this.$store.dispatch('setDeviceInfo', this.deviceType);

            }
        }

    }
</script>

<style scoped>

</style>
