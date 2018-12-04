<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="intro_contents">
                    <h1 class="logo"><img src="../../static/img/intro_logo_201809281.png" alt="vora로고"></h1>
                    <p class="txt01">이미 회원이신가요? <router-link to="/Login" tag="a" v-text="'로그인'"></router-link></p>
                    <div class="login_btns">
                        <button type="button" class="btn01" @click="callSocial">소셜 아이디로 시작하기</button>
                        <router-link to="/Login" tag="button" class="btn02"><span>이메일로 시작하기</span></router-link>
                    </div>
                    <div class="intro_txt">
                        <p><a href="#">VORA소개</a> · <a href="#">개인정보처리방침</a> · <a href="#">이용약관</a></p>
                        <p class="app_icon"><a href="#" class="iphone">iPhone 앱</a><a href="#" class="android">Android 앱</a></p>
                        <p class="copyright">Copyright @ KYOBO BOOK CENTRE.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>

    export default {
    name: 'Intro',
    components : {
    },
    data : function () {
        return {

        }
    },
    methods : {
      callSocial : function () {
            this.social = true;
      },
      close : function () {
            this.social = false;
      }
    }
  }
</script>

<style scoped>
</style>
