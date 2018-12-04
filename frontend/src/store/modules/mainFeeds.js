import global from '../../global'
import http from '../../http';

const config = global.getAxiosConfig(global.locale);

const state = {
	feeds : [

  ]
}

const getters = {
	getMainFeedList : function (){
		return state.feeds;
	}
}

const mutations = {
	setList : function(state, payload){
		state.feeds.concat(payload);
	},
	mainFeedEmptyList : function(state){
		state.feeds = [];
	},
	setListPush : function (state, payload){
		for(value of payload){
			state.feeds.push(value);
		}
		state.feeds = Array.from(state.feeds.reduce((m, t) => m.set(t.idx, t), new Map()).values());
	},
	setRemoveFromList : function (state, payload) {
		state.feeds.slice(payload, 1);
	}
}

const actions = {
	mainFeedEmptyListAct : function(context) {
		return new Promise((resolve, reject) => {
			context.commit('mainFeedEmptyList');
			resolve(true);
		}).catch((response) => {
			reject(response);
		})
	},
	searchMainFeedList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.mainFeed.getMainFeed, payload, config).then((response) => {
				if(response.data.returnData.rtns.length > 0){
					context.commit('setListPush',response.data.returnData.rtns);
				}
				let feedIndexList = context.getters.getMainFeedList.map(a => a.idx);
				context.dispatch('loadPostRercommendAct',feedIndexList).then((response)=>{
					context.commit('setListPush',context.getters.getNewestPostRercommend);
				}).catch((error)=>{});

				resolve(response.data.returnData);
			}).catch((response) => {
				reject(response);
			})
		})

	},
	toggleMainLikeButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.mainFeed.setFeedLikeToggle, payload, config).then((response) => {
				// console.log(response);


			}).catch((response) => {
			})
	},
	toggleMainCommentButtonAct : function (context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.mainFeed.getMainFeed, payload, config).then((response) => {
				// console.log(response);


				resolve(response);
			}).catch((response) => {
				reject(response)
			})
		})
	},
	toggleMainScrapButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.mainFeed.setFeedScrapToggle, payload, config).then((response) => {
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
