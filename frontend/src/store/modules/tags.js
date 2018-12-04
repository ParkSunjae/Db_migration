import http from '../../http'
import global from '../../global'

const config = global.getAxiosConfig(global.locale);

const state = {
	tags : [],
	savedTags : [],
	users : []
}

const getters = {
	getTags : function() {
		return state.tags;
	},
	getSavedTags : function() {
		return state.savedTags;
	},
	getSuggestUsers : function () {
		return state.users;
	}
}

const mutations = {
	setTags : function(state, payload) {
		state.tags = payload;
	},
	setSavedTags : function(state, payload) {
		state.savedTags = payload;
	},
	setSuggestUsers : function (state, payload){
		state.users = payload;
	}
}

const actions = {
	setTagsAct : function(context, payload){
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tags.callTags, payload, config).then((respone) => {
				console.log(respone.data.returnData);
				context.commit('setTags', respone.data.returnData.tags);
				resolve(respone.data.returnData);
			}).catch((response) => {
				reject(response);
			})
		})

	},
	setSavedTagsAct : function(context, payload){
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.tags.saveTags, payload.save, config).then((respone) => {
				console.log(respone.data);
				context.commit('setSavedTags', payload.tags);
				resolve(respone.data);
			}).catch((response) => {
				reject(response);
			})
		})
	},
	async getAdminCheckYn ({commit}, payload) {
		return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.member.getAdminCheckUsers, {}, config).then((response) => {
				resolve(response.data);
			}).catch(response => {
				reject(response);
			});
		});
	},
	async getFollowers (context, payload){
		let url = global.baseURI + global.privateUri + global.api.post.tagRelation.getTagRelationUser;
		return new Promise((resolve, reject) => {
			return http.createAxios().post(url, payload, config).then((response) => {
				if(response.data.code=='OK'){

					context.commit('setSuggestUsers', response.data.returnData);
					resolve(response.data.returnData);
				}else{
					reject(false);
				}
			}).catch(response => {
				reject(response);
			});
		});
	},
}

export default {
	state,
	getters,
	mutations,
	actions
}
