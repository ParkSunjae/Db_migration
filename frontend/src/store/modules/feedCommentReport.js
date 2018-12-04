import global from '../../global'
import http from '../../http';

const config = global.getAxiosConfig(global.locale);

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
    saveFeedCommentReportAct : function (context, payload) {
        payload['reportUidx'] = context.getters.getMemberIdx;
        return new Promise((resolve, reject) => {
			return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.feedCommentReport.saveFeedCommentReport, payload, config).then((response) => {
				resolve(response.data);
			}).catch(response => {
				reject(response);
			});
		});
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}
