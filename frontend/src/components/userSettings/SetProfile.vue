<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_rgba"></div>
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container">
                        <div class="set_content">
                            <SideMenu></SideMenu>
                            <div class="right_con">
                                <h3>프로필 수정</h3>
                                <div class="email_add02">
                                    <p class="txt01">{{ email }}</p>
                                </div>
                                <div class="profile">
                                    <form>
                                        <fieldset>
                                            <!--<legend>프로필 수정</legend>-->
                                            <div class="profile_img">
                                                <h4>프로필 이미지</h4>
                                                <div class="img_box">
                                                    <div class="img">
                                                        <img src="../../../static/img/no_image.png" alt="기본이미지"
                                                             v-if="defaultImageUse">
                                                        <img alt="사용자이미지" ref="image0" v-bind:src="thumbnail"
                                                             v-if="!defaultImageUse">
                                                    </div>
                                                    <input id="upFile" type="file" v-if="uploadReady"
                                                           @change="onFileChange" class="btn btn-default"
                                                           accept="image/*" style="display:none;">
                                                    <label for="upFile">사진 수정</label>
                                                    <label @click="discardUsingCustomImage">기본 이미지</label>
                                                </div>
                                            </div>
                                            <div class="profile_info">
                                                <h4>닉네임</h4>
                                                <span
                                                    class="text_num">({{ nickName==null ? 0 : nickName.length }}/20)</span>
                                                <div style="position: relative;">
                                                    <input type="text" v-model="nickName" title="닉네임"
                                                           @keyup="nickNameChecker($event.target.value)">
                                                    <span
                                                        v-bind:class="nickNameCheck ? 'nick_check_on' : 'nick_check' "></span>
                                                </div>
                                                <h4>소개</h4>
                                                <span
                                                    class="text_num">({{ content==null ? 0 : content.length }}/100)</span>
                                                <textarea v-model="content" placeholder="마이피드 소개 글을 작성해주세요"></textarea>
                                            </div>
                                            <div class="save_btns">
                                                <button type="button" @click="modifyMemberInfo">저장</button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="intro_txt">
                            <p><a href="#">VORA소개</a> · <a href="#">설정</a> · <a href="#">개인정보처리방침</a> · <a
                                href="#">이용약관</a></p>
                            <p class="app_icon"><a href="#" class="iphone">iPhone 앱</a><a href="#" class="android">Android
                                앱</a></p>
                            <p class="copyright">Copyright @ KYOBO BOOK CENTRE.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import http from '../../http'
    import {mapState, mapActions, mapGetters} from 'vuex'
    import global from '../../global'
    import SideMenu from '../../components/inc/SideMenu'

    export default {
        name: 'SetAccount',
        components: {
            SideMenu
        },
        computed: {
            ...mapGetters([
                'getMemberNickName',
                'getEmailAccountEmail',
                'getMemberContent',
                'getMemberIdx',
                'getFilesFileName',
                'getFilesIdx',
            ])
        },
        data: function () {
            return {
                content: '',
                nickName: '',
                email: '',
                nickNameCheck: true,
                duplicateNick: false,
                uploadReady: true,
                image: false,
                defaultImageUse: true,
                thumbnail: "",
            }
        },
        created: function () {
            this.loadMemberInfoAct(this.getMemberIdx).then((response) => {
                this.nickName = this.getMemberNickName;
                this.email = this.getEmailAccountEmail;
                this.content = this.getMemberContent;
                this.thumbnail = this.getFilesFileName;
                if (this.thumbnail) {
                    this.defaultImageUse = false;
                    this.$refs['image0'].src = this.thumbnail;
                } else {
                    this.defaultImageUse = true;
                }
            }).catch((response) => {
            });
        },
        methods: {
            ...mapActions(['updateMemberInfoAct', 'checkUserNickName', 'loadMemberInfoAct']),
            modifyMemberInfo: function () {
                if (
                    this.nickName != '' &&
                    this.nickName.length <= 10 &&
                    this.nickNameCheck
                ) {
                    let payload = {
                        'idx': this.getMemberIdx,
                        'nickName': this.nickName,
                        'content': this.content,
                        'fileIdx': this.getFilesIdx,
                        'defaultImage': this.defaultImageUse,
                    };
                    if (typeof this.image !== "undefined") {
                        payload['profileImage'] = this.image;
                    }
                    this.updateMemberInfoAct(payload).then((response) => {
                    }).catch((error) => {
                    })
                }
            },
            nickNameChecker: function (value) {
                if (value != "") {
                    value = value.replace(/ /gi,"");
                    this.nickName = value;
                    this.checkUserNickName({'nickName': this.nickName.replace(/^\s+|\s+$/g, "")}).then((response) => {

                        if (response.returnData.nickNameCheck || this.getMemberNickName == this.nickName) {
                            this.nickNameCheck = true;
                            this.duplicateNick = false;
                        } else {
                            this.nickNameCheck = false;
                            this.duplicateNick = true;
                        }

                    }).catch((error) => {
                        // console.log(error.response);
                    });
                }

            },
            onFileChange: function (e) {
                let selectedFiles = e.target.files;
                if (selectedFiles.length == 1) {
                    let maxSize = 3145728;   //20MB
                    for (let i = 0; i < selectedFiles.length; i++) {
                        let fileSize = Math.round(selectedFiles[i].size);
                        if (fileSize > maxSize) {
                            this.checkFileSizeBool = true;
                            this.clear()
                            return;
                        } else {
                            this.image = selectedFiles[i];
                        }
                    }
                    this.defaultImageUse = false;
                    let reader = new FileReader(); //instantiate a new file reader
                    reader.addEventListener('load', function () {
                        this.$refs['image0'].src = reader.result;
                    }.bind(this), false);  //add event listener
                    reader.readAsDataURL(this.image);
                } else {
                    this.clear()
                    return;
                }
            },
            discardUsingCustomImage: function () {
                if (this.$refs['image0']) {
                    document.getElementById("upFile").value = null
                    this.image = {};
                    delete this.$refs['image0'].img;
                }
                this.defaultImageUse = true;
            },
            clear: function () {
                this.uploadReady = false
                this.$nextTick(() => {
                    this.uploadReady = true
                })
            }
        }
    }
</script>

<style scoped>

</style>
