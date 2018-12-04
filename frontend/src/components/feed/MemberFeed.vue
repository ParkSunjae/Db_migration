<template>
    <div id="A_Container_Wrap ow_scroll">
        <div id="A_Container">
            <BlockMemberModal ref="blockMemberModal"/>
            <DeleteModel ref="deleteModal" :afterFunction="afterDeleteFunction"/>
            <AlertModal ref="alertModal"/>
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container_C">
                        <div class="myfeed_content">

                            <div class="feed_info">
                                <div class="img">
                                    <a href="#">
                                        <img v-bind:src="getProfileImage(getMemberFeedMemberFileInfo)" alt="태그이미지">
                                    </a>
                                </div>
                                <div class="con">
                                    <a class="tag_id" v-bind:class="{'vora_icon':getMemberFeedMemberInfo.businessYn=='Y'}">{{ getMemberFeedMemberInfo.nickName }}</a>
                                </div>
                                <div class="follow_btn" v-if="getMemberFeedMemberInfo.idx != getMemberIdx">
                                    <FollowButton :member="getMemberFeedMemberInfo"/>
                                </div>
                                <div class="my_num">
                                    <ul>
                                        <li>
                                            <p>게시물<span>{{getMemberFeedTotalCount}}</span></p>
                                        </li>
                                        <li>
                                            <p class="follow">팔로우<span>{{getMemberFeedFollowCount}}</span></p>
                                        </li>
                                        <li>
                                            <p class="following">팔로잉<span>{{getMemberFeedFollowerCount}}</span></p>
                                        </li>
                                    </ul>
                                </div>
                                <div class="myfeed_info">
                                    <p class="txt">{{ getMemberFeedMemberInfo.content }}</p>
                                </div>
                            </div>

                        </div>
                        <div class="feed_content">
                            <!-- s : 메인 tab  -->
                            <ul class="tabs">
                                <li @click="activeTabChoice('tab_a')" :class="tab_a_bool ? 'active' : ''">
                                    <a>게시물</a>
                                </li>
                                <li @click="activeTabChoice('tab_b')" :class="tab_b_bool ? 'active' : ''">
                                    <a>함께한</a>
                                </li>
                            </ul><!-- e : 메인 tab  -->
                        </div>
                        <div class="main_contents">
                            <div class="tab_container">

                                <div id="tab1" class="tab_content" v-if="tab_a_bool">
                                    <div v-if="memberFeedFeedListChecker">
                                        <FeedModel v-for="(feed,idx) in memberFeedFeedList"
                                               :key="idx"
                                               :deleteModal="$refs.deleteModal"
                                               :alertModal="$refs.alertModal"
                                               :blockMemberModal="$refs.blockMemberModal"
                                               :feed="feed"/>
                                    </div>
                                    <div class="no_content" v-else>
                                        <p>등록된 피드가 없습니다.</p>
                                    </div>
                                </div>
                                <div id="tab2" class="tab_content" v-if="tab_b_bool">

                                    <div v-if="memberFeedFeedListChecker">
                                        <FeedModel v-for="(feed,idx) in memberFeedFeedList"
                                                   :key="idx"
                                                   :deleteModal="$refs.deleteModal"
                                                   :alertModal="$refs.alertModal"
                                                   :blockMemberModal="$refs.blockMemberModal"
                                                   :feed="feed"/>
                                    </div>
                                    <div class="no_content" v-else>
                                        <p>등록된 피드가 없습니다.</p>
                                    </div>

                                </div>
                            </div>
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

    import RecommendTag from '../models/RecommendTag'
    import RecommendUser from '../models/RecommendUser'
    import FeedModel from '../models/FeedModel'
    import DeleteModel from '../models/DeleteModal'
    import AlertModal from '../models/AlertModal'
    import BlockMemberModal from '../models/BlockMemberModal'
    import FollowButton from '../models/FollowButton'
    import LoginUserInfo from '../models/LoginUserInfo'
    export default {
        name: 'MemberFeed',
        components: {
            RecommendTag,
            RecommendUser,
            FeedModel,
            DeleteModel,
            AlertModal,
            BlockMemberModal,
            FollowButton,
            LoginUserInfo
        },
        data: function () {
            return {
                bottom: false,
                blockUser: false,
                report: false,
                reportType: "",
                reportContentType: "",
                page: 0,
                tagRecommendPC: [],
                userRecommendPC: [],
                tab_a_bool: false,
                tab_b_bool: false,
                memberFeedFeedList: {},
                memberFeedFeedListChecker: true,
                memberFeedMemberFileInfo: {},
            }
        },
        computed: {
            ...mapGetters([
                'getMemberIdx',
                'getNewestTagRecommendPC',
                'getNewestUserRecommendPC',
                'getMemberFeedFeedList',
                'getMemberFeedTotalCount',
                'getMemberFeedFollowCount',
                'getMemberFeedFollowerCount',
                'getMemberFeedMemberInfo',
                'getMemberFeedMemberFileInfo',
                'getMemberFeedMemberAccount',
            ])
        },
        methods: {
            ...mapActions([
                'loadTagRecommendPCAct',
                'loadUserRecommendPCAct',
                'searchMemberFeedList',
                'searchMemberFeedPopularList',
                'toggleMemberFeedLikeButtonAct',
                'toggleMemberFeedScrapButtonAct',
                'memberFeedEmptyListAct',
                'setCallFeedIdx',
                'toggleMemberBlocmMemberAct',
            ]),
            bottomVisible() {
                const scrollY = window.scrollY
                const visible = document.documentElement.clientHeight
                const pageHeight = document.documentElement.scrollHeight
                const bottomOfPage = visible + scrollY >= pageHeight
                return bottomOfPage || pageHeight < visible
            },
            activeTabChoice: function (tab) {
                if (this.activeTab != tab) {
                    this.activeTab = tab;
                    this.$store.dispatch('memberFeedEmptyListAct')
                    this.page = 1;
                    this.total = 0;
                }
                switch (tab) {
                    case 'tab_a' :
                        this.tab_a_bool = true;
                        this.tab_b_bool = false;
                        this.getMemberFeeds();
                        break;
                    case 'tab_b' :
                        this.tab_a_bool = false;
                        this.tab_b_bool = true;
                        this.getMemberFeeds();
                        break;
                }
            },
            getProfileImage: function (imgObj) {
                if (imgObj)
                    return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
            getMemberFeeds: function () {
                let callData = {
                    page: this.page,
                    uidx: this.$route.params.memberIdx,
                    idx: this.getMemberIdx,
                }
                switch (this.activeTab) {
                    case 'tab_a':
                        this.$store.dispatch('searchMemberFeedList', callData).then((response) => {
                            this.memberFeedFeedList = this.getMemberFeedFeedList;
                            this.listChecker();
                            this.page = response.page;
                        })
                            .catch((error) => {
                            });
                        break;
                    case 'tab_b':
                        this.$store.dispatch('searchMemberFeedPopularList', callData).then((response) => {
                            this.memberFeedFeedList = this.getMemberFeedFeedList;
                            this.listChecker();
                            this.page = response.page;
                        })
                            .catch((error) => {
                            });
                        break;
                }
            },
            listChecker : function () {
                if(this.memberFeedFeedList.length > 0){
                    this.memberFeedFeedListChecker = true;
                }else{
                    this.memberFeedFeedListChecker = false;
                }
            },
            findTagByIdx: function (idx, tag) {
                this.$router.push({'name': 'TagFeed', params: {tagIdx: idx}});
            },
            findMemberByIdx: function (idx) {
                this.$router.push({'name': 'MemberFeed', params: {memberIdx: idx}});
            },
            afterDeleteFunction: function () {
                this.page = 1;
                this.total = 0;
                this.$store.dispatch('memberFeedEmptyListAct');
                this.getMemberFeeds();
            }
        },
        created: function () {
            window.addEventListener('scroll', () => {
                this.bottom = this.bottomVisible()
            });
            this.activeTab = 'tab_a';
            this.tab_a_bool = true;
            this.page = 1;
            this.$store.dispatch('memberFeedEmptyListAct');
            this.getMemberFeeds();
        },
        mounted : function(){
            this.$refs["recommendTag"].loadRecommendTagData();
            this.$refs["recommendUser"].loadRecommendUserData();
        },
        watch: {
            bottom(bottom) {
                if(bottom) this.getMemberFeeds();
            }
        }
    }

</script>

<style scoped>

</style>
