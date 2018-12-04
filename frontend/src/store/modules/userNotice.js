import http from '../../http'
import global from '../../global'

const config = global.getAxiosConfig(global.locale);

const state = {}

const getters = {}

const mutations = {}

const actions = {
    getMyNoticeList: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.alarm.callMyAlarm, payload, config).then((response) => {
                resolve(response.data);
            }).catch((response) => {
                reject(response)
            })
        })
    },
    getMyNoticeAllList: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.alarm.callMyAlarmAll, payload, config).then((response) => {
                resolve(response.data);
            }).catch((response) => {
                reject(response)
            })
        })
    },
    getMyNoticeCount: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.alarm.callMyAlarmCount, payload, config).then((response) => {
                resolve(response.data);
            }).catch((response) => {
                reject(response)
            })
        })
    },
    changeAlarmShowYn: function (context, payload) {
        return new Promise((resolve, reject) => {
            return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.alarm.changeAlarmShow, payload, config).then((response) => {
                resolve(response.data);
            }).catch((response) => {
                reject(response)
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
