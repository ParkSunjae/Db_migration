import global from '../../global'
import http from '../..//http';


const state = {
    showLoader: false,
    showGnb4 : false
}
const getters = { //getter 개념
    getShowGnb4 : function () {
        return state.showGnb4;
    },
    getLoadings : function () {
        return state.showLoader;
    }
}
const mutations = { // setters 개념
    toggleShowGnb4: function (state, payload) {
        state.showGnb4 = payload;
    },
    toggleLoading: function (state, payload) {
        state.showLoader = payload;
    }
}
const actions = { //methods 개념 call시 dispatch 사용
    showYnLoading: function (context, payload) {
        context.commit("toggleLoading", payload);
    },
    showGnb4Act : function (context, payload){
        context.commit("toggleShowGnb4", payload);
    }
}

export default {
    state,
    getters,
    mutations,
    actions
}
