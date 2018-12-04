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
    getActivityList : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberActivitiy.getActionList, payload, config).then((response)=>{
                resolve(response.data);
            }).catch((response)=>{
                reject(response);
            })
        })
    },
    getLoginLogList : function(context, payload){
        return new Promise((resolve, reject) =>{
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberActivitiy.getLoginLogList, payload, config).then((response)=>{
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
