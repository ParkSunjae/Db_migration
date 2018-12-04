import global from '../../global'
import http from '../../http'

const config = global.getAxiosConfig(global.locale);

const state = {
	feed : {
  	    contents : '',
        member : {
  	    	nickName : ''
        },
        feedLocations : {
  	    	title : ''
        },
        link1 : '',
        files : []
    }
}

const getters = {
	getFeedData : function () {
		return state.feed;
	},
}

const mutations = {
	setFeedData : function(state, payload) {
		state.feed = payload
	},
	setCallIdx : function(state, payload) {
		state.callIdx = payload;
	}
}

const actions = {
	setCallFeedIdx : function (context, payload) {
		context.commit('setCallIdx', payload);
	},
	getFeedCall : function (context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.getFeed, payload, config).then((response) => {
				context.commit("setFeedData",response.data.returnData);
				resolve(response.data.returnData);
			}).catch((reponse) => {
				reject(response);
			})
		})
	},
	callCommentOrderByLikeCount : function (context, payload){
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.getFeedCommentLike, payload, config).then((response) => {
				resolve(response.data.returnData);
			}).catch((reponse) => {
				reject(response);
			})
		})
	},
	callCommentOrderByNews : function (context, payload){
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.getFeedCommentNew, payload, config).then((response) => {
				resolve(response.data.returnData);
			}).catch((reponse) => {
				reject(response);
			})
		})
	},
	addComment : function(context, payload){
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.addFeedComment, payload, config).then((response) => {
				resolve(response.data.returnData);
			}).catch((reponse) => {
				reject(response);
			})
		})
	},
	toggleCommentLikeButtonAct : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.commentLikeToggle, payload, config).then((response) => {
				resolve(response.data.returnData);
			}).catch((reponse) => {
				reject(response);
			})
		})
	},
	deleteCommentAct : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.removeComment, payload, config).then((response) => {
				resolve(response.data.returnData);
			}).catch((reponse) => {
				reject(response);
			})
		})
	},
	updateCommentAct : function(context, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.updateComment, payload, config).then((response) => {
				resolve(response.data.returnData);
			}).catch((reponse) => {
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
