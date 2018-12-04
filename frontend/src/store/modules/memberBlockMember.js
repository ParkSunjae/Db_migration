import global from '../../global'
import http from '../..//http';

var config = global.getAxiosConfig(global.locale);

const state = {
  memberBlockMemberList : {},
  memberInfoList : {},
  total : 0,
  isLoadedPage : 0
}

const getters = {
  getMemberBlockMemberList : function(){ return state.memberBlockMemberList; },
  getMemberBlockMemberPage : function(){ return state.isLoadedPage; },
  getMemberBlockMemberTotalSize : function(){ return state.total; },
  getMemberBlockMemberInfo : (state)=> (idx) => { return state.memberInfoList[idx]; }
}

const mutations = {
  loadMemberBlockMemberList : function(state, payload){
    if(state.isLoadedPage < payload.page){
      for (var key in payload.memberBlockMemberList) {
        state.memberBlockMemberList[payload.memberBlockMemberList[key]['tidx']] = payload.memberBlockMemberList[key];
      }
      state.total = payload.total;
      for (var obj in payload.memberInfoList) {
        obj = payload.memberInfoList[obj];
        state.memberInfoList[obj.idx] = obj;
      }
      state.isLoadedPage = payload.page;
    }
  },
  clearMemberBlockMemberList : function(state,payload){
    state.isLoadedPage = 0;
    state.total = 0;
    state.memberBlockMemberList = {};
  }
}

const actions = {
  loadMemberBlockMemberListAct : function(context){
    return new Promise((resolve, reject) => {
      let payload = {};
      payload.page=state.isLoadedPage+1;
      payload.originIdx = context.getters.getMemberIdx;
	    return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberBlockMember.findBlockMembers,payload, config).then((response) => {
        if(response.data.code=='OK'){
          context.commit("loadMemberBlockMemberList",response.data.returnData);
          resolve(true);
        }
		  }).catch(response => {
				reject(response);
			});
		});
  },
  clearloadMemberBlockMemberListAct : function(context){
    return new Promise((resolve, reject) =>{
      context.commit("clearMemberBlockMemberList");
      context.dispatch("loadMemberBlockMemberListAct")
      .then((response)=>{
        resolve(true);
      }).catch((error)=>{
        reject(error);
      });
    });
  },
  toggleMemberBlocmMemberAct : function(context,payload){
    payload = {'oidx':context.getters.getMemberIdx,'tidx':payload};
    return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberBlockMember.toggleMemberBlockMember,payload, config)
      .then((response) => {

      }).catch(response => {
        reject(response);
      });
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
