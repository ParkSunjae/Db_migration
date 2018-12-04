import global from '../../global'
import http from '../../http';

const config = global.getAxiosConfig(global.locale);

const state = {
    totalCount:0,
    followCount:0,
    followerCount:0,
    memberInfo : {},
    memberFileInfo : {},
    memberAccount : {},
    feeds : [],
}

const getters = {
	getMemberFeedFeedList : function (){return state.feeds;},
    getMemberFeedTotalCount : function(){return state.totalCount;},
    getMemberFeedFollowCount : function(){return state.followCount;},
    getMemberFeedFollowerCount : function(){return state.followerCount;},
    getMemberFeedMemberInfo : function(){return state.memberInfo;},
    getMemberFeedMemberFileInfo : function(){return state.memberFileInfo;},
    getMemberFeedMemberAccount : function(){return state.memberAccount;},
}

const mutations = {
	setMemberFeedList : function(state, payload){
	    for(let i=0; i<payload.length; i++){
            state.feeds.push(payload[i]);
        }

        state.feeds = Array.from(state.feeds.reduce((m, t) => m.set(t.idx, t), new Map()).values());
	},
	memberFeedEmptyList : function(state){
		state.feeds = [];
	},
	setMemberFeedListPush : function (state, payload){
        state.followCount = payload.followCount;
        state.followerCount = payload.followerCount;
        state.memberInfo = payload.member;
        state.memberFileInfo = payload.files;
        state.memberAccount = payload.account;
		for(obj of payload.rtns){
			state.feeds.push(obj);
		}
        state.feeds = Array.from(state.feeds.reduce((m, t) => m.set(t.idx, t), new Map()).values());
	},
	removeFromTagFeedList : function (state, payload) {
		state.feeds.slice(payload, 1);
	},
    setMemberFeedTotalCount : function (state, payload){
        state.totalCount = payload;
    }
}


const actions = {
	memberFeedEmptyListAct : function(context) {
		context.commit('memberFeedEmptyList');
	},
	searchMemberFeedList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFeed.getMemberFeed, payload, config).then((response) => {
				context.commit('setMemberFeedListPush',response.data.returnData);
                context.commit('setMemberFeedTotalCount',response.data.returnData.totalCount);
				resolve(response.data.returnData);
			}).catch((response) => {
				reject(response);
			})
		})
	},
    searchMemberFeedPopularList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFeed.getMemberFeedPopular, payload, config).then((response) => {
				context.commit('setMemberFeedListPush',response.data.returnData);
				resolve(response.data.returnData);
			}).catch((response) => {
				reject(response);
			})
		})
	},
	toggleMemberFeedLikeButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFeed.setFeedLikeToggle, payload, config).then((response) => {
				// console.log(response);
			}).catch((response) => {
			})
	},
	toggleMemberFeedScrapButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.memberFeed.setFeedScrapToggle, payload, config).then((response) => {
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
