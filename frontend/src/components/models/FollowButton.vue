<template>
        <button type="button" name="button" @click="toggleFollow" v-bind:class="{'on':following.length}">
            {{ following.length ? '팔로잉' : '팔로우' }}
        </button>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'

export default {
    name: "FollowButton",
    data: function() {
        return {
            following: [],
        }
    },
    computed: {
        ...mapGetters([
            'checkedFollowMember',
            'getCheckedFollowMemberList',
        ])
    },
    props: ['member'],
    methods: {
        toggleFollow: function() {
            this.$store.dispatch('toggleFollowMemberAct', this.member.idx).then((response) => {
            }).catch((error) => {
            });
        },
    },
    created: function() {
        this.$store.dispatch('checkFollowMemberAct', this.member.idx).then((response)=>{
            this.following = this.getCheckedFollowMemberList.filter(member => ((typeof member !== 'undefined') && (member.tidx==this.member.idx)));
        }).catch((error)=>{
        });
    },
    watch : {
        getCheckedFollowMemberList : function(newlist,oldlist){
            this.following = this.getCheckedFollowMemberList.filter(member => ((typeof member !== 'undefined') && (member.tidx==this.member.idx)));
        }
    }
}
</script>
<style scoped>

</style>
