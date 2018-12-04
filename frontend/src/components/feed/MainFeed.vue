<template>
    <div id="A_Container_Wrap ow_scroll">
        <div id="A_Container">
            <BlockMemberModal ref="blockMemberModal"/>
            <DeleteModel ref="deleteModal" :afterFunction="afterDeleteFunction"/>
            <AlertModal ref="alertModal"/>
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container_C">
                        <div v-if="mainFeedList.length > 0">
                            <FeedModel v-for="(feed,idx) in mainFeedList"
                                       :key="idx"
                                       :deleteModal="$refs.deleteModal"
                                       :alertModal="$refs.alertModal"
                                       :blockMemberModal="$refs.blockMemberModal"
                                       :feed="feed"
                                       :comment-view="true"
                                       :comment-inline="true"
                            />
                        </div>
                        <div class="no_content" v-if="mainFeedListChecker">
                            <p>등록된 피드가 없습니다.</p>
                        </div>
                    </div>

                    <div id="A_Container_R">
                        <div class="right_menu">
                            <LoginUserInfo
                                :showTrigger = "true"
                            ></LoginUserInfo>
                            <!--  v-if="tagRecommendPC && tagRecommendPC.length" -->
                            <RecommendTag ref="recommendTag"/>
                            <!-- v-if="userRecommendPC && userRecommendPC.length" -->
                            <RecommendUser ref="recommendUser"/>
                            <div class="intro_txt">
                                <p><a href="#">VORA소개</a> · <a href="#">설정</a> ·
                                    <router-link :to="{name : 'TermsView' }" class="write_icon" tag="a">
                                        개인정보처리방침
                                    </router-link>
                                    ·
                                    <router-link :to="{name : 'PrivacyView' }" class="write_icon" tag="a">
                                        이용약관
                                    </router-link>
                                </p>
                                <p class="app_icon"><a href="#" class="iphone">iPhone 앱</a><a href="#" class="android">Android
                                    앱</a></p>
                                <p class="copyright">Copyright @ KYOBO BOOK CENTRE.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState, mapActions, mapGetters} from 'vuex'
    import LoginUserInfo from '../models/LoginUserInfo'
    import RecommendTag from '../models/RecommendTag'
    import RecommendUser from '../models/RecommendUser'
    import FeedModel from '../models/FeedModel'
    import DeleteModel from '../models/DeleteModal'
    import AlertModal from '../models/AlertModal'
    import BlockMemberModal from '../models/BlockMemberModal'

    export default {
        name: 'MainFeed',
        components: {
            RecommendTag,
            RecommendUser,
            FeedModel,
            DeleteModel,
            AlertModal,
            BlockMemberModal,
            LoginUserInfo
        },
        data: function () {
            return {
                bottom: false,
                blockUser: false,
                report: false,
                nickName: '',
                email: '',
                feedCount: 0,
                followingCount: 0,
                followerCount: 0,
                page: 0,
                profile: "",
                profileThumbnail: "",
                mainFeedList: [],
                mainFeedListChecker : true,
                lastScrollTop : 0,
            }
        },
        computed: {
            ...mapGetters([
                'getMemberIdx',
                'getMainFeedList',
                'getNewestPostRercommend',
            ])
        },
        methods: {
            ...mapActions([
                'searchMainFeedList',
                'mainFeedEmptyListAct',
                'loadPostRercommendAct',
            ]),
            bottomVisible() {
                const scrollY = window.scrollY
                const visible = document.documentElement.clientHeight
                const pageHeight = document.documentElement.scrollHeight
                const bottomOfPage = visible + scrollY >= pageHeight
                return bottomOfPage || pageHeight < visible;
            },
            getMainFeeds: function () {
                let callData = {
                    page: this.page,
                    uidx: this.getMemberIdx
                }
                this.$store.dispatch('searchMainFeedList', callData).then((response) => {
                    this.mainFeedList = this.getMainFeedList;
                    if(this.getMainFeedList.length > 0){
                        this.mainFeedListChecker = false;
                    }else{
                        this.mainFeedListChecker = true;
                    }
                    this.page = response.page;
                }).catch((error) => {
                });
            },
            findTagByIdx: function (idx) {
                this.$router.push({'name': 'TagFeed', params: {tagIdx: idx}});
            },
            findMemberByIdx: function (idx) {
                this.$router.push({'name': 'MemberFeed', params: {memberIdx: idx}});
            },
            afterDeleteFunction: function () {
                this.page = 1;
                this.total = 0;
                this.$store.dispatch('mainFeedEmptyListAct');
                this.getMainFeeds();
            },
            callMainFeed: function () {

                this.page = 1;
                this.$store.dispatch('mainFeedEmptyListAct');
                this.getMainFeeds();
            }
        },
        created: function () {
            window.addEventListener('scroll', () => {
                this.bottom = this.bottomVisible()
            });
            this.callMainFeed();
        },
        mounted : function(){
            this.$refs["recommendTag"].loadRecommendTagData();
            this.$refs["recommendUser"].loadRecommendUserData();
        },
        watch: {
            bottom(bottom) {
                if(bottom) this.getMainFeeds();
            }
        }
    }
</script>

<style scoped>
    .ow_scroll {
        background-color: #ebf0fa;
        bottom: 0;
    }

    .pull-down-container {
        padding-bottom: 20px;
    }

    @supports (padding: max(0px)) {
        .pull-down-container {
            padding-bottom: calc(20px + env(safe-area-inset-bottom));
        }
    }
</style>
