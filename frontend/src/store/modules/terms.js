import http from '../../http'
import global from '../../global'

const config = global.getAxiosConfig(global.locale);

const state = {

}

const getters = {

}

const mutations = {

}

const actions = {
    findLastestTerms : function (context) {
        return new Promise((resolve, reject) => {
            return http.createAxios().get(global.baseURI + global.publicUri + global.api.get.terms.findLastTerms, config).then((response) => {
                resolve(response.data);
            }).catch(response => {
                reject(response);
            });
        });
    },
    findAllTerms : function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.publicUri + global.api.post.terms.getAllTerms, payload, config).then((response) => {
                console.log(response);
                resolve(response.data.returnData);
            }).catch(response => {
                reject(response);
            });
        });
    }
}

export default {
    state,
    getters,
    mutations,
    actions
}
