<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="step_contents">
                    <div class="next_step_btn">
                        <router-link :to="{name : 'MainFeed' }" tag="a" @click="location.reload()">건너뛰기</router-link>
                    </div>
                    <h1 class="logo"><img src="../../../static/img/step_logo.png" alt="vora로고"></h1>
                    <h2>가입을 축하합니다<span>VORA와 함께하게 된 것을 환영해요!</span></h2>
                    <p class="txt01">가입하신 이메일 주소로 인증 메일을 발송해드렸어요</p>
                    <h3>당신의 취향에 따라 VORA 의 이야기는 달라집니다.<br>
                        VORA에게 당신의 취향을 알려주세요.</h3>
                    <form>
                        <fieldset>
                            <!--<legend>생년월일 입력</legend>-->
                            <div class="year">
                                <p class="tit">당신은 언제 태어났나요?</p>
                                <p class="input_box">
                                    <input type="tel" :class="ybool !='' ? 'bb_input' : ''" maxlength="1" title="생년월일"
                                           v-model="y" ref="year1st">
                                    <input type="tel" :class="ebool !='' ? 'bb_input' : ''" maxlength="1" title="생년월일"
                                           v-model="e" ref="year2nd">
                                    <input type="tel" :class="abool !='' ? 'bb_input' : ''" maxlength="1" title="생년월일"
                                           v-model="a" ref="year3th">
                                    <input type="tel" :class="rbool !='' ? 'bb_input' : ''" maxlength="1" title="생년월일"
                                           v-model="r" ref="year4th">
                                </p>
                            </div>
                            <div class="step_bar">
                                <ul>
                                    <li class="on"></li>
                                    <li></li>
                                    <li></li>
                                </ul>
                            </div>
                            <button type="button" :class="allCheckRtnCss()" @click="goStepTwo">계속하기</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'

    export default {
        name: 'StepOne',
        data: function () {
            return {
                y: '',
                e: '',
                a: '',
                r: '',
                ybool: false,
                ebool: false,
                abool: false,
                rbool: false,
                allCheck: false
            }
        },
        computed: {
            ...mapGetters([
                'getMemberIdx'
            ])
        },
        mounted: function () {
            this.$refs.year1st.focus();
        },
        methods: {
            ...mapActions([
                'saveUserYear'
            ]),
            goStepTwo: function () {
                if (this.allCheck) {

                    this.$store.dispatch('saveUserYear', {
                        idx: this.getMemberIdx,
                        year: this.y + this.e + this.a + this.r
                    }).then((response) => {
                        console.log(response);
                        if (response.code == "OK") {
                            this.$router.push({'name': 'StepTwo'});
                        }
                    }).catch((response) => {

                    })
                }
            },
            allCheckRtnCss : function () {
                return this.allCheck ? 'next' : 'step_go';
            },
            checkAllData: function(){
                if(this.y !='' && this.e !='' && this.a !='' && this.r !=''){
                    this.allCheck = true;
                }else{
                    this.allCheck = false;
                }
            }
        },
        watch: {
            y: function () {
                const regex = /[1~2]/gm;
                let m;

                if(this.y != ''){
                    while ((m = regex.exec(this.y)) !== null) {
                        // This is necessary to avoid infinite loops with zero-width matches
                        if (m.index === regex.lastIndex) {
                            regex.lastIndex++;
                        }

                        // The result can be accessed through the `m`-variable.
                        m.forEach((match, groupIndex) => {
                            this.ybool = true
                            this.$refs.year2nd.focus();
                        });
                    }
                }else{
                    this.ybool = false
                }
                this.checkAllData();
            },
            e: function () {
                let regex = '';
                if(this.y == '1'){
                    regex = /[9]/gm;
                }

                if(this.y == '2'){
                    regex = /[0]/gm;
                }


                let m;

                if(this.e !=''){
                    while ((m = regex.exec(this.e)) !== null) {
                        // This is necessary to avoid infinite loops with zero-width matches
                        if (m.index === regex.lastIndex) {
                            regex.lastIndex++;
                        }

                        // The result can be accessed through the `m`-variable.
                        m.forEach((match, groupIndex) => {
                            this.ebool = true
                            this.$refs.year3th.focus();
                        });
                    }
                }else{
                    this.ebool = false;
                    this.allCheck = false;
                }
                this.checkAllData();
            },
            a: function () {
                const regex = /[0-9]/gm;
                let m;

                if(this.a != ''){
                    while ((m = regex.exec(this.a)) !== null) {
                        // This is necessary to avoid infinite loops with zero-width matches
                        if (m.index === regex.lastIndex) {
                            regex.lastIndex++;
                        }

                        // The result can be accessed through the `m`-variable.
                        m.forEach((match, groupIndex) => {
                            this.abool = true
                            this.$refs.year4th.focus();
                        });
                    }
                }else{
                    this.abool = false;
                    this.allCheck = false;
                }
                this.checkAllData();
            },
            r: function () {
                const regex = /[0-9]/gm;
                let m;

                if(this.r != ''){
                    while ((m = regex.exec(this.r)) !== null) {
                        // This is necessary to avoid infinite loops with zero-width matches
                        if (m.index === regex.lastIndex) {
                            regex.lastIndex++;
                        }

                        // The result can be accessed through the `m`-variable.
                        m.forEach((match, groupIndex) => {
                            this.rbool = true;
                            this.allCheck = true;
                        });
                    }
                }else{
                    this.rbool = false;
                    this.allCheck = false;
                }
                this.checkAllData();
            }
        }
    }
</script>

<style scoped>

</style>
