import http from '../../http'
import global from '../../global'

let config = global.getAxiosConfig(global.locale);

const state = {
    searchHistory : [],
    popularHistory : [],
};

const getters = {
    getSearchHistory : function(state){
        return state.searchHistory;
    },
    getPopularHistory : function(state){
        return state.popularHistory;
    }
};

const mutations = {
    setSearchHistory : function (state, payload){
        state.searchHistory = payload;
    },
    setPopularHistory : function (state, payload){
        state.popularHistory = payload;
    },
};

const actions = {
    getSearchHistoryAct : function(context){
        return new Promise((resolve, reject) =>{
            return http.createAxios().post(
                        global.baseURI + global.privateUri + global.api.post.searchHistory.searchHistory,
                        {idx:context.getters.getMemberIdx},
                        config
                    )
                .then((response)=>{
                    if(response.data.code=='OK'){
                        context.commit("setSearchHistory",response.data.returnData.searchHistoryList);
                        context.commit("setPopularHistory",response.data.returnData.popularSearchList);
                    }
                }).catch((response)=>{
                    reject(response);
                })
            });
    },
    deleteSearchHistoryAct : function(context,payload){
        return http.createAxios().post(
                    global.baseURI + global.privateUri + global.api.post.searchHistory.deleteSearchHistory,
                    payload,
                    config
                )
            .then((response)=>{
                if(response.data.code=='OK'){
                    context.dispatch('getSearchHistoryAct')
                }
            }).catch((response)=>{
                reject(response);
            })
    },
    deleteAllSearchHistoryAct : function(context){
        return new Promise((resolve, reject) =>{
            return http.createAxios().post(
                        global.baseURI + global.privateUri + global.api.post.searchHistory.deleteAllSearchHistory,
                        {uidx:context.getters.getMemberIdx},
                        config
                    )
                .then((response)=>{
                    if(response.data.code=='OK'){
                        context.commit("setSearchHistory",[]);
                    }
                }).catch((response)=>{
                    reject(response);
                })
            });
    }
};

export default {
    state,
    getters,
    mutations,
    actions
}
