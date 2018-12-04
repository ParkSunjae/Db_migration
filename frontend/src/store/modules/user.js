import global from '../../global'
import http from '../..//http';

var config = global.getAxiosConfig(global.locale);

const state = {
};

const getters = {
};


const mutations = {
};

const actions = {
    login({commit}, {email, password, deviceType}) {
        return http.createAxios().post(global.baseURI + global.api.post.member.signin, {
            'email': email,
            'password': password,
            'deviceType': deviceType
        }, config)
            .then(({datas}) => {
                // console.log(datas);
                commit('login', datas);
            }).catch((response) => {
            });
    },
    logout: function(context, payload) {
        const {accessToken} = localStorage;
        return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.member.signOut,{accessToken} , config)
            .then((datas) => {
                console.log(datas);
                context.commit('initStates');
                context.commit('logout');
            }).catch((response) => {
            });
    },
    exitAfterLogout : function (context, payload){
        context.commit('logout');
    },
    memberExitAct: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.member.memberExit, payload, config).then((response) => {

                resolve(response.data);
            }).catch(response => {
                console.log(response);
                reject(error.response)
            });
        });
    },
    apiJoinUserAct: function (context, payload) {
        return new Promise((resolve, reject) => {
            http.createAxios().post(global.baseURI + global.api.post.member.signUp, payload, config)
                .then(response => {
                    console.log("response:", response.data);
                    if (response.data.code == "OK") {
                        context.commit('login', response.data.returnData);
                    } else {
                        //오류시
                        context.commit("setErrorCode", response.data);
                    }
                    resolve(response.data);
                })
                .catch(error => {
                    console.log(error.response);
                    reject(error.response)
                });
        });
    },
    checkUserNickName: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.publicUri + global.api.post.member.checkNickName, payload, config).then((response) => {
                resolve(response.data);
            }).catch(response => {
                reject(response);
            });
        });
    },
    checkUserEmail: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.publicUri + global.api.post.member.checkDuplicateEmail, payload, config).then((response) => {
                resolve(response.data);
            }).catch(response => {
                reject(response);
            });
        });
    },
    saveUserYear: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.member.saveYear, payload, config).then((response) => {
                resolve(response.data);
            }).catch(response => {
                reject(response);
            });
        });
    }
};


export default {
    state,
    getters,
    actions,
    mutations
}
