import global from '../../global'
import http from '../../http';

var config = global.getAxiosConfig(global.locale);

const state = {
  noticeList : [],
  total : 0,
  isLoadedPage : 0
}

const getters = {
  getNoticeList : function(){ return state.noticeList; },
  getNoticeTotal : function(){ return state.total; },
  getNoticeIsLoadedPage : function(){ return state.isLoadedPage; },
}

const mutations = {
  loadNoticeList : function(state, payload){
    state.noticeList = payload.noticeList
    state.total = payload.total;
    state.isLoadedPage = payload.page;
  },
  clearNoticeList : function(state,payload){
    state.isLoadedPage = 0;
    state.total = 0;
    state.noticeList = {};
  }
}

const actions = {
    loadNoticeListAct : function(context){
      return new Promise((resolve, reject) => {
        let payload = {};
        payload.page=state.isLoadedPage+1;
        payload.useYn='Y';
  	    return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.notice.getNoticeByUseYn,payload, config).then((response) => {
          if(response.data.code=='OK'){
            if(state.isLoadedPage < response.data.returnData.page){
              context.commit("loadNoticeList",response.data.returnData);
              resolve(true);
            }else{
              resolve(false);
            }
          }
  		  }).catch(response => {
  				reject(response);
  			});
  		});
    },
    clearNoticeListAct : function(context){
      return new Promise((resolve, reject) =>{
        context.commit("clearNoticeList");
        context.dispatch("loadNoticeListAct")
        .then((response)=>{
          resolve(true);
        }).catch((error)=>{
          reject(error);
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
