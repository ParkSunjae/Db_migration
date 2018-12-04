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
                                    <a><img src="../../../static/img/tag_image.png" alt="태그이미지"></a>
                                </div>
                                <div class="con">
                                    <a class="tag_id">{{ getTagFeedTag }}</a>
                                </div>
                                <div class="follow_btn">
                                    <button type="button">팔로우</button>
                                </div>
                                <div class="my_num">
                                    <ul>
                                        <li>
                                            <p>게시물<span>{{getTagTotalCount}}</span></p>
                                        </li>
                                        <li>
                                            <p class="following">팔로잉<span>{{getTagFollowCount}}</span></p>
                                        </li>
                                    </ul>
                                </div>
                                <div class="tagfeed_list">
                                    <p class="tag">
                                        <button type="button" v-for="item in relationTag"
                                                @click="findTagByIdx(item.idx,item.tag)"><span>#{{item.tag}}</span>
                                        </button>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="feed_content">
                            <!-- s : 메인 tab  -->
                            <ul class="tabs">
                                <li @click="activeTabChoice('tab_a')" :class="tab_a_bool ? 'active' : ''">
                                    <a>인기</a>
                                </li>
                                <li @click="activeTabChoice('tab_b')" :class="tab_b_bool ? 'active' : ''">
                                    <a>최신</a>
                                </li>
                            </ul><!-- e : 메인 tab  -->
                        </div>
                        <div class="main_contents">
                            <div class="tab_container">
                                <div id="tab1" class="tab_content" v-if="tab_a_bool">
                                    <div v-if="tagFeedListChecker">
                                        <FeedModel v-for="(feed,idx) in tagFeedList"
                                                   :key="idx"
                                                   :deleteModal="$refs.deleteModal"
                                                   :alertModal="$refs.alertModal"
                                                   :blockMemberModal="$refs.blockMemberModal"
                                                   :feed="feed"
                                                   :comment-view="true"
                                                   :comment-inline="true"
                                        />
                                    </div>
                                    <div class="no_content" v-else>
                                        <p>등록된 피드가 없습니다.</p>
                                    </div>

                                </div>
                                <div id="tab2" class="tab_content" v-if="tab_b_bool">
                                    <div v-if="tagFeedListChecker">
                                        <FeedModel v-for="(feed,idx) in tagFeedList"
                                                   :key="idx"
                                                   :deleteModal="$refs.deleteModal"
                                                   :alertModal="$refs.alertModal"
                                                   :blockMemberModal="$refs.blockMemberModal"
                                                   :feed="feed"
                                                   :comment-view="true"
                                                   :comment-inline="true"
                                        />
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
    import LoginUserInfo from '../models/LoginUserInfo'
    export default {
        name: 'TagFeed',
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
                page: 0,
                tagFeedList: [],
                tab_a_bool: false,
                tab_b_bool: false,
                tagFeedListChecker: true,
                relationTag: [],
            }
        },
        computed: {
            ...mapGetters([
                'getMemberIdx',
                'getTagFeedList',
                'getTagFeedTag',
                'getTagFeedTagIdx',
                'getTagTotalCount',
                'getTagFollowCount',
                'getRelationTag',
            ])
        },
        methods: {
            ...mapActions([
                'searchTagFeedList',
                'searchTagFeedPopularList',
                'tagFeedEmptyListAct',
                'setRelationTagAct',
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
                    this.$store.dispatch('tagFeedEmptyListAct')
                    this.page = 1;
                    this.total = 0;
                }
                switch (tab) {
                    case 'tab_a' :
                        this.tab_a_bool = true;
                        this.tab_b_bool = false;
                        this.getTagFeeds();
                        break;
                    case 'tab_b' :
                        this.tab_a_bool = false;
                        this.tab_b_bool = true;
                        this.getTagFeeds();
                        break;
                }
            },
            getProfileImage: function (imgObj) {
                if (imgObj)
                    return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
            getTagFeeds: function () {
                let callData = {
                    page: this.page,
                    uidx: this.getMemberIdx,
                    idx: this.$route.params.tagIdx
                }
                switch (this.activeTab) {
                    case 'tab_a':
                        this.$store.dispatch('searchTagFeedPopularList', callData).then((response) => {
                            this.tagFeedList = this.getTagFeedList;
                            this.listChecker();
                            this.page = response.page;
                        })
                            .catch((error) => {
                            });
                        break;
                    case 'tab_b':
                        this.$store.dispatch('searchTagFeedList', callData).then((response) => {
                            this.tagFeedList = this.getTagFeedList;
                            this.listChecker();
                            this.page = response.page;
                        })
                            .catch((error) => {
                            });
                        break;
                }
            },
            listChecker : function () {
              if(this.tagFeedList.length > 0){
                  this.tagFeedListChecker = true;
              }else{
                  this.tagFeedListChecker = false;
              }
            },
            findTagByIdx: function (idx, tag) {
                this.$router.push({'name': 'TagFeed', params: {tagIdx: idx}});
            },
            findMemberByIdx: function (idx) {
                this.$router.push({'name': 'MemberFeed', params: {memberIdx: idx}});
            },
            afterDeleteFunction: function () {
                this.$store.dispatch('tagFeedEmptyListAct');
                this.getTagFeeds();
            }
        },
        created: function () {
            window.addEventListener('scroll', () => {
                this.bottom = this.bottomVisible()
            });

            this.activeTab = 'tab_a';
            this.tab_a_bool = true;
            this.page = 1;
            this.$store.dispatch('tagFeedEmptyListAct');
            this.getTagFeeds();

            this.$store.dispatch('setRelationTagAct', {idx: this.getTagFeedTagIdx, tag: this.getTagFeedTag})
                .then((response) => {
                    this.relationTag = this.getRelationTag;
                }).catch((error) => {
            });
        },
        mounted : function(){
            this.$refs["recommendTag"].loadRecommendTagData();
            this.$refs["recommendUser"].loadRecommendUserData();
        },
        watch: {
            bottom(bottom) {
                if(bottom) this.getTagFeeds();
            }
        }
    }

</script>

<style scoped>

</style>
