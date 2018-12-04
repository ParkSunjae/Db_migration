<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_rgba">
            </div>
            <!-- 공유하기 팝업 -->
            <Modal v-if="pop1">
                <div slot=body>
                    <div class="share_view">
                        <div class="share_wrap">
                            <div class="tit">
                                <p>공유하기</p>
                                <span class="close" @click="close"><img src="../../../static/img/esc_icon.png" alt="닫기"></span>
                            </div>
                            <form>
                                <fieldset>
                                    <legend>공유하기</legend>
                                    <ul class="sns_icons">
                                        <li>
                                            <button type="button">페이스북</button>
                                        </li>
                                        <li>
                                            <button type="button">URL복사</button>
                                        </li>
                                    </ul>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </Modal>
            <BlockMemberModal ref="blockMemberModal"/>
            <DeleteModel ref="deleteModal" :afterFunction="afterDeleteFunction"/>
            <AlertModal ref="alertModal"/>
            <div class="bg_rgba">
            </div>
            <!--본문-->
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container_C">
                        <div class="myfeed_content">
                            <div class="feed_info">
                                <div class="img">
                                    <a href="#">
                                        <img
                                            v-bind:src="getProfileImage({'fileThumbnail':profileFileThumbnail,'fileName':profileFileName})">
                                    </a>
                                </div>
                                <div class="con">
                                    <a href="#"
                                       v-bind:class="{'vora_icon':getMemberBusinessYn=='Y','id': getMemberBusinessYn=='N'}">{{getMemberNickName}}</a>
                                    <a href="#" class="nick">{{getEmailAccountEmail}}</a>
                                </div>
                                <div class="myset_btn" v-if="getEmailAccountDeviceCheck">
                                    <router-link to="/SetProfile" tag="button">설정</router-link>
                                </div>
                                <div class="my_num">
                                    <ul>
                                        <li>
                                            <p>게시물<span>{{getFeedCount}}</span></p>
                                        </li>
                                        <li>
                                            <p class="following">팔로잉<span>{{getFollowingCount}}</span></p>
                                        </li>
                                        <li>
                                            <p class="follower">팔로워<span>{{getFollowerCount}}</span></p>
                                        </li>
                                    </ul>
                                </div>
                                <div class="myfeed_info">
                                    <p class="txt"
                                       v-html="getMemberContent != null  ? getMemberContent : '자기 소개가 없습니다.' "></p>
                                </div>
                            </div>
                        </div>
                        <div class="feed_content">
                            <!-- s : 메인 tab  -->
                            <ul class="tabs">
                                <li @click="activeTabChoice('tab_a')" :class="tab_a_bool ? 'active' : ''"><a>게시물</a>
                                </li>
                                <li @click="activeTabChoice('tab_b')" :class="tab_b_bool ? 'active' : ''"><a>함께한</a>
                                </li>
                                <li @click="activeTabChoice('tab_c')" :class="tab_c_bool ? 'active' : ''"><a>스크랩</a>
                                </li>
                            </ul><!-- e : 메인 tab  -->
                        </div>
                        <div class="main_contents">
                            <!-- s : 메인 리스트 -->
                            <div class="tab_container">
                                <div id="tab1" class="tab_content" v-if="tab_a_bool">
                                    <div v-if="getMyFeedListChecker">
                                        <FeedModel v-for="(feed,idx) in getMyFeedList"
                                                   :key="idx"
                                                   :deleteModal="$refs.deleteModal"
                                                   :alertModal="$refs.alertModal"
                                                   :blockMemberModal="$refs.blockMemberModal"
                                                   :feed="feed"
                                                   :comment-view="true"
                                                   :comment-inline="true"/>
                                    </div>
                                    <div class="no_content" v-else>
                                        <p>등록된 피드가 없습니다.</p>
                                    </div>

                                </div>
                                <div id="tab2" class="tab_content" v-if="tab_b_bool">
                                    <div v-if="getMyFeedListChecker">
                                        <FeedModel v-for="(feed,idx) in getMyFeedList"
                                                   :key="idx"
                                                   :deleteModal="$refs.deleteModal"
                                                   :alertModal="$refs.alertModal"
                                                   :blockMemberModal="$refs.blockMemberModal"
                                                   :feed="feed"
                                                   :comment-view="true"
                                                   :comment-inline="true"/>
                                    </div>
                                    <div class="no_content" v-else>
                                        <p>등록된 피드가 없습니다.</p>
                                    </div>
                                </div>
                                <div id="tab3" class="tab_content" v-if="tab_c_bool">
                                    <div v-if="getMyFeedListChecker">
                                        <FeedModel v-for="(feed,idx) in getMyFeedList"
                                                   :key="idx"
                                                   :deleteModal="$refs.deleteModal"
                                                   :alertModal="$refs.alertModal"
                                                   :blockMemberModal="$refs.blockMemberModal"
                                                   :feed="feed"
                                                   :comment-view="true"
                                                   :comment-inline="true"/>
                                    </div>
                                    <div class="no_content" v-else>
                                        <p>등록된 피드가 없습니다.</p>
                                    </div>
                                </div>
                            </div><!-- e : 메인 리스트 -->
                        </div>
                    </div>
                    <!--우측 메뉴 영역 -->
                    <div id="A_Container_R">
                        <div class="right_menu">
                            <!--추천 태그 -->
                            <!--  v-if="tagRecommendPC && tagRecommendPC.length" -->
                            <RecommendTag ref="recommendTag"/>
                            <!-- v-if="userRecommendPC && userRecommendPC.length" -->
                            <RecommendUser ref="recommendUser"/>
                            <!--푸터 -->
                            <div class="intro_txt">
                                <p><a href="#">VORA소개</a> · <a href="#">설정</a> · <a href="#">개인정보처리방침</a> · <a href="#">이용약관</a>
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

    export default {
        name: 'MyFeed',
        components: {
            RecommendTag,
            RecommendUser,
            FeedModel,
            DeleteModel,
            AlertModal,
            BlockMemberModal,
        },
        data: function () {
            return {
                pop1: false,
                bottom: false,
                page: 1,
                activeTab: 'tab_a',
                tab_a_bool: true,
                tab_b_bool: false,
                tab_c_bool: false,
                getMyFeedListChecker: true,
                total: 0,
                profileFileThumbnail: "",
                profileFileName: "",
                delFeedObj: {},
            }
        },
        computed: {
            ...mapGetters([
                'getMyFeedList',
                'getMemberIdx',
                'getMemberNickName',
                'getEmailAccountEmail',
                'getFeedCount',
                'getFollowingCount',
                'getFollowerCount',
                'getMemberContent',
                'getFilesFileThumbnail',
                'getFilesFileName',
                'getNewestTagRecommendPC',
                'getNewestUserRecommendPC',
                'getEmailAccountDeviceCheck',
                'getMemberBusinessYn'
            ])
        },
        created: function () {
            window.addEventListener('scroll', () => {
                this.bottom = this.bottomVisible()
            })
            this.$store.dispatch('emptyList');
            this.page = 1;
            this.activeTabChoice('tab_a');
            this.profileFileThumbnail = this.getFilesFileThumbnail;
            this.profileFileName = this.getFilesFileName;
        },
        mounted: function () {
            this.$refs["recommendTag"].loadRecommendTagData();
            this.$refs["recommendUser"].loadRecommendUserData();
        },
        methods: {
            ...mapActions([
                'searchMyFeedList',
                'toggleLikeButtonAct',
                'searchToGetherList',
                'toggleCommentButtonAct',
                'toggleScrapButtonAct',
                'searchMyScrapList',
                'emptyList',
                'setCallFeedIdx',
                'loadTagRecommendPCAct',
                'loadUserRecommendPCAct',
                'toggleMemberBlocmMemberAct',
                'saveFeedsReportAct',
                'deleteFeedAct'
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
                    this.$store.dispatch('emptyList')
                    this.page = 1;
                    this.total = 0;
                }
                switch (tab) {
                    case 'tab_a' :
                        this.tab_a_bool = true;
                        this.tab_b_bool = false;
                        this.tab_c_bool = false;
                        this.getMyFeeds();
                        break;
                    case 'tab_b' :
                        this.tab_a_bool = false;
                        this.tab_b_bool = true;
                        this.tab_c_bool = false;
                        this.getTogethers();
                        break;
                    case 'tab_c' :
                        this.tab_a_bool = false;
                        this.tab_b_bool = false;
                        this.tab_c_bool = true;
                        this.getMyScraps();
                        break;
                }
            },
            listCheck : function (){
              if(this.getMyFeedList.length > 0){
                  this.getMyFeedListChecker = true;
              } else{
                  this.getMyFeedListChecker = false;
              }
            },
            getMyFeeds: function () {
                let callData = {
                    page: this.page,
                    uidx: this.getMemberIdx
                }

                this.$store.dispatch('searchMyFeedList', callData).then((response) => {
                    console.log(response);
                    this.page = response.page;
                    this.total = response.totalCount;
                    this.listCheck();
                }).catch((response) => {

                });

            },
            getTogethers: function () {
                let callData = {
                    page: this.page,
                    uidx: this.getMemberIdx
                }

                this.$store.dispatch('searchToGetherList', callData).then((response) => {
                    this.page = response.page;
                    this.total = response.totalCount;
                    this.listCheck();
                }).catch((response) => {

                });

            },
            getMyScraps: function () {
                let callData = {
                    page: this.page,
                    uidx: this.getMemberIdx
                }

                this.$store.dispatch('searchMyScrapList', callData).then((response) => {
                    this.page = response.page;
                    this.total = response.totalCount;
                    this.listCheck();
                }).catch((response) => {

                });
            },
            getProfileImage: function (imgObj) {
                if (imgObj)
                    return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
            afterDeleteFunction: function () {
                this.$store.dispatch('emptyList')
                this.page = 1;
                this.total = 0;

                switch (this.activeTab) {
                    case 'tab_a' :
                        this.getMyFeeds();
                        break;
                    case 'tab_b' :
                        this.getTogethers();
                        break;
                    case 'tab_c' :
                        this.getMyScraps();
                        break;
                }
            }
        },
        watch: {
            bottom(bottom) {
                if (bottom) {
                    switch (this.activeTab) {
                        case 'tab_a' :
                            this.getMyFeeds();
                            break;
                        case 'tab_b' :
                            this.getTogethers();
                            break;
                        case 'tab_c' :
                            this.getMyScraps();
                            break;
                    }
                }
            }
        }
    }
</script>

<style scoped>
</style>
