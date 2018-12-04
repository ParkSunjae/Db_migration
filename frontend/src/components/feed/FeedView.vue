<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_rgba">
            </div>
            <!-- 공유하기 팝업 -->
            <Modal v-if="pop1">
                <div slot="body">
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
            <div class="bg_grey">
                <div class="main_inner02">
                    <div id="A_Container_C">
                        <div class="feed_content">
                            <div class="feed_info">
                                <div class="img">
                                    <a @click="findMemberByIdx(getFeedData.member.idx)">
                                        <img v-bind:src="getProfileImage(getFeedData.memberProfile)">
                                    </a>
                                </div>
                                <div class="con">
                                    <a @click="findMemberByIdx(getFeedData.member.idx)"
                                        v-bind:class="{'vora_icon':getFeedData.member.businessYn=='Y','id': getFeedData.member.businessYn=='N'}">
                                        {{getFeedData.member.nickName}}
                                    </a>
                                    <p class="time">{{checkDate(getFeedData.createAt)}} · {{getFeedData.locationText}} </p>
                                </div>
                                <div class="more_btn">
                                    <button type="button" class="set_btn"
                                            @click="menuShowBool = !menuShowBool"></button>
                                    <div class="set_wrap" v-if="menuShowBool">
                                        <div class="set_box_list" v-if="getFeedData.member.idx != getMemberIdx">
                                            <a>공유하기</a>
                                            <a @click="toggleBlockMember(getFeedData.member)">차단하기</a>
                                            <a @click="alertFeed(getFeedData,'feed')">신고하기</a>
                                        </div>
                                        <div class="set_box_list" v-else>
                                            <a @click="goModify(getFeedData)">수정하기</a>
                                            <a @click="deleteFeed(getFeedData,'feed')">삭제</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="feed_txt">
                                <div class="txt_box" v-if="getFeedData">
                                    <FeedContent ref="feedContent" :contents="getFeedData.contents"
                                                 :tags="getFeedData.feedTags" :feedMemberTag="getFeedData.feedMemberTag"
                                                 :detail="true"/>
                                    <br/>
                                    <p class="place" v-if="getFeedData.feedLocations">- <span
                                        v-html="getFeedData.feedLocations.title"></span> 에서</p>
                                </div>
                                <section class="lazy slider view_imgs" id="galaryView">
                                    <Viewer :images="getFeedData.files" @inited="inited" class="viewer" ref="viewer">
                                        <template slot-scope="scope">
                                            <img v-for="(img, index) in scope.images"
                                                 :src="img.fileThumbnail || img.fileName" :key="index" width="100%">
                                        </template>
                                    </Viewer>
                                </section>

                                <div class="video" v-if="youtubeId">
                                    <youtube v-bind:video-id="youtubeId" width="100%"/>
                                </div>
                                <!--action tags-->
                                <div class="action_info" v-for="book in getFeedData.books">
                                    <a :href="book.kyoboPcUrl" target="_blank">
                                        <div class="book_info">
                                            <div class="action_piece">
                                                <div class="book_img">
                                                    <img :src="book.imgPcUrl" alt="media.title">
                                                </div>
                                                <div class="book_txt">
                                                    <p class="tit01">{{book.bookNm}}</p>
                                                    <p class="con01">{{book.author}} <br>
                                                    </p>
                                                </div>
                                            </div>
                                            <div class="book_mark">
                                                <p>도서</p>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <!--action tags-->
                                <div class="action_info" v-for="movie in getFeedData.movies">
                                    <a :href="movie.mlink" target="_blank">
                                        <div class="book_info">
                                            <div class="action_piece">
                                                <div class="book_img">
                                                    <img :src="movie.mimage" alt="media.mtitle">
                                                </div>
                                                <div class="book_txt">
                                                    <p class="tit01" v-html="movie.mtitle"></p>
                                                    <p class="con01">{{movie.director}}</p>
                                                </div>
                                            </div>
                                            <div class="movie_mark">
                                                <p>영화</p>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <!--action tags-->
                                <div class="action_info" v-for="cul in getFeedData.culture">
                                    <a :href="cul.clink" target="_blank">
                                        <div class="book_info">
                                            <div class="action_piece">
                                                <div class="book_img">
                                                    <img :src="cul.cimage" alt="media.ctitle">
                                                </div>
                                                <div class="book_txt">
                                                    <p class="tit01">{{cul.ctitle}}</p>
                                                    <p class="con01">{{cul.startDate}} ~ {{cul.endDate}}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="performance_mark" v-if="cul.cateName1 == '공연'">
                                            <p>공연</p>
                                        </div>
                                        <div class="exhibit_mark" v-else>
                                            <p>전시</p>
                                        </div>
                                    </a>
                                </div>
                                <link-prevue :url="getFeedData.link1" v-if="viewLikeOpenGraph(getFeedData)"
                                             v-cloak>
                                    <template slot-scope="props">
                                        <div class="tag_site">
                                            <template slot="loading">
                                                <!-- set your custom loading -->
                                                <h1>Loading...</h1>
                                            </template>
                                            <a v-bind:href="props.url" target="_blank">
                                                <div class="tag_site_img" v-if="props.img">
                                                    <img :src="props.img || '../../static/img/no_image.png'"
                                                         :alt="props.title">
                                                </div>
                                                <div class="tag_site_txt">
                                                    <p class="txt01">{{props.title}}</p>
                                                    <p class="txt02">{{props.description}}</p>
                                                </div>
                                            </a>
                                        </div>
                                    </template>
                                </link-prevue>
                            </div>
                            <div class="feed_icons02">
                                <div :class="getFeedData.meLikeCheck ? 'heart_icon_on' : 'heart_icon'">
                                    <button type="button" @click="toggleLikeBtn">좋아요</button>
                                </div>
                                <div class="reply_icon">
                                    <button type="button">댓글</button>
                                </div>
                                <div :class="getFeedData.meScrapCheck ? 'star_icon_on' : 'star_icon'">
                                    <button type="button" @click="toggleScrapBtn">스크랩</button>
                                </div>
                            </div>
                            <div class="feed_icons">
                                <div class="like_num">
                                    <p><span>{{getFeedData.likeCount}}명</span> 이 좋아합니다.</p>
                                </div>
                                <div class="eye_icon">
                                    <button type="button">{{getFeedData.hits}}</button>
                                </div>
                            </div>
                        </div>

                        <div class="feed_content">
                            <!-- s : 메인 tab  -->
                            <ul class="tabs">
                                <li :class="newsBool ? 'active' : ''" @click="activeTabChoice('tab_a')"><a
                                    href="javascript:void(0);">최신</a></li>
                                <li :class="likeBool ? 'active' : ''" @click="activeTabChoice('tab_b')"><a
                                    href="javascript:void(0);">인기</a></li>
                            </ul><!-- e : 메인 tab  -->
                            <div class="reply_write">
                                <form>
                                    <fieldset>
                                        <legend>댓글 입력창</legend>
                                        <div class="reply_img">
                                            <img v-bind:src="getProfileImage2(getFilesFileThumbnail,getFilesFileName)">
                                        </div>
                                        <div class="reply_txtbox">
                                            <at-ta :members="members" name-key="name">
                                                <template slot="item" slot-scope="s">
                                                    <img :src="s.item.avatar">
                                                    <span v-text="s.item.name"></span>
                                                </template>
                                                <textarea class="editor2" contenteditable v-model="commentContents"
                                                          ref="commentParentBox"
                                                          placeholder="댓글을 입력하세요"></textarea>
                                                <button type="button" v-if="addOrUpdate" @click="addCommentParent">확인
                                                </button>
                                                <button type="button" v-if="!addOrUpdate" @click="updateCommentParent">
                                                    수정
                                                </button>
                                                <button type="button" v-if="!addOrUpdate" @click="cancelCommentParent">
                                                    취소
                                                </button>
                                                <!--<div class="editor2" contenteditable></div><button type="button" @click="addCommentParent">확인</button>-->
                                                <!--<input type="text" v-model="commentContents" title="댓글입력창" placeholder="댓글을 입력하세요" contenteditable>-->
                                            </at-ta>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                        <!-- s : 메인 리스트 -->
                        <div class="tab_container">
                            <div id="tab1" class="tab_content" v-if="newsBool">
                                <div class="feed_content">
                                    <!-- s : 메인 tab  -->
                                    <div class="reply_content" v-if="commentList.length == 0">
                                        <div class="reply_txt">
                                            등록된 댓글이 없습니다.
                                        </div>
                                    </div>
                                    <div class="reply_content" v-for="comment in commentList">
                                        <div class="feed_info">
                                            <div class="img02">
                                                <a @click="findMemberByIdx(comment.commentUser.idx)">
                                                    <img v-bind:src="getProfileImage(comment.memberProfile)">
                                                </a>
                                            </div>
                                            <div class="con03">
                                                <a @click="findMemberByIdx(comment.commentUser.idx)" class="vora_icon">{{comment.commentUser.nickName}}</a>
                                                <p class="time">{{checkDate(comment.createAt)}}</p>
                                            </div>
                                            <div class="more_btn">
                                                <button type="button" class="set_btn"
                                                        @click="comment.showMenuBool = !comment.showMenuBool"></button>
                                                <div class="set_wrap" v-if="comment.showMenuBool"
                                                     @blur="comment.showMenuBool = !comment.showMenuBool">
                                                    <div class="set_box_list"
                                                         v-if="comment.commentUser.idx != getMemberIdx">
                                                        <a>공유하기</a>
                                                        <a @click="toggleBlockMember(comment.commentUser)">차단하기</a>
                                                        <a @click="alertFeed(comment,'comment')">신고하기</a>
                                                    </div>
                                                    <div class="set_box_list" v-else>
                                                        <a @click="editCommentParent(comment)">수정</a>
                                                        <a @click="deleteFeed(comment,'commentMaster')">삭제</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="reply_txt">
                                            <FeedContent :contents="comment.commentContents" :tags="comment.commentTags"
                                                         :feedMemberTag="comment.commentMemberTag" :detail="true"/>
                                        </div>
                                        <div class="feed_icons03">
                                            <div :class="comment.meThisCommentLike ? 'heart_icon_on' : 'heart_icon'">
                                                <button type="button" @click="toggleCommentLikeBtn(comment)">좋아요
                                                </button>
                                            </div>
                                            <div class="reply_icon">
                                                <button type="button" @click="toggleCommentChild(comment)">답글달기</button>
                                            </div>
                                        </div>


                                        <div class="reply_write" v-if="comment.childWrite">
                                            <form>
                                                <fieldset>
                                                    <legend>댓글 입력창</legend>
                                                    <div class="reply_img">
                                                        <img
                                                            v-bind:src="getProfileImage2(getFilesFileThumbnail,getFilesFileName)">
                                                    </div>
                                                    <div class="reply_txtbox">
                                                        <at-ta :members="members" name-key="name">
                                                            <template slot="item" slot-scope="s">
                                                                <img :src="s.item.avatar">
                                                                <span v-text="s.item.name"></span>
                                                            </template>
                                                            <textarea class="editor2" contenteditable
                                                                      v-model="commentContents2"
                                                                      :ref="'commentChildBox' + parseInt(comment.idx)"
                                                                      placeholder="댓글을 입력하세요"
                                                                      @focus="checkRefName('commentChildBox' + parseInt(comment.idx))"></textarea>
                                                            <button type="button" v-if="addOrUpdate2"
                                                                    @click="addCommentChild(comment)">확인
                                                            </button>
                                                            <button type="button" v-if="!addOrUpdate2"
                                                                    @click="updateCommentChild">수정
                                                            </button>
                                                            <button type="button" v-if="!addOrUpdate2"
                                                                    @click="cancelCommentChild">취소
                                                            </button>
                                                            <!--<div class="editor2" contenteditable></div><button type="button" @click="addCommentParent">확인</button>-->
                                                            <!--<input type="text" v-model="commentContents" title="댓글입력창" placeholder="댓글을 입력하세요" contenteditable>-->
                                                        </at-ta>
                                                    </div>
                                                </fieldset>
                                            </form>
                                        </div>

                                        <div class="reply_content" v-for="child in comment.children">
                                            <div class="feed_info feed_info_01">
                                                <div class="img02">
                                                    <a @click="findMemberByIdx(child.commentUser.idx)">
                                                        <img v-bind:src="getProfileImage(child.memberProfile)">
                                                    </a>
                                                </div>
                                                <div class="con03">
                                                    <a @click="findMemberByIdx(child.commentUser.idx)"
                                                       class="vora_icon">{{child.commentUser.nickName}}</a>
                                                    <p class="time">{{checkDate(child.createAt)}}</p>
                                                </div>
                                                <div class="more_btn">
                                                    <button type="button" class="set_btn"
                                                            @click="child.showMenuBool = !child.showMenuBool"></button>
                                                    <div class="set_wrap" v-if="child.showMenuBool"
                                                         @blur="child.showMenuBool = !child.showMenuBool">
                                                        <div class="set_box_list"
                                                             v-if="child.commentUser.idx != getMemberIdx">
                                                            <a>공유하기</a>
                                                            <a @click="toggleBlockMember(child.commentUser)">차단하기</a>
                                                            <a @click="alertFeed(child,'comment')">신고하기</a>
                                                        </div>
                                                        <div class="set_box_list" v-else>
                                                            <a @click="editComment(comment,child, ':commentChildBox' + parseInt(comment.idx))">수정</a>
                                                            <a @click="deleteFeed(child,'comment')">삭제</a>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="reply_txt">
                                                    <FeedContent :contents="child.commentContents"
                                                                 :tags="child.commentTags"
                                                                 :feedMemberTag="child.commentMemberTag"
                                                                 :detail="true"/>
                                                </div>
                                                <div class="feed_icons03">
                                                    <div
                                                        :class="child.meThisCommentLike ? 'heart_icon_on' : 'heart_icon'">
                                                        <button type="button" @click="toggleCommentLikeBtn(child)">좋아요
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="tab2" class="tab_content" v-if="likeBool">
                                <div class="feed_content">
                                    <!-- s : 메인 tab  -->
                                    <div class="reply_content" v-if="commentList.length == 0">
                                        <div class="reply_txt">
                                            등록된 댓글이 없습니다.
                                        </div>
                                    </div>
                                    <div class="reply_content" v-for="comment in commentList">
                                        <div class="feed_info">
                                            <div class="img02">
                                                <a @click="findMemberByIdx(comment.commentUser.idx)">
                                                    <img v-bind:src="getProfileImage(comment.memberProfile)">
                                                </a>
                                            </div>
                                            <div class="con03">
                                                <a @click="findMemberByIdx(comment.commentUser.idx)" class="vora_icon">{{comment.commentUser.nickName}}</a>
                                                <p class="time">{{checkDate(comment.createAt)}}</p>
                                            </div>
                                            <div class="more_btn">
                                                <button type="button" class="set_btn"
                                                        @click="comment.showMenuBool = !comment.showMenuBool"></button>
                                                <div class="set_wrap" v-if="comment.showMenuBool"
                                                     @blur="comment.showMenuBool = !comment.showMenuBool">
                                                    <div class="set_box_list"
                                                         v-if="comment.commentUser.idx != getMemberIdx">
                                                        <a>공유하기</a>
                                                        <a @click="toggleBlockMember(comment.commentUser)">차단하기</a>
                                                        <a @click="alertFeed(comment,'comment')">신고하기</a>
                                                    </div>
                                                    <div class="set_box_list" v-else>
                                                        <a @click="editCommentParent(comment)">수정</a>
                                                        <a @click="deleteFeed(comment,'commentMaster')">삭제</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="reply_txt">
                                            <FeedContent :contents="comment.commentContents" :tags="comment.commentTags"
                                                         :feedMemberTag="comment.commentMemberTag" :detail="true"/>
                                        </div>
                                        <div class="feed_icons03">
                                            <div :class="comment.meThisCommentLike ? 'heart_icon_on' : 'heart_icon'">
                                                <button type="button" @click="toggleCommentLikeBtn(comment)">좋아요
                                                </button>
                                            </div>
                                            <div class="reply_icon">
                                                <button type="button" @click="toggleCommentChild(comment)">답글달기</button>
                                            </div>
                                        </div>


                                        <div class="reply_write" v-if="comment.childWrite">
                                            <form>
                                                <fieldset>
                                                    <legend>댓글 입력창</legend>
                                                    <div class="reply_img">
                                                        <img
                                                            v-bind:src="getProfileImage2(getFilesFileThumbnail,getFilesFileName)">
                                                    </div>
                                                    <div class="reply_txtbox">
                                                        <at-ta :members="members" name-key="name">
                                                            <template slot="item" slot-scope="s">
                                                                <img :src="s.item.avatar">
                                                                <span v-text="s.item.name"></span>
                                                            </template>
                                                            <textarea class="editor2" contenteditable
                                                                      v-model="commentContents2"
                                                                      :ref="'commentChildBox' + parseInt(comment.idx)"
                                                                      placeholder="댓글을 입력하세요"
                                                                      @focus="checkRefName('commentChildBox' + parseInt(comment.idx))"></textarea>
                                                            <button type="button" v-if="addOrUpdate2"
                                                                    @click="addCommentChild(comment)">확인
                                                            </button>
                                                            <button type="button" v-if="!addOrUpdate2"
                                                                    @click="updateCommentChild">수정
                                                            </button>
                                                            <button type="button" v-if="!addOrUpdate2"
                                                                    @click="cancelCommentChild">취소
                                                            </button>
                                                            <!--<div class="editor2" contenteditable></div><button type="button" @click="addCommentParent">확인</button>-->
                                                            <!--<input type="text" v-model="commentContents" title="댓글입력창" placeholder="댓글을 입력하세요" contenteditable>-->
                                                        </at-ta>
                                                    </div>
                                                </fieldset>
                                            </form>
                                        </div>

                                        <div class="reply_content" v-for="child in comment.children">
                                            <div class="feed_info feed_info_01">
                                                <div class="img02">
                                                    <a @click="findMemberByIdx(child.commentUser.idx)">
                                                        <img v-bind:src="getProfileImage(child.memberProfile)">
                                                    </a>
                                                </div>
                                                <div class="con03">
                                                    <a @click="findMemberByIdx(child.commentUser.idx)"
                                                       class="vora_icon">{{child.commentUser.nickName}}</a>
                                                    <p class="time">{{checkDate(child.createAt)}}</p>
                                                </div>
                                                <div class="more_btn">
                                                    <button type="button" class="set_btn"
                                                            @click="child.showMenuBool = !child.showMenuBool"></button>
                                                    <div class="set_wrap" v-if="child.showMenuBool"
                                                         @blur="child.showMenuBool = !child.showMenuBool">
                                                        <div class="set_box_list"
                                                             v-if="child.commentUser.idx != getMemberIdx">
                                                            <a>공유하기</a>
                                                            <a @click="toggleBlockMember(child.commentUser)">차단하기</a>
                                                            <a @click="alertFeed(child,'comment')">신고하기</a>
                                                        </div>
                                                        <div class="set_box_list" v-else>
                                                            <a @click="editComment(comment,child)">수정</a>
                                                            <a @click="deleteFeed(child,'comment')">삭제</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="reply_txt">
                                                <FeedContent :contents="child.commentContents"
                                                             :tags="child.commentTags"
                                                             :feedMemberTag="child.commentMemberTag"
                                                             :detail="true"/>
                                            </div>
                                            <div class="feed_icons03">
                                                <div
                                                    :class="child.meThisCommentLike ? 'heart_icon_on' : 'heart_icon'">
                                                    <button type="button" @click="toggleCommentLikeBtn(child)">좋아요
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="feed_content">
                                <div class="more_reply">
                                    <button type="button" @click="getMoreComment">댓글 더보기</button>
                                </div>
                            </div>
                        </div><!-- e : 메인 리스트 -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Viewer from 'v-viewer/src/component.vue'
    import 'viewerjs/dist/viewer.css'
    import LinkPrevue from 'link-prevue'
    import AtTa from 'vue-at/dist/vue-at-textarea'
    import {mapState, mapGetters, mapActions} from 'vuex'

    import FeedContent from '../models/FeedContent'
    import DeleteModel from '../models/DeleteModal'
    import AlertModal from '../models/AlertModal'
    import BlockMemberModal from '../models/BlockMemberModal'

    export default {
        name: 'FeedView',
        components: {
            LinkPrevue,
            AtTa,
            Viewer,
            FeedContent,
            DeleteModel,
            AlertModal,
            BlockMemberModal,
        },
        computed: {
            ...mapGetters([
                'getFeedData',
                'getMemberIdx',
                'getFilesFileName',
                'getFilesFileThumbnail',
            ])
        },
        created: function () {
            this.callDetailData();
        },
        data: function () {
            return {
                pop1: false,
                blockUser: false,
                hideFeeds: false,
                menuShowBool: false,
                noImage: "../../../static/img/no_image.png",
                images: [],
                showImage: false,
                showImage2: false,
                startAt: 0,
                newsBool: true,
                likeBool: false,
                activeTab: 'tab_a',
                commentList: [],
                commentContents: '',
                commentContents2: '',
                commentEmpty: false,
                page: 1,
                members: [{
                    name: '',
                    idx: 0
                }],
                selectedComment: {},
                changeCommentObj: {},
                addOrUpdate: true,
                addOrUpdate2: true,
                commentParent: {},
                youtubeId: '',
                report: false,
                reportType: "",
                followers: [],
                tags: [],
                thisRefs: ''
            }
        },
        methods: {
            ...mapActions([
                'getFeedCall',
                'callCommentOrderByLikeCount',
                'callCommentOrderByNews',
                'toggleLikeButtonAct',
                'toggleScrapButtonAct',
                'addComment',
                'toggleCommentLikeButtonAct',
                'updateCommentAct',
                'toggleMemberBlocmMemberAct',
                'saveFeedsReportAct',
                'saveFeedCommentReportAct',
            ]),
            inited: function (viewer) {
                viewer.options.title = false;
                this.$viewer = viewer
            },
            goModify : function(feed) {
                this.$store.dispatch('modifyDataSet',feed);
                this.$router.push({name : 'Modify'});
            },
            show: function () {
                this.$viewer.show()
            },
            changeBool: function (value) {
                this.showImage2 = value
            },
            viewImageId: function (index) {
                // this.startAt = index;
                // this.showImage2 = true;
                // this.$refs.lightbox.$el.firstChild.querySelectorAll("a img")[index].click();
            },
            callDetailData: function () {
                this.$store.dispatch('getFeedCall', {
                    idx: this.$route.params.feedIdx,
                    uidx: this.getMemberIdx
                }).then((response) => {
                    this.youtubeId = this.$youtube.getIdFromUrl(this.getFeedData.link1);
                    if (this.getFeedData.files.length > 0) {
                        for (let i = 0; i < response.files.length; i++) {
                            this.images.push({
                                src: response.files[i].fileName,
                                thumb: response.files[i].fileThumbnail,
                                caption: response.files[i].idx,
                                srcset: ''
                            });
                        }
                    }
                    this.findNewsCommentList();
                }).catch((response) => {
                });

            },
            checkImages: function (image) {
                if (image != "" && image != undefined) {
                    return image
                } else {
                    return this.noImage
                }
            },
            findTagByIdx: function (idx) {
                this.$router.push({
                    'name': 'TagFeed',
                    params: {
                        tagIdx: idx
                    }
                });
            },
            findMemberByIdx: function (idx) {
                this.$router.push({
                    'name': 'MemberFeed',
                    params: {
                        memberIdx: idx
                    }
                });
            },
            activeTabChoice: function (tab) {
                this.commentList.splice(0, this.commentList.length);
                this.page = 1;
                switch (tab) {
                    case 'tab_a':
                        this.newsBool = true;
                        this.likeBool = false;
                        this.findNewsCommentList();
                        break;
                    case 'tab_b':
                        this.newsBool = false;
                        this.likeBool = true;
                        this.findLikesCommentList();
                        break;
                }
            },
            findLikesCommentList: function () {
                this.$store.dispatch('callCommentOrderByLikeCount', {
                    idx: this.getFeedData.idx,
                    uidx: this.getMemberIdx,
                    page: this.page,
                    limit: 10
                }).then((response) => {
                    if (response.rtns.length > 0) {
                        for (let i = 0; i < response.rtns.length; i++) {
                            this.commentList.push(response.rtns[i]);
                        }
                        this.page = response.page + 1;
                    } else {
                        this.page = response.page - 1;
                    }


                }).catch((response) => {
                    console.log(response);
                })
            },
            findNewsCommentList: function () {
                this.$store.dispatch('callCommentOrderByNews', {
                    idx: this.getFeedData.idx,
                    uidx: this.getMemberIdx,
                    page: this.page,
                    limit: 10
                }).then((response) => {
                    console.log("as",response);
                    if (response.rtns.length > 0) {
                        for (let i = 0; i < response.rtns.length; i++) {
                            this.commentList.push(response.rtns[i]);
                        }
                        this.page = response.page + 1;
                    } else {
                        this.page = response.page - 1;
                    }

                }).catch((response) => {
                    console.log(response);
                })
            },
            makeUploadData: function (str, index, commentIdx) {
                //follow 처리
                let at = "@";
                let saveFollow = [];
                let splData2 = str.split(' ');

                for (let i = 0; i < splData2.length; i++) {
                    //@일때 태그 처리
                    if (splData2[i].indexOf(at) > -1) {
                        const name = splData2[i].replace(/@/gi, '');
                        let userIdx = 0;
                        for (let k = 0; k < this.members.length; k++) {
                            if (name == this.members[k].name) {
                                let follower = {uidx: this.members[k].idx};
                                this.followers.push(follower);
                                userIdx = this.members[k].idx;
                                console.log(userIdx);
                            }
                        }
                        splData2[i] = "ꊞ" + userIdx;
                    }
                }

                let end = "";
                for (let i = 0; i < splData2.length; i++) {
                    end = end + " " + splData2[i];
                }

                console.log("????", end);

                let data = {
                    idx : commentIdx,
                    parentIdx: index,
                    feedIdx: this.getFeedData.idx,
                    uidx: this.getMemberIdx,
                    commentContents: end,
                    likes: 0
                }
                let requestObj = {
                    feedComment: data,
                    follwers: this.followers,
                    tags: this.tags
                }
                return requestObj;
            },
            addCommentParent: function () {

                let data = this.makeUploadData(this.$refs.commentParentBox.value, 0, 0);

                if (this.commentContents != '') {
                    this.commentEmpty = false;
                    this.$store.dispatch('addComment', data).then((response) => {
                        this.commentContents = '';
                        this.commentList = [];
                        this.activeTabChoice(this.activeTab)
                        // console.log(response);
                    }).catch((response) => {
                        console.log(response);
                    })
                } else {
                    this.commentEmpty = true;
                }
            },
            toggleLikeBtn: function () {
                this.getFeedData.meLikeCheck = !this.getFeedData.meLikeCheck;
                if (this.getFeedData.meLikeCheck) {
                    this.getFeedData.likeCount = this.getFeedData.likeCount + 1
                } else {
                    this.getFeedData.likeCount = this.getFeedData.likeCount - 1
                }
                this.$store.dispatch('toggleLikeButtonAct', {
                    idx: this.getFeedData.idx,
                    uidx: this.getMemberIdx
                });
            },
            toggleScrapBtn: function () {
                this.getFeedData.meScrapCheck = !this.getFeedData.meScrapCheck;
                if (this.getFeedData.meScrapCheck) {
                    this.getFeedData.scrapCount = this.getFeedData.scrapCount + 1
                } else {
                    this.getFeedData.scrapCount = this.getFeedData.scrapCount - 1
                }
                this.$store.dispatch('toggleScrapButtonAct', {
                    idx: this.getFeedData.idx,
                    uidx: this.getMemberIdx
                });
            },
            toggleCommentLikeBtn: function (comment) {
                comment.meThisCommentLike = !comment.meThisCommentLike;
                this.$store.dispatch('toggleCommentLikeButtonAct', {
                    commentIdx: comment.idx,
                    uidx: this.getMemberIdx
                });
            },
            toggleCommentChild: function (comment) {
                comment.childWrite = !comment.childWrite;
            },
            addCommentChild: function (comment) {
                let requestObj = this.makeUploadData(this.$refs[this.thisRefs][0].value, comment.idx, 0);

                if (this.commentContents2 != '') {
                    this.commentList = [];
                    this.commentEmpty = false;
                    this.$store.dispatch('addComment', requestObj).then((response) => {
                        this.commentContents2 = '';
                        comment.childWrite = !comment.childWrite;
                        this.thisRefs = '';
                        this.activeTabChoice(this.activeTab)
                        // console.log(response);
                    }).catch((response) => {
                        console.log(response);
                    })
                } else {
                    this.commentEmpty = true;
                }
            },
            findFollowFriend: function (value) {
                let checker = true;
                for (let i = 0; i < this.members.length; i++) {
                    if (this.members[i].name == value) {
                        checker = false;
                        break;
                    }
                }
                if (checker) {
                    this.$store.dispatch('findFollowers', {idx: this.getMemberIdx, nickName: value}).then((response) => {
                        // console.log(response.returnData);
                        for (let i = 0; i < response.returnData.length; i++) {
                            this.members.push({
                                name: response.returnData[i].nickName,
                                idx: response.returnData[i].idx
                            })
                        }
                        for (let i = 0; i < this.members.length; i++) {
                            if (this.members[i].idx == 0) {
                                this.members.splice(i, 1);
                            }
                        }
                        this.members = Array.from(this.members.reduce((m, t) => m.set(t.idx, t), new Map()).values());

                    }).catch((response) => {
                        console.log(response);
                    })
                }
            },
            editCommentParent: function (comment) {
                this.commentContents = comment.commentContents;
                this.addOrUpdate = false;
                this.changeCommentObj = comment;
                comment.showMenuBool = !comment.showMenuBool;
            },
            editComment: function (comment, child, ref) {
                this.changeCommentObj = child;
                this.commentContents2 = child.commentContents;
                this.addOrUpdate2 = false;
                this.toggleCommentChild(comment);
                this.commentParent = comment;
                child.showMenuBool = !child.showMenuBool;
                this.thisRefs = this.$refs[ref.replace(":", "")][0].value;
            },
            updateCommentParent: function () {

                let data = this.makeUploadData(this.$refs.commentParentBox.value, 0, this.changeCommentObj.idx);

                if (this.commentContents != '') {
                    this.changeCommentObj.commentContents = this.commentContents;
                    this.commentEmpty = false;
                    this.$store.dispatch('updateCommentAct', data).then((response) => {

                        for(let i=0; i<this.commentList.length; i++){
                            for(let j=0; j<this.commentList[i].children.length; j++){
                                this.commentList[i].children[j].childWrite = false;
                            }
                        }
                        this.changeCommentObj = {};
                        this.addOrUpdate = true;
                        this.commentContents = '';
                        this.commentList = [];
                        this.activeTabChoice(this.activeTab)
                        // console.log(response);
                    }).catch((response) => {
                        console.log(response);
                    })
                } else {
                    this.commentEmpty = true;
                }
            },
            updateCommentChild: function () {

                let data = this.makeUploadData(this.thisRefs, this.commentParent.idx, this.commentParent.idx);

                if (this.commentContents2 != '') {
                    this.changeCommentObj.commentContents = this.commentContents2;
                    this.commentEmpty = false;
                    this.$store.dispatch('updateCommentAct', data).then((response) => {
                        this.commentParent = {}
                        this.addOrUpdate2 = true;
                        this.commentContents2 = '';
                        this.thisRefs = '';
                        this.commentList = [];
                        this.activeTabChoice(this.activeTab)
                        // console.log(response);
                    }).catch((response) => {
                        console.log(response);
                    })
                } else {
                    this.commentEmpty = true;
                }
            },
            cancelCommentParent: function () {
                this.commentContents = '';
                this.addOrUpdate = true;
                this.changeCommentObj = {};
            },
            cancelCommentChild: function () {
                this.commentContents2 = '';
                this.addOrUpdate2 = true;
                this.changeCommentObj = {};
                this.thisRefs = '';
                this.toggleCommentChild(this.commentParent);
            },
            getMoreComment: function () {
                this.page = this.page + 1;
                if (this.activeTab == 'tab_a') {
                    this.findNewsCommentList();
                } else {
                    this.findLikesCommentList();
                }
            },
            checkDate: function (timestamps) {
                let temp1 = this.$moment(timestamps);
                let temp2 = this.$moment(new Date());
                return this.$moment.duration(temp1.diff(temp2, "seconds"), "seconds").humanize();
            },
            getProfileImage: function (imgObj) {
                if (imgObj)
                    return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
            getProfileImage2: function (imgObj1, imgObj2) {
                return imgObj1 || imgObj2 || '../../../static/img/no_image.png';
            },
            deleteFeed: function (obj, objectType) {
                this.$refs.deleteModal.show(obj, objectType);
            },
            afterDeleteFunction: function (type) {
                if (type == "feed") {
                    this.$router.go(-1);
                } else if (type == "comment") {
                    this.activeTabChoice(this.activeTab)
                } else if (type == "commentMaster") {
                    this.commentList = [];
                    this.activeTabChoice(this.activeTab)
                }
            },
            alertFeed: function (object, type) {
                this.$refs.alertModal.show(object, type);
                if (type == 'feed') object.feedMenuShow = false;
                else object.feedCommentMenu = false;
            },
            toggleBlockMember: function (member) {
                this.$refs.blockMemberModal.show(member);
            },
            viewLikeOpenGraph: function (getFeedDataect) {
                return getFeedDataect.link1 != '' && !this.youtubeId
            },
            checkRefName: function (ref) {
                this.thisRefs = ref;
            }
        },
        watch: {
            commentContents: function (value) {
                const regex1 = /#[^\s#]+/gm;
                let m2;
                let saveTags = [];
                while ((m2 = regex1.exec(this.$refs.commentParentBox.value)) !== null) {
                    // This is necessary to avoid infinite loops with zero-width matches
                    if (m2.index === regex1.lastIndex) {
                        regex1.lastIndex++;
                    }

                    // The result can be accessed through the `m`-variable.
                    m2.forEach((match, groupIndex) => {
                        console.log(`Found match, group ${groupIndex}: ${match}`);
                        saveTags.push({tag: match.replace(/#/gi, '')});
                    });
                }
                this.tags = saveTags;


                const regex = /@[^ @]+/gm;
                let m;
                while ((m = regex.exec(this.$refs.commentParentBox.value)) !== null) {
                    // This is necessary to avoid infinite loops with zero-width matches
                    if (m.index === regex.lastIndex) {
                        regex.lastIndex++;
                    }

                    // console.log(m[m.length - 1]);
                    this.findFollowFriend(m[m.length - 1].replace(/@/gi, ''))
                }
            },
            commentContents2: function (value) {

                const regex1 = /#[^\s#]+/gm;
                const regex = /@[^ @]+/gm;
                let m;
                let m2;
                if (this.thisRefs != '') {
                    let saveTags = [];
                    while ((m2 = regex1.exec(this.$refs[this.thisRefs][0].value)) !== null) {
                        // This is necessary to avoid infinite loops with zero-width matches
                        if (m2.index === regex1.lastIndex) {
                            regex1.lastIndex++;
                        }

                        // The result can be accessed through the `m`-variable.
                        m2.forEach((match, groupIndex) => {
                            console.log(`Found match, group ${groupIndex}: ${match}`);
                            saveTags.push({tag: match.replace(/#/gi, '')});
                        });
                    }
                    this.tags = saveTags;


                    while ((m = regex.exec(this.$refs[this.thisRefs][0].value)) !== null) {
                        // This is necessary to avoid infinite loops with zero-width matches
                        if (m.index === regex.lastIndex) {
                            regex.lastIndex++;
                        }

                        // console.log(m[m.length - 1]);
                        this.findFollowFriend(m[m.length - 1].replace(/@/gi, ''))
                    }
                }

            }
        }
    }
</script>

<style scoped>

</style>
