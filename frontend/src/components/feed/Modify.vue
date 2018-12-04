<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <!--파일 용량 확인-->
            <modal v-if="checkFileSizeBool">
                <div slot="body">
                    <!-- 비밀번호변경 팝업 -->
                    <div class="pw_send_view popup">
                        <div class="popup_wrap">
                            <div class="tit">
                                <p>파일 사이즈 안내</p>
                                <span class="close" @click="closePop"><img src="../../../static/img/esc_icon.png"
                                                                           alt="닫기"></span>
                            </div>
                            <p class="link_txt">파일 사이즈는 3MB 이내로 등록 가능합니다.</p>
                            <div class="btns">
                                <button type="button" class="btn02" @click="closePop">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <!--파일 개수 확인-->
            <modal v-if="checkFileNumber">
                <div slot="body">
                    <!-- 비밀번호변경 팝업 -->
                    <div class="pw_send_view popup">
                        <div class="popup_wrap">
                            <div class="tit">
                                <p>파일 갯수 안내</p>
                                <span class="close" @click="closePop"><img src="../../../static/img/esc_icon.png"
                                                                           alt="닫기"></span>
                            </div>
                            <p class="link_txt">최대 20개의 이미지만 등록 가능합니다.</p>
                            <div class="btns">
                                <button type="button" class="btn02" @click="closePop">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </modal>
            <!-- 위치태그 팝업 -->
            <Modal v-if="locationBoolpop">
                <div slot="body">
                    <div class="maptag_view">
                        <div class="maptag_wrap">
                            <div class="tit">
                                <p>위치태그</p>
                                <span class="close" @click="closePop"><img src="../../../static/img/esc_icon.png"
                                                                           alt="닫기"></span>
                            </div>
                            <div class="search_con">
                                <input type="text" v-model="searchLocationWord"
                                       v-on:keypress.13="$refs.searchGo.click()">
                                <input type="submit" ref="searchGo" class="search_icon" @click="findLocation(true)">
                            </div>
                            <div class="maptag_list">
                                <div class="list" v-for="location in findedLocations">
                                    <p class="area" @click="saveLocation(location)"><span
                                        v-html="location.title"></span></p>
                                    <p class="add" @click="saveLocation(location)">{{location.roadAddress}}</p>
                                </div>
                            </div>
                            <div class="more_btn">
                                <button type="button" v-if="totalCount != 0" @click="upLocationPage(false)">더보기
                                    {{page}}/{{Math.ceil(totalCount/pagePerCount)}}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </Modal>
            <Modal v-if="actionBoolpop">
                <div slot="body">
                    <!-- 활동태그 팝업 -->
                    <div class="activetag_view">
                        <div class="activetag_wrap">
                            <div class="tit">
                                <p>활동태그</p>
                                <span class="close" @click="closePop"><img src="../../../static/img/esc_icon.png"
                                                                           alt="닫기"></span>
                            </div>
                            <div class="search_con">
                                <input type="text" v-model="searchword" v-on:keyup.enter="$refs.searchAct.click()">
                                <input type="submit" ref="searchAct" class="search_icon"
                                       @click="callSearchFunctions(true)">
                            </div>
                            <div class="activetag_list">
                                <ul class="tabs02">
                                    <li @click="activeTab('tab_a')"><a>도서</a></li>
                                    <li @click="activeTab('tab_b')"><a>영화</a></li>
                                    <li @click="activeTab('tab_c')"><a>공연</a></li>
                                    <li @click="activeTab('tab_d')"><a>전시</a></li>
                                </ul>
                                <div id="tab01" class="tab_content02" v-if="tab_a_bool">
                                    <p class="num">총 {{totalCount}}건</p>
                                    <div class="book_info" v-for="media in mediaList">
                                        <a @click="saveAction(media)">
                                            <div class="book_img">
                                                <img :src="checkImages(media.imgPcUrl)" alt="media.title"
                                                     @error="replaceByDefault">
                                            </div>
                                            <div class="book_txt">
                                                <p class="tit01">{{media.bookNm}}</p>
                                                <p class="con01">{{media.author}}</p>
                                            </div>
                                        </a>
                                    </div>

                                </div>
                                <div id="tab02" class="tab_content02" v-if="tab_b_bool">
                                    <p class="num">총 {{totalCount}}건</p>
                                    <div class="book_info" v-for="media in mediaList">
                                        <a @click="saveAction(media)">
                                            <div class="book_img">
                                                <img :src="checkImages(media.mimage)" alt="media.mtitle"
                                                     @error="replaceByDefault">
                                            </div>
                                            <div class="book_txt">
                                                <p class="tit01" v-html="media.mtitle"></p>
                                                <p class="con01">감독 : {{media.director}}</p>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <div id="tab03" class="tab_content02" v-if="tab_c_bool">
                                    <p class="num">총 {{totalCount}}건</p>
                                    <div class="book_info" v-for="media in mediaList">
                                        <a @click="saveAction(media)">
                                            <div class="book_img">
                                                <img :src="checkImages(media.cimage)" alt="media.ctitle"
                                                     @error="replaceByDefault">
                                            </div>
                                            <div class="book_txt">
                                                <p class="tit01">{{media.ctitle}}</p>
                                                <p class="con01">{{media.startDate}} ~ {{media.endDate}}</p>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <div id="tab04" class="tab_content02" v-if="tab_d_bool">
                                    <p class="num">총 {{totalCount}}건</p>
                                    <div class="book_info" v-for="media in mediaList">
                                        <a @click="saveAction(media)">
                                            <div class="book_img">
                                                <img :src="checkImages(media.cimage)" alt="media.ctitle"
                                                     @error="replaceByDefault">
                                            </div>
                                            <div class="book_txt">
                                                <p class="tit01">{{media.ctitle}}</p>
                                                <p class="con01">{{media.startDate}} ~ {{media.endDate}}</p>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="more_btn">
                                <button type="button" @click="upPage" v-if="totalCount != 0">더보기
                                    {{page}}/{{Math.ceil(totalCount/pagePerCount)}}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </Modal>
            <Modal v-if="linkBoolpop">
                <div slot="body">
                    <!-- 링크삽입 팝업 -->
                    <div class="link_view">
                        <div class="link_wrap">
                            <div class="tit">
                                <p>링크 삽입하기</p>
                                <span class="close" @click="closePop"><img src="../../../static/img/esc_icon.png"
                                                                           alt="닫기"></span>
                            </div>
                            <form>
                                <fieldset>
                                    <legend>링크 작성</legend>
                                    <p class="link_txt">해당 링크를 작성해주세요</p>
                                    <div class="link_con">
                                        <input type="text" title="링크작성" id="link_input" v-model="link1">
                                    </div>
                                    <div class="btns">
                                        <button type="button" class="btn01" @click="closePop">취소</button>
                                        <button type="button" class="btn02" @click="confirmLink">확인</button>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </Modal>
            <Modal v-if="mailBoolpop">
                <div slot="body">
                    <!-- 이메일 인증 팝업 -->
                    <div class="email_view">
                        <div class="email_wrap">
                            <div class="tit">
                                <p>이메일 인증하기</p>
                                <span class="close" @click="closePop"><img src="../../../static/img/esc_icon.png"
                                                                           alt="닫기"></span>
                            </div>
                            <form>
                                <fieldset>
                                    <legend>이메일 인증하기</legend>
                                    <p class="email_txt">가입하신 메일로 인증하시면 글 작성이 가능합니다.</p>
                                    <div class="btns">
                                        <button type="button" class="btn01">취소</button>
                                        <button type="button" class="btn02">메일전송</button>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </Modal>

            <div class="bg_grey">
                <div class="main_inner02">


                    <div id="A_Container_C">
                        <div class="write_quick_menu_m">
                            <ul>
                                <li>
                                    <button type="button" class="menu_btn01"
                                            @click="locationBoolpop = !locationBoolpop">
                                        위치
                                    </button>
                                </li>
                                <li>
                                    <button type="button" class="menu_btn02" @click="imgBool = !imgBool">이미지삽입</button>
                                </li>
                                <li>
                                    <button type="button" class="menu_btn03" @click="linkBoolpop = !linkBoolpop">동영상링크
                                        첨부
                                    </button>
                                </li>
                                <li>
                                    <button type="button" class="menu_btn04" @click="actionBoolpop = !actionBoolpop">
                                        활동태그
                                    </button>
                                </li>
                            </ul>
                        </div>

                        <div class="feed_content">
                            <div class="feed_info">
                                <div class="img">
                                    <a href="#">
                                        <img
                                            v-bind:src="getProfileImage({'fileThumbnail':getFilesFileThumbnail,'fileName':getFilesFileName})">
                                    </a>
                                </div>
                                <div class="con">
                                    <a href="#" class="id">{{getMemberNickName}}</a>
                                    <p class="time">{{checkDate(getModifyData.createAt)}} · {{getModifyData.locationText}} </p>
                                </div>
                            </div>
                            <div class="write_content">
                                <div class="txt_box">
                                    <at-ta :members="suggestFollowers" name-key="name">
                                        <template slot="item" slot-scope="s">
                                            <!--<img :src="s.item.avatar">-->
                                            <span v-text="s.item.name"></span>
                                        </template>
                                        <textarea ref="contentbox" class="editor" contenteditable v-model="contents"
                                                  placeholder="당신은 지금 무엇을 하고 있나요?"
                                                  @change="onContentsChange"></textarea>
                                        <!--<textarea placeholder="당신은 지금 무엇을 하고 있나요?" v-model="contents" @change="onContentsChange"></textarea>-->
                                    </at-ta>
                                </div>
                                <!--location-->
                                <div class="location" v-if="locationResult">
                                    <p><span v-html="selectedLocation.title"></span> 에서</p>
                                    <div class="esc_btn">
                                        <button type="button" @click="deleteLocation">닫기버튼</button>
                                    </div>
                                </div>
                                <!--images-->
                                <div class="photo_upload" v-if="imgBool">
                                    <ul>
                                        <li v-for="(image, key) in files">
                                            <img class="preview" :src="image.fileName" @click="deleteFiles(image.idx)"/>
                                        </li>
                                        <li v-for="(image, key2) in images" :key="image.name">
                                            <img class="preview" v-bind:ref="'image' +parseInt( key2 )"
                                                 @click.prevent="deleteImages(key2)"/>
                                        </li>

                                        <li class="upload_more" @click="$refs.callFile.click()">
                                            <input type="file" v-if="uploadReady" ref="callFile" @change="onFileChange"
                                                   class="btn btn-default" accept="image/*" multiple
                                                   style="display: none">
                                        </li>
                                    </ul>

                                    <div class="esc_btn">
                                        <button type="button" @click="closeImages">닫기버튼</button>
                                    </div>
                                </div>
                                <!--link-->
                                <div class="link" v-if="linkResult">
                                    <youtube v-if="youtubeId" v-bind:video-id="youtubeId" width="100%"/>
                                    <p><span>{{link1}}</span></p>
                                    <div class="esc_btn">
                                        <button type="button" @click="deleteLink">닫기버튼</button>
                                    </div>
                                </div>
                                <!--action tags-->
                                <div class="action_info" v-if="bookResult1" v-for="media in actionBooks">
                                    <div class="book_info">
                                        <div class="action_piece">
                                            <div class="book_img">
                                                <img :src="media.imgPcUrl" alt="media.title">
                                            </div>
                                            <div class="book_txt">
                                                <p class="tit01">{{media.bookNm}}</p>
                                                <p class="con01">{{media.author}} <br>

                                                </p>
                                            </div>
                                            <div class="esc_btn">
                                                <button type="button" @click="deleteBooks(media)">닫기버튼</button>
                                            </div>
                                        </div>
                                        <div class="book_mark">
                                            <p>도서</p>
                                        </div>
                                    </div>
                                </div>
                                <!--action tags-->
                                <div class="action_info" v-if="bookResult2" v-for="media in actionMovies">
                                    <div class="book_info">
                                        <div class="action_piece">
                                            <div class="book_img">
                                                <img :src="media.mimage"
                                                     alt="media.mtitle">
                                            </div>
                                            <div class="book_txt">
                                                <p class="tit01" v-html="media.mtitle"></p>
                                                <p class="con01">{{media.director}}</p>
                                            </div>
                                            <div class="esc_btn">
                                                <button type="button" @click="deleteMovie(media)">닫기버튼</button>
                                            </div>
                                        </div>
                                        <div class="movie_mark">
                                            <p>영화</p>
                                        </div>
                                    </div>
                                </div>
                                <!--action tags-->
                                <div class="action_info" v-if="bookResult3" v-for="media in actionculExhi">
                                    <div class="book_info">
                                        <div class="action_piece">
                                            <div class="book_img">
                                                <img :src="media.cimage"
                                                     alt="media.ctitle">
                                            </div>
                                            <div class="book_txt">
                                                <p class="tit01">{{media.ctitle}}</p>
                                                <p class="con01">{{media.startDate}} ~ {{media.endDate}}</p>
                                            </div>
                                            <div class="esc_btn">
                                                <button type="button" @click="deleteCulExhi(media)">닫기버튼</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="performance_mark" v-if="media.cateName1 == '공연'">
                                        <p>공연</p>
                                    </div>
                                    <div class="exhibit_mark" v-else>
                                        <p>전시</p>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import AtTa from 'vue-at/dist/vue-at-textarea'
    import Modal from '../../components/inc/Modal'
    import Moment from 'vue-moment'
    import Axios from 'axios'
    import {mapActions, mapGetters} from 'vuex'
    import global from '../../global'

    export default {
        name: 'Modify',
        components: {
            Modal,
            Moment,
            Axios,
            AtTa
        },
        data: function () {
            return {
                selectedFriend: '',
                locationBoolpop: false,
                actionBoolpop: false,
                linkBoolpop: false,
                mailBoolpop: false,
                locationResult: false,
                imgPreview: false,
                linkResult: false,
                bookResult1: false,
                bookResult2: false,
                bookResult3: false,
                imgBool: false,
                images: [],
                selectedFile: '',
                link1: '',
                youtubeId: '',
                tab_a_bool: true,
                tab_b_bool: false,
                tab_c_bool: false,
                tab_d_bool: false,
                activedTab: 'tab_a',
                searchword: '',
                page: 1,
                mediaList: [],
                totalCount: 0,
                pagePerCount: 0,
                actionBooks: [],
                actionMovies: [],
                actionculExhi: [],
                searchLocationWord: '',
                findedLocations: [],
                selectedLocation: {},
                contents: '',
                uIdx: 0,
                tags: [],
                followers: [],
                suggestFollowers: [{
                    name: '',
                    idx: 0
                }],
                checkFileSizeBool: false,
                uploadReady: true,
                checkFileNumber: false,
                prevTab: '',
                nickName: '',
                files: [],
                originalTags: [],
                originalFollwers: [],
                deleteLocationIdx: 0,
                deleteFilesIdx: [],
                deleteBooksIdx: [],
                deleteMoviesIdx: [],
                deleteCultureIdx: [],
                deleteTagsIdx: [],
                deleteFollowersIdx: [],
                fileCnt: 0,
                modifyData: {}
            }
        },
        computed: {
            ...mapGetters([
                'getFilesFileName',
                'getFilesFileThumbnail',
                'getMemberNickName',
                'getModifyData',
                'getMemberIdx'
            ])
        },
        created: function () {
            this.$store.dispatch('findFeedDetailData', {
                idx: this.getModifyData.idx,
                uidx: this.getMemberIdx
            }).then((response) => {
                this.modifyData = response;
                this.changeMentionTags();
                this.saveAllContents();
            }).catch((error) => {
                console.log(error);
            });



        },
        methods: {
            ...mapActions([
                'findFollowers',
                'findFeedDetailData',
                'modifyDataSet'
            ]),
            checkDate: function (timestamps) {
                let temp1 = this.$moment(timestamps);
                let temp2 = this.$moment(new Date());
                return this.$moment.duration(temp1.diff(temp2, "seconds"), "seconds").humanize();
            },
            changeMentionTags: function () {
                this.originalFollwers = this.modifyData.feedMemberTag;

                if(this.modifyData.files){
                    this.imgBool = true;
                    this.files = this.modifyData.files;
                }

                if(this.modifyData.link1) {
                    this.link1 = this.modifyData.link1;
                    this.linkResult = true;
                }
                if(this.modifyData.feedLocations){
                    this.selectedLocation = this.modifyData.feedLocations;
                    this.locationResult = true;
                }else{
                    this.selectedLocation = {};
                    this.selectedLocation.idx = 0;
                    this.locationResult = false;
                }

                if(this.modifyData.books){
                    this.actionBooks = this.modifyData.books;
                    this.bookResult1 = true;
                }

                if(this.modifyData.movies){
                    this.actionMovies = this.modifyData.movies;
                    this.bookResult2 = true;
                }

                if(this.modifyData.culture) {
                    this.actionculExhi = this.modifyData.culture;
                    this.bookResult3 = true;
                }

                this.fileCnt = this.modifyData.fileCount;

                if(this.modifyData.feedTags){
                    let stringArr = [];
                    for (let i = 0; i < this.modifyData.feedTags.length; i++) {
                        stringArr.push(this.modifyData.feedTags[i].tag);
                    }
                    this.originalTags = this.modifyData.feedTags;
                    this.tags = stringArr;
                }

                const regex = /(ꊞ[0-9]+)/gm;
                let content_split = this.modifyData.contents.split(regex);
                for(i in content_split){
                    if(content_split[i].startsWith('ꊞ')){
                        let idx = parseInt(content_split[i].replace(/ꊞ/gi, ''));
                        let member = this.originalFollwers.filter(member => member.idx == idx);
                        if(member.length){
                            this.followers.push({oIdx: this.getMemberIdx, tIdx: member[0].idx});
                            let nickName = '@'+member[0].nickName;
                            content_split[i] = nickName;
                            //this.modifyData.contents = this.modifyData.contents.replace(new RegExp('ꊞ'+idx,'gm'),nickName);
                        }
                    }
                }
                this.contents = content_split.join('');
            },
            deleteFiles: function (idx) {
                this.deleteFilesIdx.push(idx);
                for (let i = 0; i < this.files.length; i++) {
                    if (idx == this.files[i].idx) {
                        this.files.splice(i, 1);
                        break;
                    }
                }
            },
            deleteImages: function (key2) {
                this.images.splice(key2, 1);
                this.saveAllContents();
            },
            findFollowFriend: function (value) {
                let checker = true;
                for(let i=0; i<this.suggestFollowers.length; i++){
                    if(this.suggestFollowers[i].name == value){
                        checker = false;
                        break;
                    }
                }
                if(checker){
                    this.$store.dispatch('findFollowers', {idx: this.getMemberIdx, nickName: value}).then((response) => {
                        // console.log(response.returnData);
                        for (let i = 0; i < response.returnData.length; i++) {
                            this.suggestFollowers.push({
                                name: response.returnData[i].nickName,
                                idx: response.returnData[i].idx
                            })
                        }
                        for(let i=0; i<this.suggestFollowers.length; i++){
                            if(this.suggestFollowers[i].idx == 0){
                                this.suggestFollowers.splice(i,1);
                            }
                        }
                        this.suggestFollowers = Array.from(this.suggestFollowers.reduce((m, t) => m.set(t.idx, t), new Map()).values());

                    }).catch((response) => {
                        console.log(response);
                    })
                }
            },
            saveAllContents: function () {
                for (let j = 0; j < this.tags.length; j++) {
                    for (let i = 0; i < this.originalTags.length; i++) {
                        if (this.tags[j] == this.originalTags[i].tag) {
                            this.originalTags.splice(i, 1);
                            break;
                        }
                    }
                }
                for (let i = 0; i < this.originalTags.length; i++) {
                    this.deleteTagsIdx.push(this.originalTags[i].idx);
                }

                let saveContent = this.$refs.contentbox.value;
                for (let j = 0; j < this.followers.length; j++) {
                    for (let i = 0; i < this.originalFollwers.length; i++) {
                        if (this.followers[j].tIdx == this.originalFollwers[i].idx) {
                            this.suggestFollowers.push({idx:this.originalFollwers[i].idx,name:this.originalFollwers[i].nickName});
                            this.originalFollwers.splice(i, 1);
                            break;
                        }
                    }
                }

                for (let i = 0; i < this.originalFollwers.length; i++) {
                    this.deleteFollowersIdx.push(this.originalFollwers[i].idx);
                }

                //follow 처리
                const regex = /(@[^ @]+)/gm;
                let content_split = this.contents.split(regex);
                for(i in content_split){
                    if(content_split[i].startsWith('@')){
                        let nickName = content_split[i].replace(/@/gi, '');
                        let member = this.suggestFollowers.filter(member => member.name == nickName);
                        if(member.length){
                            let follower = {oIdx: this.getMemberIdx, tIdx: member[0].idx};
                            this.followers.push(follower);
                            content_split[i] = 'ꊞ'+member[0].idx;
                        }
                    }
                }
                let end = content_split.join('');

                if(this.selectedLocation.idx === undefined){
                    this.selectedLocation.idx == 0;
                }


                const saveData = {
                    idx: this.getModifyData.idx,
                    uIdx: this.getMemberIdx,
                    contents: end,
                    link1: this.link1,
                    feedLocations: this.selectedLocation,
                    saveBooks: this.actionBooks,
                    saveMovies: this.actionMovies,
                    saveCultureAndExhibition: this.actionculExhi,
                    multipartFiles: this.images,
                    tags: this.tags,
                    followers: this.followers,
                    deleteLocationIdx: this.deleteLocationIdx,
                    deleteFilesIdx: this.deleteFilesIdx,
                    deleteBooks: this.deleteBooksIdx,
                    deleteMovies: this.deleteMoviesIdx,
                    deleteCulture: this.deleteCultureIdx,
                    deleteTags: this.deleteTagsIdx,
                    deleteFollowers: this.deleteFollowersIdx
                }

                this.$store.dispatch('modifyDataSet', saveData);

            },
            deleteLocation: function () {
                if (this.selectedLocation.idx != undefined) {
                    this.deleteLocationIdx = this.selectedLocation.idx
                }

                this.locationResult = false;
                this.selectedLocation = {};
                this.saveAllContents();
            },
            deleteLink: function () {
                this.link1 = '';
                this.linkResult = false;
                this.saveAllContents();
            },
            deleteBooks: function (media) {
                console.log(media);
                for (let i = 0; i < this.actionBooks.length; i++) {
                    if (media.kyoboId == this.actionBooks[i].kyoboId) {
                        this.actionBooks.splice(i, 1);
                        break;
                    }
                }
                this.deleteBooksIdx.push(media.kyoboId);
                this.saveAllContents();
            },
            deleteMovie: function (media) {
                for (let i = 0; i < this.actionMovies.length; i++) {
                    if (media.title == this.actionMovies[i].title) {
                        this.actionMovies.splice(i, 1);
                        break;
                    }
                }
                if (media.idx != undefined) {
                    this.deleteMoviesIdx.push(media.idx);
                }

                this.saveAllContents();
            },
            deleteCulExhi: function (media) {
                for (let i = 0; i < this.actionculExhi.length; i++) {
                    if (media.id == this.actionculExhi[i].id) {
                        this.actionculExhi.splice(i, 1);
                        break;
                    }
                }

                if (media.idx != undefined) {
                    this.deleteCultureIdx.push(media.idx);
                }
                this.saveAllContents();
            },
            saveLocation: function (location) {

                this.selectedLocation = {
                    idx : 0,
                    address: location.address,
                    category: location.category,
                    description: location.description,
                    link: location.link,
                    mapx: location.mapx,
                    mapy: location.mapy,
                    roadAddress: location.roadAddress,
                    title: location.title
                }

                // console.log("locations ===",this.selectedLocation);


                this.locationResult = true;
                this.closePop();
            },
            upLocationPage: function (bool) {
                let limit = Math.ceil(this.totalCount / this.pagePerCount)
                if (this.page < limit) {
                    this.page = this.page + 1;
                    this.findLocation(bool);
                } else {
                    this.page = limit;
                }
            },
            findLocation: function (bool) {
                console.log("bool===", bool);
                let header = {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }

                if (bool) {
                    this.findedLocations = [];
                }

                console.log(this.findedLocations);
                let url = "http://13.124.107.104:8080/api/v1/public/naver/location"
                Axios.post(url, JSON.stringify({
                    query: this.searchLocationWord,
                    display: 10,
                    start: this.page
                }), header).then((response) => {
                    let parseData = JSON.parse(response.data.returnData.naverRes);

                    for (let i = 0; i < parseData.items.length; i++) {
                        this.findedLocations.push(parseData.items[i]);
                    }

                    this.page = response.data.returnData.page;
                    this.totalCount = parseData.total;
                    this.pagePerCount = response.data.returnData.pagePerCount;
                }).catch((response) => {
                    console.log(response);
                })
            },
            saveAction: function (media) {
                if (media.type == 'b') {


                    this.actionBooks.push(media);
                    this.bookResult1 = true;
                }

                if (media.type == 'm') {
                    this.actionMovies.push(media);
                    this.bookResult2 = true;
                }

                if (media.type == 'c') {
                    this.actionculExhi.push(media);
                    this.bookResult3 = true;
                }
                this.closePop();
            },
            activeTab: function (activeTabName) {
                this.activedTab = activeTabName;
                this.page = 1;
                this.mediaList = [];
                this.totalCount = 0;
                this.pagePerCount = 0;
                switch (activeTabName) {
                    case 'tab_a' :
                        this.tab_a_bool = true;
                        this.tab_b_bool = false;
                        this.tab_c_bool = false;
                        this.tab_d_bool = false;
                        break;
                    case 'tab_b' :
                        this.tab_a_bool = false;
                        this.tab_b_bool = true;
                        this.tab_c_bool = false;
                        this.tab_d_bool = false;
                        break;
                    case 'tab_c' :
                        this.tab_a_bool = false;
                        this.tab_b_bool = false;
                        this.tab_c_bool = true;
                        this.tab_d_bool = false;
                        break;
                    case 'tab_d' :
                        this.tab_a_bool = false;
                        this.tab_b_bool = false;
                        this.tab_c_bool = false;
                        this.tab_d_bool = true;
                        break;
                }
                this.callSearchFunctions();
                this.page = 2;
            },
            findActiveTabsQuery: function (searchword, activeTabName, page) {
                let callQuery = ''
                switch (activeTabName) {
                    case 'tab_a' :
                        callQuery = global.findBook;
                        break;
                    case 'tab_b' :
                        callQuery = global.findMovie;
                        break;
                    case 'tab_c' :
                        callQuery = global.searchUrl + searchword + global.perpomence + page;
                        break;
                    case 'tab_d' :
                        callQuery = global.searchUrl + searchword + global.exhibition + page;
                        break;
                }
                return callQuery;
            },
            callSearchFunctions: function (bool) {
                let search = '서울';

                console.log(bool);
                if (bool) {
                    this.mediaList = [];
                }
                if (this.searchword != "") {
                    search = this.searchword;
                } else {
                    search = '서울'
                }

                let defaultWord = this.findActiveTabsQuery(search, this.activedTab, this.page);
                console.log(defaultWord);
                if (this.activedTab == 'tab_c' || this.activedTab == 'tab_d') {
                    Axios.get(defaultWord).then((response) => {
                        for (let i = 0; i < response.data.mediaList.length; i++) {

                            let datas = {
                                actionTagsIdx: 0,
                                cateName1: response.data.mediaList[i].cateName1,
                                cimage: response.data.mediaList[i].image,
                                clink: response.data.mediaList[i].link,
                                clocation: response.data.mediaList[i].location,
                                ctitle: response.data.mediaList[i].title,
                                endDate: response.data.mediaList[i].endDate,
                                id: response.data.mediaList[i].id,
                                idx: 0,
                                startDate: response.data.mediaList[i].startDate,
                                type: 'c'
                            }


                            this.mediaList.push(datas);
                        }
                        this.totalCount = response.data.total;
                        this.pagePerCount = response.data.limit;
                        this.page = response.data.page;

                    }).catch((response) => {
                        console.log(response);
                    })
                } else {
                    let header = {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }

                    Axios.post(defaultWord, JSON.stringify({
                        query: search,
                        display: 10,
                        start: this.page
                    }), header).then((response) => {
                        if (this.activedTab == 'tab_b') {
                            let parseData = JSON.parse(response.data.returnData.naverRes);
                            for (let i = 0; i < parseData.items.length; i++) {

                                let datas = {
                                    actionTagsIdx: 0,
                                    actor: parseData.items[i].actor,
                                    director: parseData.items[i].director,
                                    idx: 0,
                                    mimage: parseData.items[i].image,
                                    mlink: parseData.items[i].link,
                                    mSubtitle: parseData.items[i].subtitle,
                                    mtitle: parseData.items[i].title,
                                    pubDate: parseData.items[i].pubDate,
                                    userRating: parseData.items[i].userRating,
                                    type: 'm'
                                }

                                this.mediaList.push(datas);
                            }
                            // console.log(parseData);

                            this.totalCount = parseData.total;
                            this.pagePerCount = response.data.returnData.pagePerCount;
                            this.page = response.data.returnData.page;
                        }
                        if (this.activedTab == 'tab_a') {
                            let parseData = response.data.returnData.books;
                            for (let i = 0; i < parseData.length; i++) {
                                parseData[i].type = 'b';
                                parseData[i].actionTagsIdx = 0;
                                this.mediaList.push(parseData[i]);
                            }

                            this.totalCount = response.data.returnData.total;
                            this.pagePerCount = response.data.returnData.pagePerCount;
                            this.page = response.data.returnData.page
                        }


                    }).catch((response) => {
                        console.log(response);
                    })
                }
            },
            upPage: function () {
                let limit = Math.ceil(this.totalCount / this.pagePerCount)
                console.log(limit);
                if (this.page < limit) {
                    this.page = this.page + 1;
                    this.callSearchFunctions(false);
                } else {
                    this.page = limit;
                }
            },
            closePop: function () {
                this.locationBoolpop = false
                this.actionBoolpop = false
                this.linkBoolpop = false
                this.mailBoolpop = false
                this.checkFileSizeBool = false
                this.checkFileNumber = false;
                this.page = 1;
                this.totalCount = 0;
                this.pagePerCount = 10;
                this.searchword = '';
                this.searchLocationWord = '';
                this.mediaList = [];
                this.findedLocations = [];
                this.saveAllContents();
            },
            onFileChange(e) {
                let selectedFiles = e.target.files;
                if (selectedFiles.length + this.fileCnt < 21) {
                    let maxSize = 3145728;   //20MB
                    for (let i = 0; i < selectedFiles.length; i++) {
                        let fileSize = Math.round(selectedFiles[i].size);
                        if (fileSize > maxSize) {
                            this.checkFileSizeBool = true;
                            this.clear()
                            return;
                        } else {
                            this.images.push(selectedFiles[i]);
                        }
                    }

                    for (let j = 0; j < this.images.length; j++) {
                        let reader = new FileReader(); //instantiate a new file reader
                        reader.addEventListener('load', function () {
                            this.$refs['image' + parseInt(j)][0].src = reader.result;
                        }.bind(this), false);  //add event listener

                        reader.readAsDataURL(this.images[j]);
                    }
                    this.saveAllContents();
                } else {
                    this.checkFileNumber = true
                    this.clear()
                    return
                }

            },
            clear() {
                this.uploadReady = false
                this.$nextTick(() => {
                    this.uploadReady = true
                })
            },
            confirmLink: function () {
                this.youtubeId = this.$youtube.getIdFromUrl(this.link1);

                this.linkBoolpop = false;
                this.linkResult = true;
                this.saveAllContents();
            },
            closeImages: function () {
                this.images = [];
                this.imgBool = false;
                this.saveAllContents();
            },
            onContentsChange: function () {
                const regex = /#[^\s#]+/gm;
                let m;
                let saveTags = [];
                while ((m = regex.exec(this.contents)) !== null) {
                    // This is necessary to avoid infinite loops with zero-width matches
                    if (m.index === regex.lastIndex) {
                        regex.lastIndex++;
                    }

                    // The result can be accessed through the `m`-variable.
                    m.forEach((match, groupIndex) => {
                        console.log(`Found match, group ${groupIndex}: ${match}`);
                        saveTags.push(match.replace(/#/gi, ''));
                    });
                }

                //Tag 처리
                this.tags = saveTags;

                //follow 처리
                let saveFollow = [];
                const regex2 = /(@[^ @]+)/gm;
                let content_split = this.contents.split(regex);
                for(i in content_split){
                    if(content_split[i].startsWith('@')){
                        let nickName = content_split[i].replace(/@/gi, '');
                        let member = this.suggestFollowers.filter(member => member.name == nickName);
                        if(member.length){
                            let follower = {oIdx: this.getMemberIdx, tIdx: member[0].idx};
                            saveFollow.push(follower);
                        }
                    }
                }
                this.followers = saveFollow;
                // console.log(this.followers);
                this.saveAllContents();
            },
            checkImages: function (image) {
                if (image != "" && image != undefined) {
                    return image
                } else {
                    return this.noImage
                }
            },
            replaceByDefault: function (e) {
                e.target.src = this.noImage;
            },
            getProfileImage: function (imgObj) {
                if (imgObj)
                    return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
        },
        watch: {
            contents: function (value) {
                const regex = /@[^ @]+/gm;
                let m;
                while ((m = regex.exec(this.$refs.contentbox.value)) !== null) {
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
</script>

<style scoped>

</style>
