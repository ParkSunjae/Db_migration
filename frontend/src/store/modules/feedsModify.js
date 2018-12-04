import global from '../../global'
import http from '../../http';


var config = global.getAxiosConfig(global.locale);
var configForm = global.getAxiosConfig(global.locale,'form');

const state = {
    idx: 0,
    uIdx: 0,
    contents: '',
    link1: '',
    feedLocations: {},
    saveBooks: [],
    saveMovies: [],
    saveCultureAndExhibition: [],
    multipartFiles: [],
    tags: [],
    followers: [],
    deleteLocationIdx : 0,
    deleteFilesIdx: [],
    deleteBooks: [],
    deleteMovies: [],
    deleteCulture: [],
    deleteTags: [],
    deleteFollowers: []
}

const getters = {
    getModifyData: function () {
        const datas = {
            idx: state.idx,
            uIdx: state.uIdx,
            contents: state.contents,
            link1: state.link1,
            feedLocations: state.feedLocations,
            saveBooks: state.saveBooks,
            saveMovies: state.saveMovies,
            saveCultureAndExhibition: state.saveCultureAndExhibition,
            multipartFiles: state.multipartFiles,
            tags: state.tags,
            followers: state.followers,
            deleteLocationIdx : state.deleteLocationIdx,
            deleteFilesIdx: state.deleteFilesIdx,
            deleteBooks: state.deleteBooks,
            deleteMovies: state.deleteMovies,
            deleteCulture: state.deleteCulture,
            deleteTags: state.deleteTags,
            deleteFollowers: state.deleteFollowers
        }
        return datas;
    }
}

const mutations = {
    makeModifyData: function (state, payload) {
        state.idx = payload.idx;
        state.uIdx = payload.uIdx;
        state.contents = payload.contents;
        state.link1 = payload.link1;
        state.feedLocations = payload.feedLocations;
        state.saveBooks = payload.saveBooks;
        state.saveMovies = payload.saveMovies;
        state.saveCultureAndExhibition = payload.saveCultureAndExhibition;
        state.tags = payload.tags;
        state.followers = payload.followers;
        state.multipartFiles = payload.multipartFiles;
        state.deleteLocationIdx = payload.deleteLocationIdx;
        state.deleteFilesIdx = payload.deleteFilesIdx;
        state.deleteBooks = payload.deleteBooks;
        state.deleteMovies = payload.deleteMovies;
        state.deleteCulture = payload.deleteCulture;
        state.deleteTags = payload.deleteTags;
        state.deleteFollowers = payload.deleteFollowers;
    },
    resetModifyData: function (state) {
        state.uIdx = 0;
        state.contents = '';
        state.link1 = '';
        state.feedLocations = {};
        state.saveBooks = [];
        state.saveMovies = [];
        state.saveCultureAndExhibition = [];
        state.multipartFiles = [];
        state.tags = [];
        state.followers = [];
        state.deleteLocationIdx = 0;
        state.deleteFilesIdx = [];
        state.deleteBooks = [];
        state.deleteMovies = [];
        state.deleteCulture = [];
        state.deleteTags = [];
        state.deleteFollowers = [];
    }
}

const actions = {
    modifyDataSet: function (context, payload) {
        context.commit('makeModifyData', payload);
    },
    removeModifyDataSet: function (context) {
        context.commit('resetModifyData');
    },
    findFeedDetailData : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.findDetailData, payload, config).then((response) => {
                resolve(response.data.returnData);
            }).catch((reponse) => {
                reject(response);
            })
        })
    },
    saveModifyFeeds: function (context, payload) {

        const form = new FormData();
        //feeds
        form.append('idx', payload.idx);
        form.append('uIdx', payload.uIdx);
        form.append('contents', payload.contents);
        form.append('link1', payload.link1);

        if(payload.feedLocations.idx){
            form.append('locationIdx', payload.feedLocations.idx);
        }else{
            form.append('locationIdx', '0');
        }

        //feedLocation
        if (payload.feedLocations && typeof payload.feedLocations === 'object' && Object.keys(payload.feedLocations).length !== 0) {

            form.append('idx', payload.feedLocations.idx ? payload.feedLocations.idx : '0');
            form.append('address', payload.feedLocations.address);
            form.append('category', payload.feedLocations.category);
            form.append('description', payload.feedLocations.description);
            form.append('link', payload.feedLocations.link);
            form.append('mapx', payload.feedLocations.mapx);
            form.append('mapy', payload.feedLocations.mapy);
            form.append('roadAddress', payload.feedLocations.roadAddress);
            form.append('title', payload.feedLocations.title);
        }
        //book List
        if (payload.saveBooks.length > 0) {
            for (let m = 0; m < payload.saveBooks.length; m++) {
                form.append('actionTagsBookIdx', payload.saveBooks[m].actionTagsIdx);
                form.append('bookId', payload.saveBooks[m].kyoboId);
            }
        }

        //movie List
        if (payload.saveMovies.length > 0) {
            for (let m = 0; m < payload.saveMovies.length; m++) {
                form.append('actionTagsMovieIdx', payload.saveMovies[m].actionTagsIdx);
                form.append('movieIdx', payload.saveMovies[m].idx);
                form.append('actor', payload.saveMovies[m].actor);
                form.append('director', payload.saveMovies[m].director);
                form.append('mImage', payload.saveMovies[m].mimage);
                form.append('mLink', payload.saveMovies[m].mlink);
                form.append('pubDate', payload.saveMovies[m].pubDate);
                form.append('mSubtitle', payload.saveMovies[m].mSubtitle);
                form.append('mTitle', payload.saveMovies[m].mtitle);
                form.append('userRating', payload.saveMovies[m].userRating);
            }
        }

        //CultureAndExhibition List
        if (payload.saveCultureAndExhibition.length > 0) {
            for (let m = 0; m < payload.saveCultureAndExhibition.length; m++) {
                form.append('actionTagsCultureIdx', payload.saveCultureAndExhibition[m].actionTagsIdx);
                form.append('cultureIdx', payload.saveCultureAndExhibition[m].idx);
                form.append('cateName1', payload.saveCultureAndExhibition[m].cateName1);
                form.append('cImage', payload.saveCultureAndExhibition[m].cimage);
                form.append('cLink', payload.saveCultureAndExhibition[m].clink);
                form.append('cLocation', payload.saveCultureAndExhibition[m].clocation);
                form.append('startDate', payload.saveCultureAndExhibition[m].startDate);
                form.append('endDate', payload.saveCultureAndExhibition[m].endDate);
                form.append('cTitle', payload.saveCultureAndExhibition[m].ctitle);
            }
        }

        //follower List
        if (payload.followers.length > 0) {
            for (let m = 0; m < payload.followers.length; m++) {
                form.append('tIdx', payload.followers[m].tIdx);
            }
        }
        //tag List
        if (payload.tags.length > 0) {
            for (let m = 0; m < payload.tags.length; m++) {
                form.append('tag', payload.tags[m]);
            }
        }


        //delete items setup
        if (payload.deleteBooks.length > 0) {
            for (let m = 0; m < payload.deleteBooks.length; m++) {
                form.append('deleteBookIdx', payload.deleteBooks[m]);
            }
        }else{
            form.append('deleteBookIdx', payload.deleteBooks);
        }

        if (payload.deleteFilesIdx.length > 0) {
            for (let m = 0; m < payload.deleteFilesIdx.length; m++) {
                form.append('deleteFileIdx', payload.deleteFilesIdx[m]);
            }
        }else{
            form.append('deleteFileIdx', payload.deleteFilesIdx);
        }

        if (payload.deleteMovies.length > 0) {
            for (let m = 0; m < payload.deleteMovies.length; m++) {
                form.append('deleteMovieIdx', payload.deleteMovies[m]);
            }
        }else{
            form.append('deleteMovieIdx', payload.deleteMovies);
        }

        if (payload.deleteCulture.length > 0) {
            for (let m = 0; m < payload.deleteCulture.length; m++) {
                form.append('deleteCultureIdx', payload.deleteCulture[m]);
            }
        }else{
            form.append('deleteCultureIdx', payload.deleteCulture);
        }

        if (payload.deleteTags.length > 0) {
            for (let m = 0; m < payload.deleteTags.length; m++) {
                form.append('deleteTagIdx', payload.deleteTags[m]);
            }
        }else{
            form.append('deleteTagIdx', payload.deleteTags);
        }

        if (payload.deleteFollowers.length > 0) {
            for (let m = 0; m < payload.deleteFollowers.length; m++) {
                form.append('deleteFollowerIdx', payload.deleteFollowers[m]);
            }
        }else{
            form.append('deleteFollowerIdx', payload.deleteFollowers);
        }

        if(payload.deleteLocationIdx !=0){
            form.append('deleteLocationIdx', payload.deleteLocationIdx);
        }else{
            form.append('deleteLocationIdx', 0);
        }

        //image files
        if (payload.multipartFiles.length > 0) {
            for (let m = 0; m < payload.multipartFiles.length; m++) {
                form.append('multipartFiles', payload.multipartFiles[m]);
            }
        }

        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feed.update, form, configForm).then((response) => {
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
