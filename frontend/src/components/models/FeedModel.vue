<template>
    <div class="feed_content">
        <div class="feed_info">
            <div class="img">
                <a @click="findMemberByIdx(feed.member.idx)">
                    <img v-bind:src="getProfileImage(feed.memberProfile)" alt="사용자이미지">
                </a>
            </div>
            <div class="con">
                <a @click="findMemberByIdx(feed.member.idx)"
                    v-bind:class="{'vora_icon':feed.member.businessYn=='Y','id': feed.member.businessYn=='N'}">
                    {{feed.member.nickName}}
                </a>
                <p class="time">{{ checkDate(feed.createAt)}} · {{feed.locationText}}</p>
            </div>

            <div class="more_btn">
                <button type="button" class="set_btn" @click="toggleShowMenu(feed)"></button>
                <div class="set_wrap" v-if="feed.feedMenuShow">
                    <div class="set_box_list" v-if="feed.member.idx != getMemberIdx">
                        <a>공유하기</a>
                        <a @click="toggleBlockMember(feed.member,feed)" href="#">차단하기</a>
                        <a @click="alertFeed(feed,'feed')" href="#">신고하기</a>
                    </div>
                    <div class="set_box_list" v-else>
                        <a @click="goModify(feed)">수정하기</a>
                        <a @click="deleteFeed(feed)">삭제하기</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="feed_txt">
            <div class="txt_box">
                <FeedContent :idx="feed.idx" :contents="feed.contents" :tags="feed.feedTags"
                             :feedMemberTag="feed.feedMemberTag"/>
            </div>
            <div class="photo-grid2">
                <a @click="moveDetail(feed)">
                <photo-grid :box-border="10" :box-height="'612px'" :box-width="'100%'" v-on:mouseover=""
                            v-on:clickExcess="triggerClick" v-if="feed.fileCount > 0">
                    <img v-for="image in feed.files" v-bind:src="image.fileThumbnail || image.fileName"/>
                </photo-grid>
                </a>
            </div>
        </div>
        <div class="feed_icons">
            <div :class="feed.meLikeCheck ? 'heart_icon_on' : 'heart_icon'">
                <button type="button" @click="toggleLikeBtn(feed)">{{feed.likeCount}}</button>
            </div>
            <div :class="feed.meCommentCheck ? 'reply_icon_on' : 'reply_icon'">
                <button type="button" @click="moveDetail(feed)">{{feed.commentCount}}</button>
            </div>
            <div :class="feed.meScrapCheck ? 'star_icon_on' : 'star_icon'">
                <button type="button" @click="toggleScrapBtn(feed)">{{feed.scrapCount}}</button>
            </div>
        </div>
        <div class="feed_replys" v-if="commentView !=false">
            <div class="reply_con" v-for="comment in feed.commentList.slice(0,3)">
                <template v-if="commentInline!=false">
                    <div class="img">
                        <img v-bind:src="getProfileImage(comment.memberProfile)" @click="findMemberByIdx(comment.commentUser.idx)">
                    </div>
                    <p class="txt">
                        <a @click="findMemberByIdx(comment.commentUser.idx)"
                            v-bind:class="{'vora_icon':comment.commentUser.businessYn=='Y','id': comment.commentUser.businessYn=='N'}">
                            {{comment.commentUser ? comment.commentUser.nickName: ''}}
                        </a>
                        <FeedContent :contents="comment.commentContents" :tags="comment.commentTags" :feedMemberTag="comment.commentMemberTag" :detail="true"/>
                    </p>
                    <!-- 탬매뉴가 detail쪽에만 있어서 일단 주석처리 -->
                    <!-- <div class="more_btn">
                        <button type="button" class="set_btn" @click="toggleShowCommentMenu(comment)"></button>
                        <div class="set_wrap" v-if="comment.showMenuBool">
                            <div class="set_box_list" v-if="comment.commentUser.idx != getMemberIdx">
                                <a>공유하기</a>
                                <a @click="toggleBlockCommentMember(comment.commentUser,feed,comment)">차단하기</a>
                                <a @click="alertFeed(comment,'comment')">신고하기</a>
                            </div>
                            <div class="set_box_list" v-else>
                                <a>수정하기</a>
                                <a @click="deleteComment(comment)">삭제하기</a>
                            </div>
                        </div>
                    </div> -->
                </template>
                <template v-if="commentInline == false">
                    <div class="img">
                        <img v-bind:src="getProfileImage(comment.memberProfile)" @click="findMemberByIdx(comment.commentUser.idx)">
                    </div>
                    <p class="txt"><a @click="findMemberByIdx(comment.commentUser.idx)" class="id"> {{comment.commentUser ? comment.commentUser.nickName: ''}}</a>
                        <FeedContent :contents="comment.commentContents" :tags="comment.commentTags" :feedMemberTag="comment.commentMemberTag" :detail="true"/>
                    </p>
                    <!-- 탬매뉴가 detail쪽에만 있어서 일단 주석처리 -->
                    <!-- <div class="more_btn">
                        <button type="button" class="set_btn" @click="toggleShowCommentMenu(comment)"></button>
                        <div class="set_wrap" v-if="comment.showMenuBool">
                            <div class="set_box_list" v-if="comment.commentUser.idx != getMemberIdx">
                                <a>공유하기</a>
                                <a @click="toggleBlockCommentMember(comment.commentUser,feed,comment)">차단하기</a>
                                <a @click="alertFeed(comment,'comment')">신고하기</a>
                            </div>
                            <div class="set_box_list" v-else>
                                <a>수정하기</a>
                                <a @click="deleteComment(comment)">삭제하기</a>
                            </div>
                        </div>
                    </div> -->
                </template>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState, mapActions, mapGetters} from 'vuex'
    import FeedContent from './FeedContent'

    export default {
        name: 'FeedModel',
        props: ['feed', 'alertModal', 'deleteModal', 'blockMemberModal', 'shareModal', 'commentView', 'commentInline'],
        computed: {
            ...mapGetters([
                'getMemberIdx',
            ])
        },
        components: {
            FeedContent
        },
        created: function () {
            // console.log(this.feed);
        },
        methods: {
            getProfileImage: function (imgObj) {
                if (imgObj)
                    return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
            findMemberByIdx: function (idx) {
                this.$router.push({'name': 'MemberFeed', params: {memberIdx: idx}});
            },
            checkDate: function (timestamps) {
                let temp1 = this.$moment(timestamps);
                let temp2 = this.$moment(new Date());
                return this.$moment.duration(temp1.diff(temp2, "seconds"), "seconds").humanize();
            },
            toggleShowMenu: function (feed) {
                feed.feedMenuShow = !feed.feedMenuShow;
            },
            toggleShowMenu2: function (feed, check) {
                feed.feedMenuShow = check;
            },
            toggleShowCommentMenu: function (comment) {
                console.log(comment);
                comment.showMenuBool = !comment.showMenuBool;
            },
            toggleShowCommentMenu2: function (comment, check) {
                comment.feedCommentMenu = check;
            },
            alertFeed: function (object, type) {
                this.alertModal.show(object, type);
                if (type == 'feed') object.feedMenuShow = false;
                else object.feedCommentMenu = false;
            },
            toggleBlockMember: function (member, feed) {
                this.blockMemberModal.show(member);
                this.toggleShowMenu(feed);
            },
            toggleBlockCommentMember: function (member, comment) {
                this.blockMemberModal.show(member);
                this.toggleShowCommentMenu(comment);
            },
            deleteFeed: function (feed) {
                this.deleteModal.show(feed, "feed");
                feed.feedMenuShow = false;
            },
            deleteComment: function (comment) {
                this.deleteModal.show(comment, "comment");
                comment.feedMenuShow = false;
            },
            goModify: function (feed) {
                this.$store.dispatch('modifyDataSet', feed);
                this.$router.push({name: 'Modify'});
            },
            triggerClick: function () {
            },
            toggleScrapBtn: function (feed) {
                feed.meScrapCheck = !feed.meScrapCheck;
                if (feed.meScrapCheck) {
                    feed.scrapCount = feed.scrapCount + 1;
                } else {
                    feed.scrapCount = feed.scrapCount - 1;
                }
                this.$store.dispatch('toggleMainScrapButtonAct', {idx: feed.idx, uidx: this.getMemberIdx});
            },
            toggleLikeBtn: function (feed) {

                feed.meLikeCheck = !feed.meLikeCheck;
                if (feed.meLikeCheck) {
                    feed.likeCount = feed.likeCount + 1;
                } else {
                    feed.likeCount = feed.likeCount - 1;
                }
                this.$store.dispatch('toggleMainLikeButtonAct', {idx: feed.idx, uidx: this.getMemberIdx});
            },
            moveDetail: function (feed) {
                this.$router.push({'name': 'FeedView', params: {feedIdx: feed.idx}});
            },
        },
    }
</script>
<style scoped>

</style>
