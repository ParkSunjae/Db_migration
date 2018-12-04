import global from '../../global'
import http from '../../http';

const config = global.getAxiosConfig(global.locale);

const state = {
    tag:"",
    tagIdx:0,
    totalCount:0,
    followCount:0,
    feeds : [],
}

const getters = {
	getTagFeedList : function (){return state.feeds;},
    getTagFeedTag : function(){return state.tag;},
    getTagFeedTagIdx : function(){return state.tagIdx;},
    getTagTotalCount : function(){return state.totalCount;},
    getTagFollowCount : function(){return state.followCount;},
}

const mutations = {
	setTagFeedList : function(state, payload){
		state.feeds.concat(payload);
	},
	tagFeedEmptyList : function(state){
		state.feeds = [];
	},
	setTagFeedListPush : function (state, payload){
        state.tagIdx = payload.tagsInfo.idx;
        state.tag = payload.tagsInfo.tag;
        state.followCount = payload.followCount;
        state.totalCount = payload.totalCount;
		for(let i=0; i<payload.rtns.length; i++){
			state.feeds.push(payload.rtns[i]);
		}
        state.feeds = Array.from(state.feeds.reduce((m, t) => m.set(t.idx, t), new Map()).values());
	},
	removeFromTagFeedList : function (state, payload) {
		state.feeds.slice(payload, 1);
	},
}


const actions = {
	tagFeedEmptyListAct : function(context) {
		context.commit('tagFeedEmptyList');
	},
	searchTagFeedList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tagFeed.getTagFeed, payload, config).then((response) => {
				if(response.data.returnData.rtns.length > 0){
					context.commit('setTagFeedListPush',response.data.returnData);
					resolve(response.data.returnData);
				}
			}).catch((response) => {
				reject(response);
			})
		})
	},
    searchTagFeedPopularList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tagFeed.getTagFeedPopular, payload, config).then((response) => {
				if(response.data.returnData.rtns.length > 0){
					context.commit('setTagFeedListPush',response.data.returnData);
					resolve(response.data.returnData);
				}
			}).catch((response) => {
				reject(response);
			})
		})
	},
	toggleTagFeedLikeButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tagFeed.setFeedLikeToggle, payload, config).then((response) => {
				// console.log(response);
			}).catch((response) => {
			})
	},
	toggleTagFeedScrapButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tagFeed.setFeedScrapToggle, payload, config).then((response) => {
				// console.log(response);
			}).catch((response) => {
			})
	}
}


export default {
	state,
	getters,
	mutations,
	actions
}
