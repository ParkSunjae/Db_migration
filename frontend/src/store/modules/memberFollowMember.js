import global from '../../global'
import http from '../..//http';

var config = global.getAxiosConfig(global.locale);

const state = {
    checkedFollowMemberList : []
}

const getters = {
    checkedFollowMember : (state)=> (idx) => {
        return state.checkedFollowMemberList.filter(member => member.tidx==idx);
    },
    getCheckedFollowMemberList: function(state){
        return state.checkedFollowMemberList;
    }
}

const mutations = {
    checkFollowMember : function(state,payload){
        state.checkedFollowMemberList.push(payload);
    },
    deleteFollowMember : function(state,payload){
        state.checkedFollowMemberList = state.checkedFollowMemberList.filter(member => ((typeof member !== 'undefined') && (member.tidx!=payload)));
    },
}

const actions = {
    toggleFollowMemberAct: function(context, payload) {
        context.commit("deleteFollowMember",payload);
        payload = {
            oidx: context.getters.getMemberIdx,
            tidx: payload
        };
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFollowMember.toggleFollowMember, payload, config)
                .then((response) => {
                    if (response.data.code == 'OK') {
                        context.dispatch("checkFollowMemberAct",payload.tidx);
                        context.commit("setMemberInfoLoadedVal",0);
                        context.dispatch("loadMemberInfoAct").then((response) => {
                            resolve(true);
                        }).catch((response) => {
                            reject(response);
                        });
                    }
                }).catch((error) => {
                    reject(response);
                });
        });
    },
    checkFollowMemberAct : function(context, payload){
        return new Promise((resolve, reject) => {

            if(context.getters.checkedFollowMember(payload).length == 0){
                payload = {
                    oidx: context.getters.getMemberIdx,
                    tidx: payload
                };
                return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFollowMember.checkFollowMember, payload, config)
                    .then((response) => {
                        if (response.data.code == 'OK') {
                            if(response.data.returnData != false){
                                context.commit("checkFollowMember",response.data.returnData);
                            }
                        }
                        resolve(true);
                    }).catch((response) => {
                        reject(response);
                    });
            }else{
                resolve(true);
            }
        });

    },

    // feedMemberTag 쪽으로 이전 필요
    findFollowers: function(context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFollowMember.findFollowers, payload, config).then((response) => {
                resolve(response.data);
            }).catch(response => {
                reject(response);
            });
        });
    },
    saveFollowers: function(context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFollowMember.saveFollowers, payload, config).then((response) => {
                console.log("response", response);
                resolve(response.data);
            }).catch((response) => {
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
