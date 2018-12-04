<template>
    <div class="my_info">
        <div class="img">
            <a href="#">
                <img
                    v-bind:src="getProfileImage(getFilesFileThumbnail, getFilesFileName)">
            </a>
        </div>
        <div class="con">
            <a href="#">
                <p class="id">{{ getMemberNickName }}</p>
                <p class="nick">{{ getEmailAccountEmail ? getEmailAccountEmail : "&nbsp" }}</p>
            </a>
        </div>
        <div class="set_icon" v-if="showTrigger != false">
            <router-link to="/SetProfile" tag="button"></router-link>
        </div>
        <div class="my_num">
            <ul>
                <li>
                    <p>게시물<span>{{ getFeedCount }}</span></p>
                </li>
                <li>
                    <p>팔로잉<span>{{ getFollowingCount }}</span></p>
                </li>
                <li>
                    <p>팔로워<span>{{ getFollowerCount }}</span></p>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex'

    export default {
        name: "LoginUserInfo",
        props : ['showTrigger'],
        computed: {
            ...mapGetters([
                'getMemberIdx',
                'getMemberNickName',
                'getEmailAccountEmail',
                'getFeedCount',
                'getFollowingCount',
                'getFollowerCount',
                'getMainFeedList',
                'getFilesFileThumbnail',
                'getFilesFileName'
            ])
        },
        mounted: function () {
            this.getUserData()
        },
        methods: {
            ...mapActions([
                'loadMemberInfoAct'
            ]),
            getProfileImage: function (fileThumbnail, fileName) {
                if (fileThumbnail || fileName)
                    return fileThumbnail || fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
            getUserData: function () {
                this.$store.dispatch('loadMemberInfoAct');
            }
        }
    }
</script>

<style scoped>

</style>
