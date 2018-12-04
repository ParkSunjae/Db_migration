<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <Modal v-if="passwordSend">
                <div slot="body">
                    <!-- 임시 비밀번호 발송 팝업 -->
                    <div class="pw_send_view popup">
                        <div class="pw_send_wrap popup_wrap">
                            <div class="tit">
                                <p>임시 비밀번호 발송</p>
                                <span class="close" @click="passwordSend = !passwordSend"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                            </div>
                            <p class="link_txt">{{email}} (으)로<br>
                                임시 비밀번호를 발송해드렸어요.</p>
                            <div class="btns">
                                <button type="button" class="btn02" @click="goLogin">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </Modal>
            <!-- 임시 비밀번호 발송 가입되지 않은 이메일 등록시 팝업 -->
            <Modal v-if="notJoinUser">
                <div slot="body">
                    <div class="pw_send_view02">
                        <div class="pw_send_wrap02">
                            <div class="tit">
                                <p>임시 비밀번호 발송</p>
                                <span class="close" @click="notJoinUser = !notJoinUser"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                            </div>
                            <p class="link_txt">{{email}} (은)는<br>
                                가입되지 않은 이메일이에요.<br>
                                다시 한번 확인해주세요.</p>
                            <div class="btns">
                                <button type="button" class="btn02" @click="notJoinUser = !notJoinUser">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </Modal>
            <div class="bg_grey">
                <div class="re_member_contents">
                    <h1 class="logo"><img src="../../../static/img/login_logo.png" alt="vora로고"></h1>
                    <h2 class="re_txt01">임시 비밀번호 발송</h2>
                    <h3 class="re_txt02">가입하신 이메일 주소를 입력하시면 임시 비밀번호를 발송해드려요</h3>
                    <form>
                        <fieldset>
                            <!--<legend>임시 비밀번호 발송 이메일주소 입력</legend>-->
                            <div id="emailDiv_re" class="join_input">
                                <input type="text" placeholder="이메일 주소" title="이메일 주소" v-model="email">
                                <span class="email_check"></span>
                            </div>
                            <div class="login_btns">
                                <button type="button" class="btn01" @click="sendMailService">임시 비밀번호 발송</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Modal from '../inc/Modal'
    import {mapActions} from 'vuex'

    export default {
        name: 'PasswordSend',
        components: {
            Modal
        },
        data: function () {
            return {
                passwordSend: false,
                notJoinUser : false,
                email : ""
            }
        },
        methods : {
            ...mapActions([
                'sendTempPassword',
                'checkUserMailAct'
            ]),
            validateEmail : function (email) {
                const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(String(email).toLowerCase());
            },
            sendMailService : function () {

                if(this.validateEmail(this.email)){
                    this.$store.dispatch('checkUserMailAct', {email : this.email}).then((response)=>{
                        console.log("success",response);
                        if(response.code == "OK"){
                            this.passwordSend = !this.passwordSend;
                            if(this.validateEmail(this.email)){
                                this.$store.dispatch('sendTempPassword', {email : this.email}).then((response)=>{
                                    if(response.code == "OK"){
                                    }
                                }).catch((response)=>{
                                    console.log(response)
                                })
                            }
                        }else{
                            this.notJoinUser = !this.notJoinUser;
                        }
                    }).catch((response)=>{
                        console.log("error",response)
                    })
                }
            },
            goLogin : function () {
                this.passwordSend = !this.passwordSend;
                this.$router.push({name : 'Login'});
            }
        }
    }
</script>

<style scoped>

</style>
