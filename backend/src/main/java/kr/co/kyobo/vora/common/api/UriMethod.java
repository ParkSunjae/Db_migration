package kr.co.kyobo.vora.common.api;

public class UriMethod {

    /**
     * Member
     */
    public static final String SIGN_IN = "/signIn";
    public static final String SIGN_IN_T = "/signInT";
    public static final String SIGN_OUT = "/signOut";
    public static final String SIGN_UP = "/signUp";


    public static final String CHECK_NICKNAME = "/checkNickName";
    public static final String LOAD_MEMBERPROFILE = "/loadMemberProfile";
    public static final String MODIFY_MEMBERPROFILE = "/modifyMemberProfile";
    public static final String SAVE_MEMBER_YEAR = "/saveYear";

    /**
     * Feeds
     */
    public static final String FEEDS_POST = "/post";
    public static final String FEEDS_WRITE = "/write";

    public static final String FIND_FOLLOWERS = "/findFollowers";
    public static final String GET_MY_SCRAPS = "/getMyScrapList";
    public static final String GET_MY_FEED = "/getMyFeed";
    public static final String GET_TOGETHER = "/getToGetter";
    public static final String SET_FEED_LIKE_TOGGLE = "/setFeedLikeToggle";
    public static final String SET_FEED_SCRAP_TOGGLE = "/setFeedScrapToggle";
    public static final String FEED_VIEW = "/feedView";
    public static final String SET_COMMENT_LIKE_TOGGLE = "/commentLikeToggle";
    public static final String REMOVE_COMMENT = "/removeComment";
    public static final String UPDATE_COMMENT = "/updateComment";

    public static final String GET_MAIN_FEED = "/getMainFeed";
    public static final String DELETE_FEED = "/deleteFeed";

    public static final String GET_TAG_FEED = "/getTagFeed";
    public static final String GET_TAG_FEED_POPULAR = "/getTagFeedPopular";

    public static final String GET_MEMBER_FEED = "/getMemberFeed";
    public static final String GET_MEMBER_FEED_TOGETER = "/getMemberFeedTogether";

    /**
     * MemberBlockMember
     */
    public static final String FIND_BLOCKMEMBERS = "/findBlockMembers";
    public static final String DELETE_MEMBERBLOCKMEMBERS = "/deleteMemberBlockMembers";
    public static final String TOGGLE_MEMBER_BLOCK_MEMBER = "/toggleMemberBlockMember";

    /**
     * BusinessCategory
     */
    public static final String BUSINESSCATEGORY1_LIST = "/getBusinessCategory1";
    public static final String BUSINESSCATEGORY2_LIST = "/getBusinessCategory2";


    public static final String FIND_COMMENT_BY_LIKE = "/getCommentByLikeCount";
    public static final String FIND_COMMENT_BY_NEWS = "/getCommentByIdxCount";
    public static final String ADD_FEED_COMMENT = "/addFeedComment";

    /**
     * Notice
     */
    public static final String GET_NOTICE_BY_USE_YN = "/getNoticeByUseYn";
    public static final String GET_NOTICE_ALL = "/getNoticeAll";
    public static final String UPDATE_NOTICE = "/updateNotice";
    public static final String INSERT_NOTICE = "/insertNotice";
    public static final String DELETE_NOTICE = "/deleteNotice";

    /**
     * Help
     */
    public static final String GET_HELP_BY_USE_YN = "/getHelpByUseYn";
    public static final String GET_HELP_ALL = "/getHelpAll";
    public static final String UPDATE_HELP = "/updateHelp";
    public static final String INSERT_HELP = "/insertHelp";
    public static final String DELETE_HELP = "/deleteHelp";

    /**
     * Inquiry
     */
    public static final String SAVE_INQUIRY = "/saveInquiry";
    public static final String UPDATE_INQUIRY = "/updateInquiry";
    public static final String FIND_REQUEST_INQUIRY = "/getInquiry";

    /**
     * tags
     */
    public static final String CALL_TAGS = "/callTags";
    public static final String SAVE_USERS_TAGS = "/saveUsersTags";
    public static final String GET_ADMIN_CHECK_USERS = "/adminCheckUsers";
    public static final String GET_SUGGEST_USERS = "/getSuggestUserFind";

    public static final String SAVE_MEMBER_FOLLOWER = "/saveFollowers";
    public static final String TOGGLE_FOLLOW_MEMBER = "/toggleFollowMember";
    public static final String CHECK_FOLLOW_MEMBER = "/checkFollowMember";

    /**
     * personal recommend
     */
    public static final String TAG_RECOMMEND_PC = "/tagRecommendPC";
    public static final String TAG_RECOMMEND_MOBILE = "/tagRecommendMobile";
    public static final String USER_RECOMMEND_PC = "/userRecommendPC";
    public static final String USER_RECOMMEND_MOBILE = "/userRecommendMobile";
    public static final String POST_RECOMMEND = "/postRecommend";
    public static final String CALL_MY_ALARM = "/callMyAlarm";
    public static final String CALL_MY_ALARM_ALL = "/callMyAlarmAll";
    public static final String CALL_MY_ALARM_COUNT = "/callMyAlarmCount";
    public static final String FIND_JOIN_TERMS = "/findJoinTerms";
    public static final String GET_ALL_TERMS = "/getAllTerms";

    /**
     * feed report
     */
    public static final String SAVE_FEED_REPORT = "/saveFeedReport";

    /**
     * feed comment report
     */
    public static final String SAVE_FEED_COMMENT_REPORT = "/saveFeedCommentReport";
    public static final String MAIL_VERIFICATION = "/mailVerification";
    public static final String CHANGE_STATUS= "/changeStatusMail";
    public static final String GET_USER_ACCOUNT = "/getUserAccount";
    public static final String SEND_CERT_MAIL = "/sendCertMail";
    public static final String TEMP_PWD_SEND = "/tempPwdSend";
    public static final String CHECK_EMAIL = "/checkUserMail";

    /**
     * relations
     */
    public static final String GET_RELATED_TAG = "/getRelatedTag";
    public static final String GET_RELATED_USER = "/getRelatedUser";
    public static final String CHECK_PASSWORD = "/checkPassword";

    public static final String GET_ACTION_LIST = "/getActionList";
    public static final String GET_LOGIN_LOG_LIST = "/getLoginLogList";
    public static final String FEEDS_UPDATE = "/update";
    public static final String CHANGE_ALARM_SHOW = "/changeAlarmShow";
    public static final String FEED_UPDATE_DATA = "/findDetailData";

    public static final String MEMBER_EXIT = "/exit";
    public static final String CHECK_DUPLICATE_EMAIL = "/checkDuplicateEmail";

    public static final String SEARCH_FEED = "/searchFeed";

    public static final String SEARCH_HISTORY = "/searchHistory";
    public static final String SAVE_SEARCH_HISTORY = "/saveSearchHistory";
    public static final String DELETE_SEARCH_HISTORY = "/deleteSearchHistory";
    public static final String DELETE_ALL_SEARCH_HISTORY = "/deleteAllSearchHistory";

}
