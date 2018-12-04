import global from '../../global'
import http from '../../http';

var config = global.getAxiosConfig(global.locale);

const state = {
  postRercommend:[],
  userRecommendPC:[],
  userRecommendMobile:[],
  tagRecommendPC:[],
  tagRecommendMobile:[],
}

const getters = {
  getNewestPostRercommend : function(){
    return state.postRercommend.slice(-1)[0];
  },
  getPostRercommend:function(){
    return state.postRercommend;
  },
  getNewestUserRecommendPC: function(){
    return state.userRecommendPC.slice(-1)[0];
  },
  getUserRecommendPC:function(){
    return state.userRecommendPC;
  },
  getNewestUserRecommendMobile: function(){
    return state.userRecommendMobile.slice(-1)[0];
  },
  getUserRecommendMobile:function(){
    return state.userRecommendMobile;
  },
  getNewestTagRecommendPC: function(){
    return state.tagRecommendPC.slice(-1)[0];
  },
  getTagRecommendPC:function(){
    return state.tagRecommendPC;
  },
  getNewestTagRecommendMobile: function(){
    return state.tagRecommendMobile.slice(-1)[0];
  },
  getTagRecommendMobile:function(){
    return state.tagRecommendMobile;
  },
}

const mutations = {
  loadPostRercommend:function(state,payload){
    state.postRercommend.push(payload.feedsList);
  },
  loadUserRecommendPC:function(state,payload){
    state.userRecommendPC.push(payload.memberInfo);
  },
  loadUserRecommendMobile:function(state,payload){
    state.userRecommendMobile.push(payload.memberInfo);
  },
  loadTagRecommendPC:function(state,payload){
    state.tagRecommendPC.push(payload.tagsList);
  },
  loadTagRecommendMobile:function(state,payload){
    state.tagRecommendMobile.push(payload.tagsList);
  },
}

const actions = {
  loadPostRercommendAct:function(context,payload){
    payload = {
      userId:context.getters.getMemberIdx,
      delivered:payload
    }
    return new Promise((resolve, reject) =>{
      return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.personalRecommend.postRecommend,payload, config)
        .then((response) => {
          context.commit("loadPostRercommend",response.data.returnData);
          resolve(true);
        }).catch(response => {
          reject(response);
        });
    });
  },
  loadUserRecommendPCAct:function(context){
    let payload = {
      userId:context.getters.getMemberIdx,
    }
    return new Promise((resolve, reject) =>{
      return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.personalRecommend.userRecommendPc,payload, config)
        .then((response) => {
          context.commit("loadUserRecommendPC",response.data.returnData);
          resolve(true);
        }).catch(response => {
          reject(response);
        });
    });
  },
  loadUserRecommendMobileAct:function(context){
    let payload = {
      userId:context.getters.getMemberIdx,
    }
    return new Promise((resolve, reject) =>{
      return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.personalRecommend.userRecommendMobile,payload, config)
        .then((response) => {
          context.commit("loadUserRecommendMobile",response.data.returnData);
          resolve(true);
        }).catch(response => {
          reject(response);
        });
    });
  },
  loadTagRecommendPCAct:function(context){
    let payload = {
      userId:context.getters.getMemberIdx,
    }
    return new Promise((resolve, reject) =>{
      return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.personalRecommend.tagRecommendPc,payload, config)
        .then((response) => {
          context.commit("loadTagRecommendPC",response.data.returnData);
          resolve(true);
        }).catch(response => {
          reject(response);
        });
    });
  },
  loadTagRecommendMobileAct:function(context){
    let payload = {
      userId:context.getters.getMemberIdx,
    }
    return new Promise((resolve, reject) =>{
      return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.personalRecommend.tagRecommendMobile,payload, config)
        .then((response) => {
          context.commit("loadTagRecommendMobile",response.data.returnData);
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
