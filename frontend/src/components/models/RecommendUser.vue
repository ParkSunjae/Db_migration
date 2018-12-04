<template>
    <div class="my_follow">
        <h3 class="tit">VORA 추천 이용자</h3>
        <ul>
          <li v-for="item in userRecommendPC">
            <div class="profile">
              <div class="img">
                <a @click="findMemberByIdx(item.idx)">
                  <img v-bind:src="getProfileImage(item.profileInfo)">
                </a>
              </div>
              <div class="con">
                <a v-bind:class="{'vora_icon':item.businessYn=='Y','id': item.businessYn=='N'}" @click="findMemberByIdx(item.idx)">
                  {{item.nickName}}
                </a>
                  <div v-if="getMemberIdx != item.idx">
                    <FollowButton :member="item" />
                  </div>
              </div>
            </div>
          </li>
        </ul>
    </div>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import FollowButton from './FollowButton'

export default {
    name:"RecommendUser",
    components : {
        FollowButton
    },
    data : function(){
        return {
            userRecommendPC:[],
        }
    },
    computed : {
        ...mapGetters([
            'getNewestUserRecommendPC',
            'getMemberIdx'
        ])
    },
    methods : {
        ...mapActions([
            'loadMemberInfoAct',
            'loadUserRecommendPCAct',
        ]),
        findMemberByIdx : function(idx){
            this.$router.push({'name': 'MemberFeed',params:{memberIdx:idx}});
        },
        getProfileImage : function(imgObj){
          if(imgObj)
            return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
          else
            return '../../../static/img/no_image.png';
        },
        loadRecommendUserData : function(){
            this.$store.dispatch('loadUserRecommendPCAct').then((response) => {
                this.userRecommendPC = this.getNewestUserRecommendPC;
            }).catch((error) => {
            });
        }
    },
}
</script>
<style scoped>

</style>
