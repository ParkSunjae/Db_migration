<template>
    <Modal v-if="report">
        <div slot=body>
            <!-- 신고하기 팝업 -->
            <div class="report_view">
                <div class="report_wrap">
                    <div class="tit">
                        <p>신고하기</p>
                        <span class="close" @click="close"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                    </div>
                    <form>
                        <fieldset>
                            <legend>신고하기</legend>
                            <p class="report_txt">신고 사유를 선택해주세요</p>
                            <div class="report_kind">
                                    <span class="input">
                                        <input type="radio" id="report_kind01" name="report_kind" value="불법 혹은 음란성" v-model="reportType">
                                        <label for="report_kind01">
                                            <span></span>불법 혹은 음란성
                                        </label>
                                    </span>
                                <span class="input">
                                        <input type="radio" id="report_kind02" name="report_kind" value="부적합한 스팸성" v-model="reportType">
                                        <label for="report_kind02">
                                            <span></span>부적합한 스팸성
                                        </label>
                                    </span>
                                <span class="input">
                                        <input type="radio" id="report_kind03" name="report_kind" value="타인에게 피해 혹은 폭력성" v-model="reportType">
                                        <label for="report_kind03">
                                            <span></span>타인에게 피해 혹은 폭력성
                                        </label>
                                    </span>
                                <span class="input">
                                        <input type="radio" id="report_kind04" name="report_kind" value="기타" v-model="reportType">
                                        <label for="report_kind04">
                                            <span></span>기타
                                        </label>
                                    </span>
                            </div>
                            <div class="btns">
                                <button type="button" class="btn01" @click="close">취소</button>
                                <button type="button" class="btn02" @click="saveAlert">신고하기</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </Modal>
</template>

<script>
import Modal from '../inc/Modal'
export default {
    name:"AlertModal",
    props:['afterFunction'],
    components : {
        Modal
    },
    data: function(){
        return {
            report : false,
            reportContentType : "",
            reportType : "",
        }
    },
    methods : {
        show : function(object,type){
            this.report = object;
            this.reportContentType = type;
        },
        saveAlert : function(){
            if(this.reportContentType=='feed'){
                this.saveAlertFeed();
            }else if(this.reportContentType=='comment'){
                this.saveAlertFeedComment();
            }
        },
        saveAlertFeed : function(){
            this.$store.dispatch('saveFeedsReportAct',{'feedsIdx':this.report.idx,'type':this.reportType})
            .then((response)=>{
                this.reportType="";
                this.reportContentType="";
                this.close();
            }).catch((error)=>{
            });
        },
        saveAlertFeedComment : function(){
            this.$store.dispatch('saveFeedCommentReportAct',{'feedCommentIdx':this.report.idx,'type':this.reportType})
            .then((response)=>{
                this.reportType="";
                this.reportContentType="";
                this.close();
            }).catch((error)=>{
            });
        },
        close : function(){
            this.report = false;
        }
    }
}
</script>
<style scoped>

</style>
