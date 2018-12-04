<template>
    <div class="my_tag">
        <h3 class="tit">VORA 추천 태그</h3>
        <p class="tag">
            <button type="button" v-for="item in tagRecommendPC" @click="findTagByIdx(item.idx)">
                <span>#{{item.tag}}</span>
            </button>
        </p>
    </div>
</template>
<script>
import {mapState, mapActions, mapGetters} from 'vuex'
export default {
    name:"RecommendTag",
    data : function(){
        return {
            tagRecommendPC:[],
        }
    },
    computed : {
        ...mapGetters([
            'getNewestTagRecommendPC',
        ])
    },
    methods : {
        ...mapActions([
            'loadMemberInfoAct',
            'loadTagRecommendPCAct',
        ]),
        findTagByIdx : function(idx){
            this.$router.push({'name': 'TagFeed',params:{tagIdx:idx}});
        },
        loadRecommendTagData : function(){
            this.$store.dispatch('loadTagRecommendPCAct').then((response) => {
                this.tagRecommendPC = this.getNewestTagRecommendPC;
            }).catch((error) => {
            });
        }
    }
}
</script>
<style scoped>

</style>
