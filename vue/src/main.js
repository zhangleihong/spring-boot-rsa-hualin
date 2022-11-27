import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import request from "@/utils/request";
import VueResource from 'vue-resource'
Vue.use(VueResource)
Vue.config.productionTip = false


Vue.use(ElementUI, { size: "mini" });


Vue.prototype.request=request

import { JSEncrypt } from 'jsencrypt'
//JSEncrypt加密方法
Vue.prototype.$encryptedData = function(publicKey, data) {
  //new一个对象
  let encrypt = new JSEncrypt()
  //设置公钥 publicKey是公钥
  encrypt.setPublicKey(window.publickey)
  //data是要加密的数据
  let result = encrypt.encrypt(data)
  return result
}
//JSEncrypt解密方法
Vue.prototype.$decryptData = function(privateKey, data) {
  // 新建JSEncrypt对象
  let decrypt = new JSEncrypt()
  // 设置私钥 privateKey是私钥
  decrypt.setPrivateKey(privateKey)
  // 解密数据 data
  let result = decrypt.decrypt(data)
  return result
}
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
