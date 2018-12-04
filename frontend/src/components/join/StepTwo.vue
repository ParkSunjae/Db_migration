<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="step_contents">
                    <div class="next_step_btn">
                        <router-link :to="{name : 'MainFeed' }" tag="a">건너뛰기</router-link>
                    </div>
                    <h1 class="logo"><img src="../../../static/img/step_logo.png" alt="vora로고"></h1>
                    <h2>VORA 당신이 좋아하는 문화생활</h2>
                    <h3>관심있는 주제를 선택하여 나만의 피드를 만들어보세요</h3>
                    <form>
                        <fieldset>
                            <!--<legend>태그 선택</legend>-->
                            <div class="tag_box">
                                <p>
                                    <button type="button" v-for="(tag, index) in getTags" v-if="index < 20">
                                        <span :class="tag.selected ? 'on' : ''" @click="checkTagsToggle(tag)">#{{tag.tag}}</span>
                                    </button>
                                </p>
                                <p>
                                    <button type="button" v-for="(tag, index) in getTags" v-if="index > 19 && index < 40">
                                        <span :class="tag.selected ? 'on' : ''" @click="checkTagsToggle(tag)">#{{tag.tag}}</span>
                                    </button>
                                </p>

                                <p>
                                    <button type="button" v-for="(tag, index) in getTags" v-if="index > 39 && index < 60">
                                        <span :class="tag.selected ? 'on' : ''" @click="checkTagsToggle(tag)">#{{tag.tag}}</span>
                                    </button>
                                </p>
                                <p>
                                    <button type="button" v-for="(tag, index) in getTags" v-if="index > 59 && index < 80">
                                        <span :class="tag.selected ? 'on' : ''" @click="checkTagsToggle(tag)">#{{tag.tag}}</span>
                                    </button>
                                </p>
                                <p>
                                    <button type="button" v-for="(tag, index) in getTags" v-if="index > 79 && index < 100">
                                        <span :class="tag.selected ? 'on' : ''" @click="checkTagsToggle(tag)">#{{tag.tag}}</span>
                                    </button>
                                </p>
                            </div>
                            <div class="step_bar">
                                <ul>
                                    <li class="on"></li>
                                    <li class="on"></li>
                                    <li></li>
                                </ul>
                            </div>
                            <button type="button":class="saveTags.length > 0 ? 'step_go_success' : 'step_go'" @click="goStepThree">계속하기</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import { mapGetters, mapActions} from 'vuex'
  export default {
    name: 'StepTwo',
    data : function() {
    	return {
    		page : 1,
            limit : 100,
            saveTags : [],
            toggle : false
      }
    },
    computed : {
        ...mapGetters([
            'getTags',
          'getMemberIdx'
        ])
    },
    created : function () {
	    this.calltags();
    },
    methods : {
      ...mapActions([
        'setTagsAct',
        'setSavedTagsAct'
      ]),
      calltags : function () {
        this.$store.dispatch('setTagsAct', { page : this.page, limit : this.limit});
      },
      checkTagsToggle : function(tag) {
      	tag.selected = !tag.selected;
      	if(tag.selected){
      		this.saveTags.push(tag);
        }else{
		    for(let i=0; i<this.saveTags.length; i++){
		    	if(this.saveTags[i].idx == tag.idx){
                    console.log(this.saveTags[i].idx,tag.idx)
                    this.saveTags.splice(i, 1);
                }
            }
        }

        console.log(this.saveTags);
      },
      goStepThree : function () {
        if(this.saveTags.length > 0) {
        	let saveObj = {}
        	let save = [];
	        for(let i=0;i<this.saveTags.length;i++){
		        save.push({
			        tagIdx : this.saveTags[i].idx,
			        uidx : this.getMemberIdx
		        });
	        }

	        saveObj.save = save;
	        saveObj.tags = this.saveTags;

        	this.$store.dispatch('setSavedTagsAct', saveObj).then((response)=> {
        		if(response.code == "OK"){
                    this.$router.push({'name': 'StepThree'});
                }
            }).catch((response)=>{

            });
        }

      }
    }
  }
</script>

<style scoped>

</style>
