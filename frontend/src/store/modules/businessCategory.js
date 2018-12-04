import global from '../../global'
import http from '../..//http';

var config = global.getAxiosConfig(global.locale);

const state = {
  businessCategory1List : [],
  isloaded : 0,
  businessCategory2List : {},
}

const getters = {
  getBusinessCategory1List : function(){ return state.businessCategory1List; },
  getBusinessCategory2List : function(){ return state.businessCategory2List; }
}

const mutations = {
  loadBusinessCategory1List : function(state, payload){
    state.businessCategory1List = payload.businessCategory1List;
  },
  loadBusinessCategory2List : function(state, payload){
    state.businessCategory2List[payload.parentIdx] = payload.businessCategory2List
  }
}

const actions = {
  loadBusinessCategory1ListAct : function(context, payload){
    return new Promise((resolve, reject) => {
      if(state.isloaded==0){
		    return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.businessCategory1.getBusinessCategory1, {}, config).then((response) => {
          if(response.data.code=='OK'){
            context.commit("loadBusinessCategory1List",response.data.returnData);
            resolve(true);
          }
  		  }).catch(response => {
  				reject(response);
  			});
      }else
        resolve(true);
		});
  },
  loadBusinessCategory2ListAct : function(context, payload){
    return new Promise((resolve, reject) => {
      if(typeof state.businessCategory2List[payload.parentIdx] == 'undefined'){
        return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.businessCategory2.getBusinessCategory2, payload, config)
        .then((response) => {
          if(response.data.code=='OK'){
            response.data.returnData.parentIdx=payload.parentIdx;
            context.commit("loadBusinessCategory2List",response.data.returnData);
            resolve(true);
          }
        }).catch(response => {
          reject(response);
        });
      }else
        resolve(true);
    });
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
