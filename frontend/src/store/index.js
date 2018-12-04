import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import user from './modules/user'
import account from './modules/account'
import feedWrite from './modules/feedsWrite'
import feedsModify from './modules/feedsModify'
import memberInfo from './modules/memberInfo'
import businessCategory from './modules/businessCategory'
import myFeedView from './modules/myFeedView'
import memberBlockMember from './modules/memberBlockMember'
import memberFollowMember from './modules/memberFollowMember'
import feedView from './modules/feedView'
import notice from './modules/notice'
import help from './modules/help'
import inquiry from './modules/inquiry'
import tags from './modules/tags'
import mainFeeds from './modules/mainFeeds'
import userNotice from './modules/userNotice'
import personalRecommend from './modules/personalRecommend'
import terms from './modules/terms'
import feedReport from './modules/feedReport'
import feedCommentReport from './modules/feedCommentReport'
import tagFeed from './modules/tagFeed'
import tagRelation from './modules/tagRelation'
import setList from './modules/setList'
import memberFeed from './modules/memberFeed'
import intro from './modules/intro'
import searchFeed from './modules/searchFeed'
import searchHistory from './modules/searchHistory'

export default new Vuex.Store({
    modules: {
        user,
        feedWrite,
        memberInfo,
        businessCategory,
        myFeedView,
        memberBlockMember,
        memberFollowMember,
        feedView,
        notice,
        help,
        inquiry,
        tags,
        mainFeeds,
        userNotice,
        personalRecommend,
        terms,
        feedReport,
        feedCommentReport,
        account,
        tagFeed,
        tagRelation,
        setList,
        feedsModify,
        memberFeed,
        intro,
        searchFeed,
        searchHistory,
    }
})
