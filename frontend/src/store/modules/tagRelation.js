import global from '../../global'
import http from '../../http';

var config = global.getAxiosConfig(global.locale);

const state = {
    relationTag : [],
    relationUser : []
}
const getters = {
    getRelationTag : function(){
        return state.relationTag;
    },
    getRelationUser : function(){
        return state.relationUse;
    },
}

const mutations = {
    setRelationTag : function(state,payload){
        state.relationTag = payload;
    },
    setRelationUser : function(state,payload){
        state.relationUse = payload;
    },
}

const actions = {
    setRelationTagAct : function(context,payload){
        return new Promise((resolve, reject) =>{
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tagRelation.getTagRelationTag,payload, config)
                .then((response) => {
                  context.commit("setRelationTag",response.data.returnData.tagsList);
                  resolve(true);
                }).catch(response => {
                  reject(response);
                });
        });
    },
    setRelationUserAct : function(context,payload){
        return new Promise((resolve, reject) =>{
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tagRelation.getTagRelationUser,payload, config)
                .then((response) => {
                  context.commit("setRelationUser",response.data.returnData.memberInfo);
                  resolve(true);
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
