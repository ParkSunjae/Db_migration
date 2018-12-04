import global from '../../global'
import router from '../../router'
import http from '../..//http';

var config = global.getAxiosConfig(global.locale);
var configForm = global.getAxiosConfig(global.locale,'form');

const b64DecodeUnicode = function b64DecodeUnicode(str) {
    return decodeURIComponent(Array.prototype.map.call(atob(str), function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
    }).join(''))
};

const parseToken = function (accessToken) {
    // console.log('accessToken:', accessToken);
    if (accessToken) {
        try {
            let base64Url = accessToken.split('.')[1];
            let base64 = base64Url.replace('-', '+').replace('_', '/');
            //console.log('base64', base64);
            //console.log(b64DecodeUnicode(base64));
            return JSON.parse(b64DecodeUnicode(base64));
        } catch (e) {
            console.log('accessToken parsing error: ', e);
            console.log('accessToken: ', accessToken);
            return undefined;
        }

    } else {
        return undefined;
    }
};

const state = {
    accessToken: '',
    //회원가입용 정보.
    tokenInfo: parseToken(localStorage.getItem('accessToken')),
    member: {
        idx: 0,
        fileIdx: 0,
        year: "",
        content: "",
        nickName: "",
        businessYn: "",
        businessName: "",
        businessContent: "",
        businessCategoryIdx: "",
        businessSubCategoryIdx: 0,
        businessEmail: "",
        businessTel: "",
        businessSite: "",
        businessSdate: "",
        businessEdate: "",
        emailCertYn: "N",
        emailCertDate: "",
        createAt: "",
        updateAt: ""
    },
    feedCount: 0,
    followingCount: 0,
    followerCount: 0,
    loginAccount : {
        idx: 0,
        accountType: "",
        deviceType: "",
        email: "",
        pushToken: "",
        pushYn: "N",
        snsToken: "",
        snsType: "",
        userIdx: 0,
        userPwd: "",
        createAt: "",
        updateAt: ""
    },
    emailAccount: {
        idx: 0,
        email: "",
        deviceType:""
    },
    files: {
        idx: 0,
        fileName: "",
        fileThumbnail: "",
        fileType: "",
        createAt: "",
        updateAt: ""
    },
    isloaded: 0,
    lastLogin : {},
    locationText : ''
}

const getters = {
    // Member
    getMemberIdx: function () {
        return state.member.idx;
    },
    getMemberFileIdx: function () {
        return state.member.fileIdx;
    },
    getMemberYear: function () {
        return state.member.year;
    },
    getMemberContent: function () {
        return state.member.content;
    },
    getMemberNickName: function () {
        return state.member.nickName;
    },
    getMemberBusinessYn: function () {
        return state.member.businessYn;
    },
    getMemberBusinessName: function () {
        return state.member.businessName;
    },
    getMemberBusinessContent: function () {
        return state.member.businessContent;
    },
    getMemberBusinessCategoryIdx: function () {
        return state.member.businessCategoryIdx;
    },
    getMemberBusinessSubCategoryIdx: function () {
        return state.member.businessSubCategoryIdx;
    },
    getMemberBusinessEmail: function () {
        return state.member.businessEmail;
    },
    getMemberBusinessTel: function () {
        return state.member.businessTel;
    },
    getMemberBusinessSite: function () {
        return state.member.businessSite;
    },
    getMemberBusinessSdate: function () {
        return state.member.businessSdate;
    },
    getMemberBusinessEdate: function () {
        return state.member.businessEdate;
    },
    getMemberCreateAt: function () {
        return state.member.createAt;
    },
    getMemberUpdateAt: function () {
        return state.member.updateAt;
    },
    getEmailCertYn: function () {
        return state.member.emailCertYn;
    },


    // Account
    getEmailAccoutDeviceType: function () {
        return state.emailAccount.deviceType;
    },
    getEmailAccountDeviceCheck : function () {
        if(state.emailAccount.deviceType == "WEB"){
            return true;
        }else{
            return false;
        }
    },
    getEmailAccountIdx: function () {
        return state.emailAccount.idx;
    },
    getEmailAccountEmail: function () {
        return state.emailAccount.email;
    },

    // Files
    getFilesIdx: function () {
        return state.files.idx;
    },
    getFilesFileName: function () {
        return state.files.fileName;
    },
    getFilesFileThumbnail: function () {
        return state.files.fileThumbnail;
    },
    getFilesFileType: function () {
        return state.files.fileType;
    },
    getFilesCreateAt: function () {
        return state.files.createAt;
    },
    getFilesUpdateAt: function () {
        return state.files.updateAt;
    },

    getFeedCount: function () {
        return state.feedCount;
    },
    getFollowingCount: function () {
        return state.followingCount;
    },
    getFollowerCount: function () {
        return state.followerCount;
    },

    //LoginAccount
    getEmail: function () {
        return state.loginAccount.email;
    },
    getPassword: function () {
        return state.loginAccount.userPwd;
    },
    getPushToken: function () {
        return state.loginAccount.pushToken;
    },
    getPushYn: function () {
        return state.loginAccount.pushYn;
    },
    getAccountIdx: function () {
        return state.loginAccount.idx;
    },
    getAccountType: function () {
        return state.loginAccount.accountType;
    },
    getSnsToken: function () {
        return state.loginAccount.snsToken;
    },
    getSnsType: function () {
        return state.loginAccount.snsType;
    },
    getLastLogin : function () {
        return state.lastLogin;
    },
    getLocationText : function () {
        return state.locationText;
    }
}

const mutations = {
    initStates : function (state) {
        state.member = {
            idx: 0,
                fileIdx: 0,
                year: "",
                content: "",
                nickName: "",
                businessYn: "",
                businessName: "",
                businessContent: "",
                businessCategoryIdx: "",
                businessSubCategoryIdx: 0,
                businessEmail: "",
                businessTel: "",
                businessSite: "",
                businessSdate: "",
                businessEdate: "",
                emailCertYn: "N",
                emailCertDate: "",
                createAt: "",
                updateAt: ""
        };
        state.feedCount =  0;
        state.followingCount = 0,
            state.followerCount = 0,
            state.loginAccount = {
            idx: 0,
                accountType: "",
                deviceType: "",
                email: "",
                pushToken: "",
                pushYn: "N",
                snsToken: "",
                snsType: "",
                userIdx: 0,
                userPwd: "",
                createAt: "",
                updateAt: ""
        };
        state.emailAccount = {
            idx: 0,
                email: "",
                deviceType:""
        };
        state.files = {
            idx: 0,
                fileName: "",
                fileThumbnail: "",
                fileType: "",
                createAt: "",
                updateAt: ""
        };
        state.isloaded = 0;
        state.lastLogin = '';
        state.locationText = '';
    },
    loadMemberInfo: function (state, payload) {
        state.member.idx = payload.member.idx;
        state.member.year = payload.member.year;
        state.member.content = payload.member.content;
        state.member.nickName = payload.member.nickName;
        state.member.businessYn = payload.member.businessYn;
        state.member.businessName = payload.member.businessName;
        state.member.businessContent = payload.member.businessContent;
        state.member.businessCategoryIdx = payload.member.businessCategoryIdx;
        state.member.businessSubCategoryIdx = payload.member.businessSubCategoryIdx;
        state.member.businessEmail = payload.member.businessEmail;
        state.member.businessTel = payload.member.businessTel;
        state.member.businessSite = payload.member.businessSite;
        state.member.businessSdate = payload.member.businessSdate;
        state.member.businessEdate = payload.member.businessEdate;
        state.member.emailCertYn = payload.member.emailCertYn;
        state.member.createAt = payload.member.createAt;
        state.member.updateAt = payload.member.updateAt;
        state.feedCount = payload.feedCount;
        state.followingCount = payload.followingCount;
        state.followerCount = payload.followerCount;
        state.emailAccount.email = payload.account.email;
        state.locationText = payload.locationText;
        if (typeof payload.files === "object" && payload.files != null) {
            state.files.idx = payload.files.idx;
            state.files.fileName = payload.files.fileName;
            state.files.fileThumbnail = payload.files.fileThumbnail;
            state.files.fileType = payload.files.fileType;
            state.files.createAt = payload.files.createAt;
            state.files.updateAt = payload.files.updateAt;
        } else {
            state.files.idx = "";
            state.files.fileName = "";
            state.files.fileThumbnail = "";
            state.files.fileType = "";
            state.files.createAt = "";
            state.files.updateAt = "";
        }
        state.isloaded = 1;
    },
    setMemberInfoLoadedVal: function (state, payload) {
        state.isloaded = payload;
    },
    setWebDeviceInfo(state, payload) {
        state.emailAccount.deviceType = payload;
    },
    login(state, {accessToken, deviceType, accountType, midx, aidx, lastLogin}) {
        // console.log("loginIdx ===",midx);
        state.accessToken = accessToken;
        state.loginAccount.idx = aidx;
        state.loginAccount.userIdx = midx;
        state.member.idx = midx;
        state.loginAccount.deviceType = deviceType;
        state.lastLogin = lastLogin;
        localStorage.accessToken = accessToken;
        localStorage.setItem('accessToken', accessToken);
        state.tokenInfo = parseToken(accessToken);
    },
    logout(state) {
        state.accessToken = '';
        delete localStorage.accessToken;
        state.tokenInfo = global.domain.tokenInfo;
        // 모든 HTTP 요청헤더에 Authorization을 추가
        //http.createAxios().defaults.headers.common['Authorization'] = undefined;
        router.push({name: 'Intro'});
    },
    setTokenUserIdx: function (state, payload) {
        state.tokenInfo.idx = payload;
    },
}

const actions = {
    initUserState : function (context){
        context.commit('initStates');
    },
    setDeviceInfo : function (context, payload) {
        console.log("store payload", payload);
        context.commit('setWebDeviceInfo', payload);
    },
    loadMemberInfoAct: function (context, payload) {
        return new Promise((resolve, reject) => {
            if (state.isloaded == 0)
                return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.member.loadMemberData, {
                    idx: context.getters.getMemberIdx
                }, config).then((response) => {
                    if (response.data.code == 'OK') {
                        context.commit("loadMemberInfo", response.data.returnData);
                        resolve(true);
                    }
                }).catch(response => {
                    reject(response);
                });
            else
                resolve(true);
        });
    },
    updateMemberInfoAct: function (context, payload) {
        const form = new FormData();
        form.append('idx', payload.idx);
        if (typeof payload.nickName === 'string') {
            form.append('nickName', payload.nickName);
            if(payload.fileIdx){
                form.append('fileIdx', payload.fileIdx);
            }
            form.append('content', payload.content);
            form.append('defaultImage', payload.defaultImage);
            if (!payload.defaultImage) {
                if(payload.profileImage)
                    form.append('profileImage', payload.profileImage);
            }
        }
        if (payload.businessYn) {
            form.append('businessName', payload.businessName);
            form.append('businessEmail', payload.businessEmail);
            form.append('businessTel', payload.businessTel);
            form.append('businessSite', payload.businessSite);
            form.append('businessCategoryIdx', payload.businessCategoryIdx);
            form.append('businessSubCategoryIdx', payload.businessSubCategoryIdx);
            form.append('businessYn', payload.businessYn);
        }

        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.member.updateMemberData, form, configForm).then((response) => {
                if (response.data.code == 'OK') {
                    state.isloaded = 0;
                    context.dispatch("loadMemberInfoAct").then((response) => {
                        resolve(true);
                    }).catch((response) => {
                        reject(response);
                    });
                }
            }).catch(response => {
                reject(response);
            });
        });
    },
}

export default {
    state,
    getters,
    actions,
    mutations
}
