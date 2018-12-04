import http from '../../http'
import global from '../../global'

let config = global.getAxiosConfig(global.locale);

const state = {

};

const getters = {

};

const mutations = {

};

const actions = {
    getAccountDataAct : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.account.getAccount, payload, config).then((response)=>{
                console.log(response.data);
                resolve(response.data);
            }).catch((response)=>{
                reject(response);
            })
        })
    },
    callCertMail : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.member.certMail, payload, config).then((response)=>{
                console.log(response.data);
                resolve(response.data);
            }).catch((response)=>{
                reject(response);
            })
        })
    },
    sendTempPassword : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.publicUri + global.api.post.member.tempPwdSend, payload, config).then((response)=>{
                console.log(response.data);
                resolve(response.data);
            }).catch((response)=>{
                reject(response);
            })
        })
    },
    checkUserMailAct : function (context, payload){
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.publicUri + global.api.post.member.checkUserMail, payload, config).then((response)=>{
                console.log(response.data);
                resolve(response.data);
            }).catch((response)=>{
                reject(response);
            })
        })
    },
    checkPwdAndChange : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.account.checkPassword, payload, config).then((response)=>{
                console.log(response.data);
                resolve(response.data);
            }).catch((response)=>{
                reject(response);
            })
        })
    }
};

export default {
    state,
    getters,
    mutations,
    actions
}
