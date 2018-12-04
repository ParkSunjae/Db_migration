<template>
    <Modal v-if="blockUser">
        <div slot=body>
            <!-- 차단하기 팝업 -->
            <div class="block_view">
                <div class="block_wrap">
                    <div class="tit">
                        <p>차단하기</p>
                        <span class="close" @click="close"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                    </div>
                    <p class="block_txt">‘{{blockUser.nickName}}’님을 차단하시겠습니까?<br>
                        차단하시면 ‘{{blockUser.nickName}}’님의 게시물을 받아볼 수 없습니다.</p>
                    <div class="btns">
                        <button type="button" class="btn01" @click="close">취소</button>
                        <button type="button" class="btn02" @click="toggleBlockMemberAction">차단하기</button>
                    </div>
                </div>
            </div>
        </div>
    </Modal>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import Modal from '../inc/Modal'

export default {
    name: 'BlockMemberModal',
    components : {
        Modal
    },
    data: function(){
        return {
            blockUser : false,
        }
    },
    methods : {
        show : function(member){
            this.blockUser = member;
        },
        toggleBlockMemberAction: function(){
            this.$store.dispatch('toggleMemberBlocmMemberAct',this.blockUser.idx);
            this.blockUser = false;
        },
        close : function(){
            this.blockUser = false;
        }
    }
}
</script>
<style scoped>

</style>
