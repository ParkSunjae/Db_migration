import global from '../../global'
import http from '../../http';

var config = global.getAxiosConfig(global.locale);

const state = {
  helpList : [],
  total : 0,
  isLoadedPage : 0
}

const getters = {
  getHelpList : function(){ return state.helpList; },
  getHelpTotal : function(){ return state.total; },
  getHelpIsLoadedPage : function(){ return state.isLoadedPage; },
}

const mutations = {
  loadHelpList : function(state, payload){
    state.helpList = payload.helpList
    state.total = payload.total;
    state.isLoadedPage = payload.page;
  },
  clearHelpList : function(state,payload){
    state.isLoadedPage = 0;
    state.total = 0;
    state.helpList = {};
  }
}

const actions = {
    loadHelpListAct : function(context){
      return new Promise((resolve, reject) => {
        let payload = {};
        payload.page=state.isLoadedPage+1;
        payload.useYn='Y';
  	    return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.help.getHelpByUseYn,payload, config).then((response) => {
          if(response.data.code=='OK'){
            if(state.isLoadedPage < response.data.returnData.page){
              context.commit("loadHelpList",response.data.returnData);
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
    clearHelpListAct : function(context){
      return new Promise((resolve, reject) =>{
        context.commit("clearHelpList");
        context.dispatch("loadHelpListAct")
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
