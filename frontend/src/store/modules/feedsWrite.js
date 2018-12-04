import global from '../../global'
import http from '../../http';

const config = global.getAxiosConfig(global.locale,'form');

const state = {
	uIdx :  0,
	contents :  '',
	link1 :  '',
	feedLocations :  {},
	saveBooks :  [],
	saveMovies :  [],
	saveCultureAndExhibition :  [],
	multipartFiles :  [],
	tags : [],
	followers : []
}

const getters = {
	getWriteData : function (){
		const datas = {
			uIdx : state.uIdx,
			contents : state.contents,
			link1 : state.link1,
			feedLocations : state.feedLocations,
			saveBooks : state.saveBooks,
			saveMovies : state.saveMovies,
			saveCultureAndExhibition : state.saveCultureAndExhibition,
			multipartFiles : state.multipartFiles,
			tags : state.tags,
			followers : state.followers
		}
		return datas;
	}
}

const mutations = {
	makeWriteData : function (state, payload) {
		state.uIdx = payload.uIdx;
		state.contents = payload.contents;
		state.link1 = payload.link1;
		state.feedLocations = payload.feedLocations;
		state.saveBooks = payload.saveBooks;
		state.saveMovies = payload.saveMovies;
		state.saveCultureAndExhibition = payload.saveCultureAndExhibition;
		state.multipartFiles = payload.multipartFiles;
		state.tags = payload.tags;
		state.followers = payload.followers;
	},
	resetWriteData : function (state){
		state.uIdx = 0;
		state.contents = '';
		state.link1 = '';
		state.feedLocations = {};
		state.saveBooks = [];
		state.saveMovies = [];
		state.saveCultureAndExhibition = [];
		state.multipartFiles = [];
		state.tags = [],
		state.followers = [];
	}
}

const actions = {
	writeDataSet : function (context, payload) {
		context.commit('makeWriteData', payload);
	},
	removeWriteDataSet : function (context) {
		context.commit('resetWriteData');
	},
	saveFeeds : function (context, payload) {

	    console.log(payload);

		const form = new FormData();
		//feeds
		form.append('uIdx', payload.uIdx);
		form.append('contents', payload.contents);
		form.append('link1', payload.link1);

		//feedLocation
		if(payload.feedLocations && typeof payload.feedLocations === 'object' && Object.keys(payload.feedLocations).length !== 0) {
            form.append('locationIdx', payload.feedLocations.idx ? payload.feedLocations.idx : '0');
			form.append('address', payload.feedLocations.address);
			form.append('category', payload.feedLocations.category);
			form.append('description', payload.feedLocations.description);
			form.append('link', payload.feedLocations.link);
			form.append('mapx', payload.feedLocations.mapx);
			form.append('mapy', payload.feedLocations.mapy);
			form.append('roadAddress', payload.feedLocations.roadAddress);
			form.append('title', payload.feedLocations.title);
			form.append('feedLocations', payload.feedLocations.feedLocations);
		}
		//book List
		if(payload.saveBooks.length > 0){
			for(let m = 0; m < payload.saveBooks.length; m++){
                form.append('actionTagsBookIdx', payload.saveBooks[m].actionTagsIdx);
				form.append('bookId', payload.saveBooks[m].kyoboId);
			}
		}

		//movie List
		if(payload.saveMovies.length > 0){
			for(let m = 0; m < payload.saveMovies.length; m++){
                form.append('actionTagsMovieIdx', payload.saveMovies[m].actionTagsIdx);
				form.append('actor', payload.saveMovies[m].actor);
				form.append('director', payload.saveMovies[m].director);
				form.append('mImage', payload.saveMovies[m].image);
				form.append('mLink', payload.saveMovies[m].link);
				form.append('pubDate', payload.saveMovies[m].pubDate);
				form.append('mSubtitle', payload.saveMovies[m].subtitle);
				form.append('mTitle', payload.saveMovies[m].title);
				form.append('userRating', payload.saveMovies[m].userRating);
			}
		}

		//CultureAndExhibition List
		if(payload.saveCultureAndExhibition.length > 0) {
			for (let m = 0; m < payload.saveCultureAndExhibition.length; m++) {
                form.append('actionTagsCultureIdx', payload.saveCultureAndExhibition[m].actionTagsIdx);
				form.append('cateName1', payload.saveCultureAndExhibition[m].cateName1);
				form.append('cImage', payload.saveCultureAndExhibition[m].image);
				form.append('cLink', payload.saveCultureAndExhibition[m].link);
				form.append('cLocation', payload.saveCultureAndExhibition[m].location);
				form.append('startDate', payload.saveCultureAndExhibition[m].startDate);
				form.append('endDate', payload.saveCultureAndExhibition[m].endDate);
				form.append('cTitle', payload.saveCultureAndExhibition[m].title);
			}
		}

		//image files
		if(payload.multipartFiles.length > 0) {
			for (let m = 0; m < payload.multipartFiles.length; m++) {
				form.append('multipartFiles', payload.multipartFiles[m]);
			}
		}

		//follower List
		if(payload.followers.length > 0) {
			for (let m = 0; m < payload.followers.length; m++) {
				form.append('tIdx', payload.followers[m].tIdx);
			}
		}
		//tag List
		if(payload.tags.length > 0) {
			for (let m = 0; m < payload.tags.length; m++) {
				form.append('tag', payload.tags[m]);
			}
		}
		return new Promise( (resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.write, form, config).then((response) => {
				resolve(response.data);
			}).catch(response => {
				reject(response);
			});
		})
	}
}

export default {
	state,
	mutations,
	getters,
	actions
}
