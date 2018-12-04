import global from '../../global'
import http from '../../http';

const config = global.getAxiosConfig(global.locale);

const state = {
	feeds : [
		// {
		// 	idx: 0,
		// 	uidx: 0,
		// 	contents: '',
		// 	hits: 0,
		// 	likes: 0,
		// 	scrap: 0,
		// 	link1: '',
		// 	link2: '',
		// 	feedStatus: '',
		// 	tagLocation: '',
		// 	tagAction: '',
		//
		// 	feedLocations : {},
		// 	comments : [],
		// 	files : [],
		// 	likers : [],
		// 	feedTags : [],
		// 	atTags : [],
		// 	actionTags : [],
		// 	books : [],
		// 	movies : [],
		// 	culture : [],
		// 	scraps : [],
		// 	feedMemberTag : [],
		// 	member : {},
		// 	account : {},
		// 	createAt : '',
		// 	updateAt : '',
		// 	meLikeCheck : false,
		// 	meCommentCheck : false,
		// 	meScrapCheck : false,
		// 	likeCount : 0,
		// 	commentCount : 0,
		// 	scrapCount : 0
		// }
	]
}

const getters = {
	getMyFeedList : function (){
		return state.feeds;
	}
}

const mutations = {
	setList : function(state, payload){
		state.feeds.concat(payload);
	},
	setEmptyList : function(state){
		state.feeds = [];
	},
	setListPush : function (state, payload){
		for(let i=0; i<payload.length; i++){
			state.feeds.push(payload[i]);
		}
	},
	setRemoveFromList : function (state, payload) {
		state.feeds.slice(payload, 1);
	}
}

const actions = {
	emptyList : function(context) {
		context.commit('setEmptyList');
	},
	searchMyFeedList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.myFeed.getMyFeed, payload, config).then((response) => {
				if(response.data.returnData.rtns.length > 0){
					context.commit('setListPush',response.data.returnData.rtns);
					resolve(response.data.returnData);
				}
			}).catch((response) => {
				reject(response);
			})
		})

	},
	searchToGetherList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.myFeed.getToGetter, payload, config).then((response) => {
				if(response.data.returnData.rtns.length > 0){
					context.commit('setListPush',response.data.returnData.rtns);
					resolve(response.data.returnData);
				}
			}).catch((response) => {
				reject(response);
			})
		})

	},
	searchMyScrapList : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.myFeed.getMyScrap, payload, config).then((response) => {
				if(response.data.returnData.rtns.length > 0){
					context.commit('setListPush',response.data.returnData.rtns);
					resolve(response.data.returnData);
				}
			}).catch((response) => {
				reject(response);
			})
		})
	},
	toggleLikeButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.myFeed.setFeedLikeToggle, payload, config).then((response) => {
				// console.log(response);


			}).catch((response) => {
			})
	},
	toggleCommentButtonAct : function (context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.myFeed.getMyFeed, payload, config).then((response) => {
				// console.log(response);


				resolve(response);
			}).catch((response) => {
				reject(response)
			})
		})
	},
	toggleScrapButtonAct : function (context, payload) {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.myFeed.setFeedScrapToggle, payload, config).then((response) => {
				// console.log(response);
			}).catch((response) => {
			})
	},
    deleteFeedAct : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.myFeed.deleteFeeds, payload, config).then((response) => {
                // console.log(response);
                resolve(response);
            }).catch((response) => {
                reject(response);
            })
        })
    }
}

export default {
	state,
	getters,
	mutations,
	actions
}
