import global from "./global";
import axios from "axios";
import eventHub from './eventHub'

export default {
    createAxios: function () {
        const axiosis = axios.create(global.getAxiosConfig(global.locale));
        axiosis.interceptors.request.use(
            conf => {
                eventHub.$emit('before-request');
                // console.log("===========request call=============");
                return conf;
            },
            error => {
                eventHub.$emit('request-error');
                console.log(error);
                console.log("===========request error=============");

                if(!error.status){
                    if (global.locale == 'ko') {
                        eventHub.$emit("common-alert","서버와 연결이 되지 않습니다. 통신환경을 확인 해주세요.");
                    } else {
                        eventHub.$emit("common-alert","server connecting fail. check network environment.");
                    }

                } else {
                    console.log('===== common error=======',error);
                    if(error.code === 500){
                        if (global.locale == 'ko') {
                            eventHub.$emit("common-alert","서버 오류.");
                        } else {
                            eventHub.$emit("common-alert","Server Error");
                        }
                    }
                    if(error.code === 401){
                        if (global.locale == 'ko') {
                            eventHub.$emit("common-alert","권한이 없습니다..");
                        } else {
                            eventHub.$emit("common-alert","No Authentication");
                        }
                    }
                }

                if (error.response) {
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2xx
                    // console.log(error.response.data);
                    // console.log(error.response.status);
                    // console.log(error.response.headers);
                } else if (error.request) {
                    // The request was made but no response was received
                    // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                    // http.ClientRequest in node.js
                    console.log(error.request);
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }
                console.log(error.config);


                return Promise.reject(error);
            }
        );
        axiosis.interceptors.response.use(
            response => {
                // console.log('eventHub',eventHub);
                eventHub.$emit('after-response');
                return response;
            },
            error => {
                // console.log('eventHub',eventHub);
                eventHub.$emit('response-error');
                console.log(error);
                console.log("===========response error=============");


                if(!error.status){
                    if (global.locale == 'ko') {
                        eventHub.$emit("common-alert","서버와 연결이 되지 않습니다. 통신환경을 확인 해주세요.");
                    } else {
                        eventHub.$emit("common-alert","server connecting fail. check network environment.");
                    }

                } else {
                    console.log('===== common error=======',error);
                    if(error.code === 500){
                        if (global.locale == 'ko') {
                            eventHub.$emit("common-alert","서버 오류.");
                        } else {
                            eventHub.$emit("common-alert","Server Error");
                        }
                    }
                    if(error.code === 401){
                        if (global.locale == 'ko') {
                            eventHub.$emit("common-alert","권한이 없습니다..");
                        } else {
                            eventHub.$emit("common-alert","No Authentication");
                        }
                    }
                }

                if (error.response) {
                    console.log("===========error.response=============");
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2xx
                    console.log("data=====", error.response.data);
                } else if (error.request) {
                    console.log("===========error.request=============");
                    // The request was made but no response was received
                    // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                    // http.ClientRequest in node.js

                    if (error.code == 'ECONNABORTED') {
                        if (global.locale == 'ko') {
                            eventHub.$emit("common-alert","서버와 연결이 되지 않습니다. 통신환경을 확인 해주세요.");
                        } else {
                            eventHub.$emit("common-alert","server connecting fail. check network environment.");
                        }
                    }


                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }
                console.log(error.config);

                return Promise.reject(error);
            }
        );
        return axiosis;
    }
}
