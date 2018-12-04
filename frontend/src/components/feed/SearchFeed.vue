<template>
    <div id="A_Container_Wrap ow_scroll">
        <div id="A_Container">
            <BlockMemberModal ref="blockMemberModal"/>
            <DeleteModel ref="deleteModal" :afterFunction="afterDeleteFunction"/>
            <AlertModal ref="alertModal"/>
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container_C">
                        <div v-if="getSearchFeedList.length > 0">
                            <FeedModel v-for="(feed,idx) in getSearchFeedList"
                                       :key="idx"
                                       :deleteModal="$refs.deleteModal"
                                       :alertModal="$refs.alertModal"
                                       :blockMemberModal="$refs.blockMemberModal"
                                       :feed="feed"
                                       :comment-view="true"
                                       :comment-inline="true"
                            />
                        </div>
                        <!-- <div class="no_content" v-if="searchFeedListChecker">
                            <p>등록된 피드가 없습니다.</p>
                        </div> -->
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
    name: 'SearchFeed',
    components: {
        RecommendTag,
        RecommendUser,
        FeedModel,
        DeleteModel,
        AlertModal,
        BlockMemberModal,
        LoginUserInfo,
    },
    data : function(){
        return {
            bottom : false,
        }
    },
    computed: {
        ...mapGetters([
            'getMemberIdx',
            'getSearchFeedList',
        ])
    },
    methods : {
        ...mapActions([
            'searchFeedAct',
            'setSearchQueryAct',
        ]),
        bottomVisible() {
            const scrollY = window.scrollY
            const visible = document.documentElement.clientHeight
            const pageHeight = document.documentElement.scrollHeight
            const bottomOfPage = visible + scrollY >= pageHeight
            return bottomOfPage || pageHeight < visible
        },
        getSearchedFeeds : function(){
            this.$store.dispatch('searchFeedAct');
        },
        afterDeleteFunction : function(){
            this.$store.dispatch('initSearchFeedAct');
            this.getSearchedFeeds();
        }
    },
    created : function(){
        window.addEventListener('scroll', () => {
            this.bottom = this.bottomVisible()
        });
        this.getSearchedFeeds();
    },
    mounted : function(){
        this.$refs["recommendTag"].loadRecommendTagData();
        this.$refs["recommendUser"].loadRecommendUserData();
    },
    watch: {
        bottom(bottom) {
            if(bottom) this.getSearchedFeeds();
        }
    }
}
</script>

<style lang="css">
</style>
