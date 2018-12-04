<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_rgba">
            </div>
            <Modal v-if="inquirys">
                <div slot="body">
                <!-- 문의 및 제안하기 팝업 -->
                <div class="business_change_view popup">
                    <div class="business_change_view_wrap popup_wrap">
                        <div class="tit">
                            <p>문의 및 제안하기</p>
                            <span class="close" @click="inquirysClose"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                        </div>
                        <p class="link_txt">문의 및 제안 접수가 완료되었어요.<br>
                            담당자가 확인 후 빠른 시일내에<br>
                            작성하신 이메일(sample@mail.com)(으)로<br>
                            답변을 드릴게요. 감사합니다.</p>
                        <div class="btns">
                            <button type="button" class="btn02" @click="inquirysClose">확인</button>
                        </div>
                    </div>
                </div>
                </div>
            </Modal>
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container">
                        <div class="set_content">
                            <SideMenu></SideMenu>
                            <div class="right_con">
                                <h3>문의 및 제안을 주시면 최대한 빠른 시일 내 답변을 드리겠습니다.</h3>
                                <div class="set_business">
                                    <form>
                                        <fieldset>
                                            <legend>비즈니스계정 전환 폼</legend>
                                            <h4>문의 및 제안 항목</h4>
                                            <p>
                                                <select class="select01" v-model="type">
                                                    <option value="문의">문의하기</option>
                                                    <option value="제안">제안하기</option>
                                                    <option value="기타">기타</option>
                                                </select>
                                            </p>
                                            <h4>내용</h4>
                                            <p>
                                                <textarea placeholder="문의 및 제안내용을 작성해 주세요." v-model="content"></textarea>
                                            </p>
                                            <h4>답변 받을 이메일 주소</h4>
                                            <p>
                                                <input type="text" placeholder="답변 받을 이메일 주소" v-model="email">
                                            </p>
                                            <input type="checkbox" id="business_check" v-model="agree_check"><label for="business_check"><span>개인정보처리방침</span>에 동의합니다.</label>
                                            <div class="save_btns">
                                                <button type="button" @click="saveInquiry">보내기</button>
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
  import {mapState, mapActions, mapGetters} from 'vuex'
  import global from '../../global'

  import Modal from '../../components/inc/Modal'
  import SideMenu from '../../components/inc/SideMenu'
  export default {
    name: 'SetInquiry',
    components : {
      Modal, SideMenu
    },
    data : function () {
      return {
        inquirys : false,
        type : "문의",
        content : "",
        email : "",
        agree_check : false,
      }
    },
    methods : {
      ...mapActions(['saveInquiryAct']),
      inquirysClose : function () {
        this.inquirys = false
      },
      saveInquiry : function(){
        let payload = {
          "type":this.type,
          "content":this.content,
          "email":this.email,
          "agree_check":this.agree_check
        };
        if(this.agree_check){
          this.saveInquiryAct(payload).then((response)=>{
            if(response){
              this.inquirys = !this.inquirys
            }
          }).catch((error)=>{
          });
        }else{
          alert("동의해주세요");
        }
      }
    }
  }
</script>

<style scoped>

</style>
