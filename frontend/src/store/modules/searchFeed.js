import http from '../../http'
import global from '../../global'

let config = global.getAxiosConfig(global.locale);

const state = {
    searchFeedList : [],
    searchQuery : "",
    page : 1,
    limit : 10,
};

const getters = {
    getSearchFeedList : function(state){
        return state.searchFeedList;
    },
    getSearchQuery : function(state){
        return state.searchQuery;
    },
    getSearchQueryData : function(state){
        return {
            page : state.page,
            query : state.searchQuery,
            limit : state.limit,
        }
    }
};

const mutations = {
    setSearchResult : function(state,payload){
        state.page = payload.page;
        state.total = payload.total;
        state.limit = payload.limit;
        for (variable of payload.rtns) {
            state.searchFeedList.push(variable);
        }
    },
    setSearchQuery : function (state, payload){
        state.searchQuery = payload;
        state.page = 1;
        state.limit = 10;
        state.searchFeedList.splice(0,state.searchFeedList.length);
    },
};

const actions = {
    searchFeedAct : function(context, payload){
        let data = context.getters.getSearchQueryData;
        data.idx = context.getters.getMemberIdx;
        return new Promise((resolve, reject) =>{
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.searchFeed.searchFeed, data, config)
                .then((response)=>{
                    if(response.data.code=='OK'){
                        context.commit("setSearchResult",response.data.returnData);
                        resolve(response.data.returnData);
                    }else{
                        reject(response);
                    }
                }).catch((response)=>{
                    reject(response);
                })
            });
    },
    setSearchQueryAct : function(context,payload){
        return new Promise((resolve, reject) =>{
            context.commit("setSearchQuery",payload);
            resolve(payload);
        });
    },
    initSearchFeedAct : function(context){
        context.commit('setSearchQuery');

    },

};

export default {
    state,
    getters,
    mutations,
    actions
}
