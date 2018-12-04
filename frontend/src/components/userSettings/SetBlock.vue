<template>
    <div id="A_Container_Wrap">
        <div id="A_Container">
            <div class="bg_grey">
                <div class="main_inner">
                    <div id="A_Container">
                        <div class="set_content">
                            <SideMenu></SideMenu>
                            <div class="right_con">
                                <div class="following_people">
                                    <p class="num">{{ total }}명</p>
                                    <div class="profile" v-for="item in memberBlockMemberList">
                                        <div class="img">
                                            <a href="#">
                                                <img v-bind:src="getProfileImage(memberBlockMemberInfo[item.tidx].profileInfo)" alt="프로필 이미지">
                                            </a>
                                        </div>
                                        <div class="con">
                                            <a v-bind:class="{'vora_icon':memberBlockMemberInfo[item.tidx].businessYn=='Y','id': memberBlockMemberInfo[item.tidx].businessYn=='N'}"
                                               href="#">
                                                {{ memberBlockMemberInfo[item.tidx].nickName }}
                                            </a>
                                            <button type="button" @click="toggleMemberBlocmMember(item.tidx)" class="on"
                                                    v-if="deleteMemberBlockMemberList.indexOf(item.tidx)">해제
                                            </button>
                                            <button type="button" @click="toggleMemberBlocmMember(item.tidx)" v-else>
                                                차단
                                            </button>
                                        </div>
                                    </div>
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
    import {mapState, mapActions, mapGetters} from 'vuex'
    import SideMenu from '../../components/inc/SideMenu'

    export default {
        name: 'SetBlock',
        components: {
            SideMenu
        },
        computed: {
            ...mapGetters([
                'getMemberBlockMemberList',
                'getMemberBlockMemberTotalSize',
                'getMemberBlockMemberPage',
                'getMemberBlockMemberInfo'
            ])
        },
        data: function () {
            return {
                deleteMemberBlockMemberList: [],
                memberBlockMemberList: [],
                total: 0,
                memberBlockMemberInfo: {},
                bottom: false
            }
        },
        created: function () {
            this.clearloadMemberBlockMemberListAct()
                .then((response) => {
                    this.memberBlockMemberList = this.getMemberBlockMemberList;
                    this.total = this.getMemberBlockMemberTotalSize;
                    for (var obj in this.memberBlockMemberList) {
                        obj = this.memberBlockMemberList[obj];
                        this.memberBlockMemberInfo[obj.tidx] = this.getMemberBlockMemberInfo(obj.tidx);
                    }
                }).catch((error) => {
            });
            window.addEventListener('scroll', () => {
                this.bottom = this.bottomVisible()
            })
        },
        methods: {
            ...mapActions([
                'loadMemberBlockMemberListAct',
                'clearloadMemberBlockMemberListAct',
                'toggleMemberBlocmMemberAct'
            ]),
            getProfileImage: function (imgObj) {
                if (imgObj)
                    return imgObj.fileThumbnail || imgObj.fileName || '../../../static/img/no_image.png';
                else
                    return '../../../static/img/no_image.png';
            },
            toggleMemberBlocmMember: function (idx) {
                this.toggleMemberBlocmMemberAct(idx);
                if (this.deleteMemberBlockMemberList.indexOf(idx) == -1)
                    this.deleteMemberBlockMemberList.push(idx);
                else
                    this.deleteMemberBlockMemberList.splice(this.deleteMemberBlockMemberList.indexOf(idx), 1);
            },
            bottomVisible() {
                const scrollY = window.scrollY
                const visible = document.documentElement.clientHeight
                const pageHeight = document.documentElement.scrollHeight
                const bottomOfPage = visible + scrollY >= pageHeight
                return bottomOfPage || pageHeight < visible
            }
        },
        watch: {
            bottom(bottom) {
                if (bottom) this.loadMemberBlockMemberListAct();
            }
        }
    }
</script>

<style scoped>

</style>
