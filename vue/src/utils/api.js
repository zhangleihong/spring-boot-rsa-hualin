// ====== 方法1：把所有请求的地址封装成一个方法，好处就是有个归总 ======
//引入request.js文件
import request from "./request";
 
// 试一下
export function getOverMoney(data) {
    return request({
        url: "/over-money/findovermoney", // 这个地址是去掉公共地址剩下的地址
        method: "GET", // 请求方式 支持多种方式  get post put delete 等等
        data //发送请求要配置的参数 无参数的情况下也可以不写
    });
}


// ====== 方法2：直接挂载到全局 ======
//把request.js文件引入到main.js中
// import utils from "./request";

// Vue.prototype.$http = http;

// // 最后是在页面中的引用:
// this.$http.post('/getuser/user',这里填参数).then((res) => {
//           console.log(res);
//         }).catch((err) => {
//           console.log(err);
//         });
