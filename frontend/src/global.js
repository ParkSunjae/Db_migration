/**
 * global parameters
 */

const axiosConfig = {
    headers: {
        'accept': 'application/json',
        'accept-language': '',
        'Content-Type' : 'application/json'
    }
};
const axiosConfig2 = {
    headers: {
        'accept': 'application/json',
        'accept-language': '',
        'Content-Type' : 'multipart/form-data'
    }
};

export default {
    deviceType : '',
    //운영
    // baseURI: 'https://app.passcoin.co.kr/api/v1',
    // baseURI: 'https://test-app.passcoin.co.kr/api/v1',
    //개발
    baseURI: 'http://13.124.107.104:8080/api/v1',
    // baseURI: 'http://localhost:8080/api/v1',
    naverLocationUrl : 'http://13.124.107.104:8080/api/v1/public/naver/location',
    findBook : 'http://13.124.107.104:8080/api/v1/public/books/find',
    findMovie : 'http://13.124.107.104:8080/api/v1/public/naver/movie',
    searchUrl : 'http://avanvora.cafe24.com/api/media/search?query=',
    perpomence : '&cateName1=공연&page=',
    exhibition : '&cateName1=전시&page=',
    publicUri: '/public',
    privateUri: '/private',
    locale: '',
    //api uri define
    api: {
        post: {
            account : {
                getAccount : '/account/getUserAccount',
                checkPassword : '/account/checkPassword'
            },
            member: {
                saveMember: '/member/save',
                signin: '/public/member/signIn',
                signinT: '/public/member/signInT',
                signUp: '/public/member/signUp',
                checkNickName: '/member/checkNickName',
                findMember: '/member/find',
                loadMemberData: '/member/loadMemberProfile',
                updateMemberData: '/member/modifyMemberProfile',
                saveYear: '/member/saveYear',
                getAdminCheckUsers: '/member/adminCheckUsers',
                getSuggestUserFind: '/member/getSuggestUserFind',
                certMail : '/member/sendCertMail',
                tempPwdSend : '/member/tempPwdSend',
                checkUserMail : '/member/checkUserMail',
                signOut: '/member/signOut',
                memberExit : '/member/exit',
                checkDuplicateEmail : '/member/checkDuplicateEmail'
            },
            memberActivitiy : {
                getActionList : '/memberActivity/getActionList',
                getLoginLogList : '/memberActivity/getLoginLogList',
            },
            memberFollowMember: {
                saveFollowers: '/memberFollowMember/saveFollowers',
                findFollowers: '/memberFollowMember/findFollowers',
                toggleFollowMember: '/memberFollowMember/toggleFollowMember',
                checkFollowMember : '/memberFollowMember/checkFollowMember',
            },
            memberBlockMember: {
                findBlockMembers: '/memberBlockMember/findBlockMembers',
                toggleMemberBlockMember: '/memberBlockMember/toggleMemberBlockMember'
            },
            myFeed: {
                getMyFeed: '/myFeed/getMyFeed',
                getToGetter: '/myFeed/getToGetter',
                getMyScrap: '/myFeed/getMyScrapList',
                setFeedLikeToggle: '/myFeed/setFeedLikeToggle',
                setFeedScrapToggle: '/myFeed/setFeedScrapToggle',
                deleteFeeds: '/feeds/deleteFeed'
            },
            feed: {
                getFeed: '/feeds/feedView',
                findDetailData : '/feeds/findDetailData',
                getFeedCommentLike: '/feeds/getCommentByLikeCount',
                getFeedCommentNew: '/feeds/getCommentByIdxCount',
                addFeedComment: '/feeds/addFeedComment',
                commentLikeToggle: '/feeds/commentLikeToggle',
                removeComment: '/feeds/removeComment',
                updateComment: '/feeds/updateComment',
                write: '/feeds/write',
                update: '/feeds/update',
            },
            businessCategory1: {
                getBusinessCategory1: '/businessCategory1/getBusinessCategory1'
            },
            businessCategory2: {
                getBusinessCategory2: '/businessCategory2/getBusinessCategory2'
            },
            notice: {
                getNoticeByUseYn: "/notice/getNoticeByUseYn",
                getNoticeAll: "/notice/getNoticeAll",
                updateNotice: "/notice/updateNotice",
                insertNotice: "/notice/insertNotice",
                deleteNotice: "/notice/deleteNotice"
            },
            help: {
                getHelpByUseYn: "/help/getHelpByUseYn",
                getHelpAll: "/help/getHelpAll",
                updateHelp: "/help/updateHelp",
                insertHelp: "/help/insertHelp",
                deleteHelp: "/help/deleteHelp"
            },
            inquiry: {
                saveInquiry: "/inquiry/saveInquiry",
                updateInquiry: "/inquiry/updateInquiry",
                getInquiry: "/inquiry/getInquiry"
            },
            tags: {
                callTags: "/tags/callTags",
                saveTags: "/tags/saveUsersTags"
            },
            mainFeed: {
                getMainFeed: '/mainFeed/getMainFeed',
                setFeedLikeToggle: '/mainFeed/setFeedLikeToggle',
                setFeedScrapToggle: '/mainFeed/setFeedScrapToggle',
            },
            alarm: {
                callMyAlarm: '/alarm/callMyAlarm',
                callMyAlarmAll:'/alarm/callMyAlarmAll',
                callMyAlarmCount: '/alarm/callMyAlarmCount',
                changeAlarmShow: '/alarm/changeAlarmShow',
            },
            personalRecommend: {
                tagRecommendPc: "/personalRecommend/tagRecommendPC",
                tagRecommendMobile: "/personalRecommend/tagRecommendMobile",
                userRecommendPc: "/personalRecommend/userRecommendPC",
                userRecommendMobile: "/personalRecommend/userRecommendMobile",
                postRecommend: "/personalRecommend/postRecommend",
            },
            terms: {
                getAllTerms: '/terms/getAllTerms'
            },
            feedReport: {
                saveFeedReport: '/feedReport/saveFeedReport',
            },
            feedCommentReport: {
                saveFeedCommentReport: '/feedCommentReport/saveFeedCommentReport',
            },
            tagFeed:{
                getTagFeed : '/tagFeed/getTagFeed',
                getTagFeedPopular : '/tagFeed/getTagFeedPopular',
                setFeedLikeToggle: '/tagFeed/setFeedLikeToggle',
                setFeedScrapToggle: '/tagFeed/setFeedScrapToggle',
            },
            tagRelation:{
                getTagRelationTag : "/relations/getRelatedTag",
                getTagRelationUser : "/relations/getRelatedUser",
            },
            memberFeed:{
                getMemberFeed : '/memberFeed/getMemberFeed',
                getMemberFeedPopular : '/memberFeed/getMemberFeedTogether',
                setFeedLikeToggle: '/memberFeed/setFeedLikeToggle',
                setFeedScrapToggle: '/memberFeed/setFeedScrapToggle',
            },
            searchFeed:{
                searchFeed: '/searchFeed/searchFeed',
            },
            searchHistory:{
                searchHistory: '/searchHistory/searchHistory',
                saveSearchHistory: '/searchHistory/saveSearchHistory',
                deleteSearchHistory: '/searchHistory/deleteSearchHistory',
                deleteAllSearchHistory: '/searchHistory/deleteAllSearchHistory',
            },

        },
        get: {
            member: {},
            myapp: {},
            app: {
                getAppversion: '/app/find'
            },
            sms: {},
            notice: {},
            bank: {
                getBanks: '/bank/find',
            },
            favorite: {},
            terms: {
                findLastTerms: '/terms/findJoinTerms',
            }
        }
    },
    //java model define
    domain: {
        tokenInfo: {
            exp: '',
            iat: '',
            iss: '',
            sub: '',
            idx: '',
            eml: ''
        },
        paging: {
            mbrNo: '',
            offset: 0,
            limit: 0
        }
    },
    axiosConfig,
    axiosConfig2,
    getAxiosConfig: function (locale,type) {
        let axiosUse;
        if(typeof type !== 'undefined' && type=='form'){
            axiosUse = axiosConfig2;
        }else{
            axiosUse = axiosConfig;
        }
        axiosUse.headers["accept-language"] = locale;
        // console.log(localStorage.accessToken);
        if (localStorage.accessToken) {
            axiosUse.headers["Authorization"] = 'Bearer ' + localStorage.accessToken;
        }
        axiosUse.timeout = 160000;
        return axiosUse;
    }
}
