<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <Modal v-if="duplicateMail">
                <div slot="body">
                    <div class="join_alert">
                        <div class="join_alert_wrap">
                            <p @click="closeAndFocudSet">이미 사용중인 이메일입니다.</p>
                        </div>
                    </div>
                </div>
            </Modal>
            <Modal v-if="duplicateNick">
                <div slot="body">
                    <div class="join_alert">
                        <div class="join_alert_wrap">
                            <p @click="close">이미 사용중인 닉네임입니다.</p>
                        </div>
                    </div>
                </div>
            </Modal>
            <Modal v-if="term">
                <div slot="body">
                    <div class="agree_view">
                        <div class="view_wrap">
                            <span class="close" @click="closeTerm"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                            <form>
                                <fieldset>
                                    <!--<legend>약관동의 폼</legend>-->
                                    <h2>약관동의</h2>
                                    <h3>개인정보수집이용(필수사항)</h3>
                                    <div class="agree_box" v-html="termText">

                                    </div>
                                    <div class="agree_input_box">
                                        <span class="input">
                                            <input type="radio" id="agree01" name="agree1" value="agree"
                                                   v-model="checkTerm1">
                                            <label for="agree01">
                                                <span></span>동의
                                            </label>
                                        </span>
                                        <span class="input">
                                            <input type="radio" id="agree02" name="agree1" value="deagree"
                                                   v-model="checkTerm1">
                                            <label for="agree02">
                                                <span></span>미동의
                                            </label>
                                        </span>
                                    </div>
                                    <h3>마케팅활용/개인정보수집이용 (선택사항)</h3>
                                    <div class="agree_box" v-html="privacyText">

                                    </div>
                                    <div class="agree_input_box">
                                        <span class="input">
                                            <input type="radio" id="agree03" name="agree2" value="agree"
                                                   v-model="checkTerm2">
                                            <label for="agree03">
                                                <span></span>동의
                                            </label>
                                        </span>
                                        <span class="input">
                                            <input type="radio" id="agree04" name="agree2" value="deagree"
                                                   v-model="checkTerm2">
                                            <label for="agree04">
                                                <span></span>미동의
                                            </label>
                                        </span>
                                    </div>
                                    <div class="agree_all">
                                        <span class="input02">
                                            <input type="checkbox" id="all_check" name="all_check" value="checked"
                                                   v-model="allcheck" @change="allTerm">
                                            <label for="all_check">
                                                <span></span>위의 모든 약관에 동의합니다
                                            </label>
                                        </span>
                                    </div>
                                    <div class="btns">
                                        <button type="button" class="btn01" @click="closeTerm">취소</button>
                                        <button type="button" class="btn02" @click="goSaveMember">확인</button>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </Modal>
            <div class="bg_grey">
                <div class="join_contents">
                    <h1 class="logo"><img src="../../../static/img/login_logo.png" alt="vora로고"></h1>
                    <form>
                        <fieldset>
                            <!--<legend>vora join</legend>-->
                            <div id="emailDiv" class="join_input">
                                <input type="text" placeholder="이메일 주소" title="이메일주소" v-model="email" ref="mailinput">
                                <span class="email_check" v-if="checkEmail"></span>
                            </div>
                            <div id="nicknameDiv" class="join_input">
                                <input type="text" placeholder="닉네임" title="닉네임" v-model="nickName" ref="nickinput"
                                       @focus="checkMaildup">
                                <span class="email_check" v-if="nickNameCheck"></span>
                                <!--<span class="nick_check" v-else></span>-->
                            </div>
                            <div id="pwDiv1" class="join_input">
                                <input type="password" placeholder="비밀번호(8자 이상 문자, 숫자, 특수문자 조합 필수)" title="비밀번호"
                                       v-model="userPwd" @focus="nickNameChecker()">
                                <span class="pw_check1" v-if="checkPassPattern"></span>
                            </div>
                            <div id="pwDiv2" class="join_input">
                                <input type="password" placeholder="비밀번호 확인" title="비밀번호확인" v-model="checkPwd">
                                <span class="pw_check2" v-if="checkPassMatch" @focus="nickNameChecker()"></span>
                            </div>
                            <div class="login_btns">
                                <p class="txt01">회원가입 시 <span class="argee_box_open"
                                                              @click="term = !term">개인정보처리방침·이용약관</span>에 동의하게 됩니다.<br>
                                    (만 14세 미만 아동은 회원가입이 제한됩니다)</p>
                                <button type="button" class="btn01" @click="goSingUp">동의하고 회원가입</button>
                                <p class="txt02">이미 회원이신가요?
                                    <router-link :to="{name : 'Login' }" class="write_icon" tag="a">로그인</router-link>
                                </p>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Modal from '../../components/inc/Modal'
    import {mapState, mapGetters, mapActions} from 'vuex'

    export default {
        name: "Join",
        components: {
            Modal
        },
        data: function () {
            return {
                email: "",
                userPwd: "",
                deviceType: "WEB",
                accountType: "email",
                nickName: "",
                emailCertYn: "",
                userStatus: "",
                checkPwd: "",
                checkEmail: false,
                nickNameCheck: false,
                checkPassPattern: false,
                checkPassMatch: false,
                duplicateNick: false,
                term: false,
                checkTerm1: "deagree",
                checkTerm2: "deagree",
                allcheck: "",
                termText: "",
                privacyText: "",
                duplicateMail: false
            }
        },
        created: function () {
            this.findTerms();
        },
        methods: {
            closeAndFocudSet: function () {
                this.duplicateMail = !this.duplicateMail;
                this.moveMailFocus();
            },
            findTerms: function () {
                this.$store.dispatch('findLastestTerms').then((response) => {
                    if (response.returnData[0].type == 't') {
                        this.termText = response.returnData[0].termsContents;
                    }
                    if (response.returnData[1].type == 'p') {
                        this.privacyText = response.returnData[1].termsContents;
                    }
                }).catch((response) => {

                });
            },
            goSaveMember: function () {
                if (this.checkTerm1 == 'agree') {
                    //TODO saveMember
                    let signupRequest = {
                        account: {
                            accountType: this.accountType,
                            deviceType: this.deviceType,
                            email: this.email,
                            userPwd: this.userPwd
                        },
                        member: {
                            nickName: this.nickName
                        }
                    }

                    this.$store.dispatch('apiJoinUserAct', signupRequest).then((response) => {
                        this.$router.push({'name': 'StepOne'});
                    }).catch((error) => {
                        console.log(error.response);
                    });


                }
            },
            allTerm: function () {
                if (this.allcheck) {
                    this.checkTerm1 = "agree";
                    this.checkTerm2 = "agree";
                } else {
                    this.checkTerm1 = "deagree";
                    this.checkTerm2 = "deagree";
                }
            },
            close: function () {
                this.duplicateNick = false
                this.moveNickFocus();
            },
            closeTerm: function () {
                this.term = false
            },
            goSingUp: function () {
                if (this.checkEmail && this.nickNameCheck && this.checkPassPattern && this.checkPassMatch) {
                    this.term = true;
                }
            },
            validateEmail: function (email) {
                let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(String(email).toLowerCase());
            },
            nickNameChecker: function () {
                if (this.nickName != "") {
                    this.nickName = this.nickName.replace(/ /gi, "");
                    this.$store.dispatch('checkUserNickName', {'nickName': this.nickName.replace(/^\s+|\s+$/g, "")}).then((response) => {

                        if (response.returnData.nickNameCheck) {
                            this.nickNameCheck = true;
                            this.duplicateNick = false;
                        } else {
                            this.nickNameCheck = false;
                            this.duplicateNick = true;
                        }

                    }).catch((error) => {
                        // console.log(error.response);
                    });
                } else {
                    this.moveNickFocus();
                }

            },
            validatePassword: function (pass) {
                let re = /^(?=.*[0-9])(?=.*[a-z])(?=.*[$@!%*#?&])[a-z0-9$@!%*#?&]{8,}$/;
                return re.test(String(pass).toLowerCase());
            },
            moveNickFocus: function () {
                this.$refs.nickinput.focus();
            },
            moveMailFocus: function () {
                this.$refs.mailinput.focus();
            },
            checkMaildup: function () {
                if (this.email != '') {
                    this.$store.dispatch('checkUserEmail', {email: this.email}).then((response) => {
                        if (response.returnData.mailCheck) {
                            this.duplicateMail = false;
                            this.checkEmail = true;
                        } else {
                            this.duplicateMail = true;
                            this.checkEmail = false;
                        }
                    }).catch((response) => {

                    })
                } else {
                    this.moveMailFocus();
                }
            },
            ...mapActions([
                'checkUserNickName',
                'apiJoinUserAct',
                'findLastestTerms',
                'checkUserEmail'
            ])
        },
        watch: {
            email: function (value) {
                if (this.validateEmail(value)) {
                    this.checkEmail = true;
                } else {
                    this.checkEmail = false;
                }
            },
            userPwd: function (value) {
                if (this.validatePassword(value)) {
                    this.checkPassPattern = true;
                } else {
                    this.checkPassPattern = false;
                }
            },
            checkPwd: function (value) {
                if (this.userPwd == value) {
                    this.checkPassMatch = true
                } else {
                    this.checkPassMatch = false
                }
            }
        }
    }
</script>
<style scoped>

</style>
