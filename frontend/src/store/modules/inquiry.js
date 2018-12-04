import global from '../../global'
import http from '../../http';

var config = global.getAxiosConfig(global.locale);

const state ={

}
const getters ={

}
const mutations ={

}
const actions ={
  saveInquiryAct : function(context,payload){
      return new Promise((resolve, reject) => {
        payload.uidx = context.getters.getMemberIdx;
  	    return http.createAxios().post(global.baseURI + global.privateUri + global.api.post.inquiry.saveInquiry,payload, config).then((response) => {
          if(response.data.code=='OK'){
            resolve(true);
          }else{
            resolve(false);
          }
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
