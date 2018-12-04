<template>
    <Modal v-if="deleteObjectInfo">
        <div slot=body>
            <!-- 삭제하기 팝업 -->
            <div class="delete_view">
                <div class="delete_wrap">
                    <div class="tit">
                        <p>삭제하기</p>
                        <span class="close" @click="close()"><img
                            src="../../../static/img/esc_icon.png" alt="닫기"></span>
                    </div>
                    <p class="block_txt" v-if="objectType=='feed'">이 개시물을 삭제하시겠어요?</p>
                    <p class="block_txt" v-if="objectType=='comment'">이 댓글을 삭제하시겠어요?</p>
                    <p class="block_txt" v-if="objectType=='commentMaster'">이 댓글을 삭제하시겠어요? 삭제시 하위 댓글도 같이 삭제됩니다.</p>
                    <div class="btns">
                        <button type="button" class="btn01" @click="close()">취소</button>
                        <button type="button" class="btn02" @click="doDeleteFeed()">삭제하기</button>
                    </div>
                </div>
            </div>
        </div>
    </Modal>
</template>

<script>
import Modal from '../inc/Modal'
export default {
    name:"DeleteModal",
    props:['afterFunction'],
    components : {
        Modal
    },
    data : function(){
        return {
            deleteObjectInfo : false,
            objectType : "",
        }
    },
    methods : {
        close: function(){
            this.deleteObjectInfo = false;
            this.objectType = "";
        },
        show : function(object,objectType){
            this.deleteObjectInfo = object;
            this.objectType = objectType;
        },
        doDeleteFeed : function (){
            let action;
            if(this.objectType=="feed")
                action = "deleteFeedAct";
            else
                action = "deleteCommentAct"
            this.$store.dispatch(action,this.deleteObjectInfo).then((response)=>{
                this.afterFunction(this.objectType);
                this.close();
            }).catch((response)=>{
            });
        }
    }
}
</script>
<style scoped>

</style>
