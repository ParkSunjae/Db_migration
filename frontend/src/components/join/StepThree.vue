<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="step_contents">
                    <div class="next_step_btn">
                        <router-link :to="{name : 'MainFeed' }" tag="a">건너뛰기</router-link>
                    </div>
                    <h1 class="logo"><img src="../../../static/img/step_logo.png" alt="vora로고"></h1>
                    <h2>나와 비슷한 취향을 가진 사용자를 만나보세요</h2>
                    <h3>팔로우를 통해 다양한 사람들과 이야기를 나눌 수 있습니다.</h3>
                    <form>
                        <fieldset>
                            <legend>팔로우 선택</legend>
                            <div class="follow_box">
                                <div class="profile" v-for="users in findUsers">
                                    <div class="img">
                                        <img v-bind:src="getProfileImage(users.profileInfo)" alt="프로필 이미지">
                                    </div>
                                    <div class="con">
                                        <p class="id">{{users.nickName}}</p>
                                        <p class="info">{{users.categoryNameChild}} · 팔로워 {{users.followerCount}}</p>
                                    </div>
                                    <div class="btn">
                                        <button type="button" :class="users.followBool ? 'on' : ''" @click="toggleFollowUsers(users)">팔로우</button>
                                    </div>
                                </div>
                            </div>
                            <div class="step_bar">
                                <ul>
                                    <li class="on"></li>
                                    <li class="on"></li>
                                    <li class="on"></li>
                                </ul>
                            </div>
                            <button type="button" class="step_end" @click="goMain">시작하기</button>
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
    name: 'StepThree',
    computed : {
      ...mapGetters([
      	'getSavedTags',
        'getSuggestUsers',
        'getMemberIdx'
      ])
    },
    created : function () {
	    this.findAdminCheckUser();
    },
    data : function () {
    	return {
            findUsers : [],
            selectedUser : [],
            memberFollowMember : []
        }
    },
    methods : {
      ...mapActions([
        'getTags',
        'getAdminCheckYn',
        'saveFollowers',
        'getFollowers'
      ]),
      findAdminCheckUser : function () {
        this.$store.dispatch('getAdminCheckYn',{tags : this.getSavedTags, adminRecommendList : this.findUsers}).then((response)=> {
	        this.findUsers = response.returnData;
	        this.findSameFavorite()
        }).catch((response)=>{

        });
      },
      findSameFavorite : function () {
      	let names = [];
      	for(let i=0; i<this.findUsers.length; i++){
      		names.push(this.findUsers[i].idx)
        }
        this.$store.dispatch('getFollowers',{tags : this.getSavedTags, adminRecommendList : names}).then((response)=>{
            for(let i=0; i<response.memberInfo.length; i++){
                this.findUsers.push(response.memberInfo[i]);
            }
        }).catch((error)=>{
            console.log(error);
        })
      },
      toggleFollowUsers : function (user) {
        user.followBool = !user.followBool;
        if(user.followBool){
	        this.selectedUser.push(user);
        }else{
	        for(let i=0; i<this.selectedUser.length; i++){
	        	if(user.idx == this.selectedUser[i].idx){
	        		this.selectedUser.splice(i, 1);
                }
            }
        }
      },
      goMain : function () {
      	for(let i=0; i<this.selectedUser.length; i++){
		      let data = {
			      oidx : this.getMemberIdx,
                  tidx : this.selectedUser[i].idx
		      }
      		this.memberFollowMember.push(data);
        }
        this.memberFollowMember = Array.from(this.memberFollowMember.reduce((m, t) => m.set(t.tidx, t), new Map()).values());

        this.$store.dispatch('saveFollowers', this.memberFollowMember).then((response)=>{
        	if(response.code == 'OK'){
                this.$router.push({'name': 'MainFeed'});
            }
        }).catch({

        })

    },
    getProfileImage: function (imgObj) {
        if (imgObj)
            return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
        else
            return '../../../static/img/no_image.png';
    },
    }
  }
</script>

<style scoped>

</style>
