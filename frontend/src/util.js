
const StringUtil = {
    chkOverByte: function(str, maxByte) {
        if(typeof str !== 'string'){
            console.log(str, " not string")
            return false;
        };

        let len = str.length;
        let byte = 0;
        let char = "";

        for ( var i=0; i< len; i++) {
            char = str.charAt(i);

            if (escape(char).length > 4) {
                byte += 2;
            } else {
                byte++;
            }
        }
        if(byte > maxByte) {
            return true;
        } else {
            return false;
        }
    },
    chkHanEngNum:function(str){
        if(typeof str !== 'string'){
            console.log(str, " not string")
            return false;
        };
        let regex = /^[가-힣|A-Z|a-z|0-9]+$/
        if(regex.test(str)){
            return true;
        }else{
            return false
        }
    },
    chkLength: function(str,minLen, maxLen){
        if(typeof str !== 'string'){
            console.log(str, " not string")
            return false;
        };
        let len = str.length;
        if(len >= minLen && len <= maxLen){
            return true;
        }else{
            return false;
        }
    }

};


export {StringUtil}
