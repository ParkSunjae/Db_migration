<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_rgba">
            </div>
            <modal v-if="changeBusiness">
                <div slot="body">
                    <!-- 비즈니스계정 전환 팝업 -->
                    <div class="business_change_view popup">
                        <div class="popup_wrap">
                            <div class="tit">
                                <p>비즈니스계정 전환</p>
                                <span class="close" @click="changeBusinessClose"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                            </div>
                            <p class="link_txt">비즈니스계정 전환이 완료되었어요.<br>
                                불법 계정의 경우 인증이 철회 될 수 있습니다.<br>
                                일반계정으로 전환을 원하시면<br>
                                문의 및 제안하기를 통해 문의해주세요.</p>
                            <div class="btns">
                                <button type="button" class="btn02" @click="changeBusinessClose">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <modal v-if="businessPop">
                <div slot="body">
                    <!-- 비즈니스계정 팝업 -->
                    <div class="business_view popup">
                        <div class="popup_wrap">
                            <div class="tit">
                                <p>비즈니스계정</p>
                                <span class="close" @click="businessPopClose"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                            </div>
                            <p class="link_txt">닉네임 님은 이미 비즈니스계정이에요.<br>
                                일반 계정으로 전환을 원하시면<br>
                                문의 및 제안하기를 통해 문의해주세요.<br>
                                감사합니다.</p>
                            <div class="btns">
                                <button type="button" class="btn02" @click="businessPopClose">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container">
                        <div class="set_content">
                            <SideMenu></SideMenu>
                            <div class="right_con">
                                <h3>비즈니스 계정으로 전환합니다.<br>
                                    <span>VORA는 당신의 비즈니스 성공을 기원합니다.</span></h3>
                                <div class="set_business">
                                    <form>
                                        <fieldset>
                                            <legend>비즈니스계정 전환 폼</legend>
                                            <h4>카테고리</h4>
                                            <p>
                                                <select class="select01" v-model="businessCategory1" @change="loadBusinessCategory2List">
                                                    <option disabled value="">카테고리 선택</option>
                                                    <option v-for="item in businessCategory1List" :value="item.idx">{{item.categoryName}}</option>
                                                </select>
                                                <select  v-model="businessCategory2">
                                                    <option disabled value="">하위 카테고리 선택</option>
                                                    <option v-for="item in businessCategory2List" :value="item.idx">{{item.categoryName}}</option>
                                                </select>
                                            </p>
                                            <h4>*공식명칭</h4>
                                            <span class="text_num">({{ businessName==null ? 0 : businessName.length }}/20)</span>
                                            <p>
                                                <input type="text" v-model="businessName" placeholder="공식명칭">
                                            </p>
                                            <h4>대표이메일</h4>
                                            <p>
                                                <input type="text" v-model="businessEmail" placeholder="대표이메일">
                                            </p>
                                            <h4>대표번호</h4>
                                            <p>
                                                <input type="text" v-model="businessTel" placeholder="대표번호">
                                            </p>
                                            <h4>웹사이트</h4>
                                            <p>
                                                <input type="text" v-model="businessSite" placeholder="웹사이트">
                                            </p>
                                            <input type="checkbox" id="business_check" v-model="businessAgree"><label for="business_check">비즈니스 계정 전환 시 <span>개인정보처리방침</span> · <span>비즈니스계정 이용약관</span>에 동의합니다</label>
                                            <div class="business_txt_box">
                                                <p>비즈니스 계정으로 전환하시면<br>프로필 정보를 마이피드에 보여줍니다.</p>
                                            </div>
                                            <div class="save_btns">
                                                <button type="button" @click="saveBusinessInfo">비즈니스계정 전환</button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="intro_txt">
                            <p><a href="#">VORA소개</a> · <a href="#">설정</a> · <a href="#">개인정보처리방침</a> · <a href="#">이용약관</a></p>
                            <p class="app_icon"><a href="#" class="iphone">iPhone 앱</a><a href="#" class="android">Android 앱</a></p>
                            <p class="copyright">Copyright @ KYOBO BOOK CENTRE.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Modal from '../../components/inc/Modal'
    import {mapState, mapActions, mapGetters} from 'vuex'
    import SideMenu from '../../components/inc/SideMenu'

    export default {
        name: 'SetBusiness',
        components : {
            Modal,
            SideMenu
        },
        computed: {
          ...mapGetters([
            'getMemberBusinessName',
            'getMemberBusinessEmail',
            'getMemberBusinessTel',
            'getMemberBusinessSite',
            'getMemberBusinessCategoryIdx',
            'getMemberBusinessSubCategoryIdx',
            'getMemberBusinessSite',
            'getMemberIdx',
            'getMemberBusinessYn',

            // business category list
            'getBusinessCategory1List',
            'getBusinessCategory2List'
          ])
        },
        data : function() {
            return {
                isBusiness : false,
                changeBusiness : false,
                businessPop : false,
                businessName : '',
                businessEmail : '',
                businessTel : '',
                businessSite : '',
                businessCategory1 : '',
                businessCategory2 : '',
                businessCategory1List : [],
                businessCategory2List : [],
                businessYn : '',
                businessAgree : false,
            }
        },
        created : function(){
          this.businessName = this.getMemberBusinessName;
          this.businessEmail = this.getMemberBusinessEmail;
          this.businessTel = this.getMemberBusinessTel;
          this.businessSite = this.getMemberBusinessSite;
          this.businessYn = this.getMemberBusinessYn;

          this.loadBusinessCategory1ListAct({
          }).then((response)=>{
            this.businessCategory1List = this.getBusinessCategory1List;
            this.businessCategory1 = this.getMemberBusinessCategoryIdx;
            if(this.getMemberBusinessCategoryIdx){
              this.loadBusinessCategory2List();
              this.businessCategory2 = this.getMemberBusinessSubCategoryIdx;
            }
          }).catch((error)=>{
          })
        },
        methods : {
            ...mapActions(
              [
                'updateMemberInfoAct',
                'loadBusinessCategory1ListAct',
                'loadBusinessCategory2ListAct'
              ]
            ),
            changeBusinessClose : function () {
              this.changeBusiness = false;
            },
            businessPopClose : function () {
              this.businessPop = false;
            },
            saveBusinessInfo : function(){
              if(this.businessAgree){
                if(this.businessYn!='Y'){
                  this.updateMemberInfoAct(
                    {
                      'idx':this.getMemberIdx,
                      'businessName':this.businessName,
                      'businessEmail':this.businessEmail,
                      'businessTel':this.businessTel,
                      'businessSite':this.businessSite,
                      'businessCategoryIdx':this.businessCategory1,
                      'businessSubCategoryIdx':this.businessCategory2,
                      'businessYn':'Y',
                    }
                  ).then((response) => {
                    this.changeBusiness = !this.changeBusiness;
                  }).catch((error)=>{
                  });
                }else{
                  this.businessPop = !this.businessPop;
                }
              } else {
                alert("개인정보처리방침 · 비즈니스계정 이용약관에 동의해주세요");
              }

            },
            loadBusinessCategory2List: function(){
              this.loadBusinessCategory2ListAct({'parentIdx':this.businessCategory1}).then((response)=>{
                this.businessCategory2List = this.getBusinessCategory2List[this.businessCategory1];
              }).catch((error)=>{
              });
            }
        }
    }
</script>

<style scoped>

</style>
