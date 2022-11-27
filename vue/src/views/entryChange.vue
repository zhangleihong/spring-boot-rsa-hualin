<template>
  <div>

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="报单修改" prop="serial_info">
      </el-form-item>
      <el-form-item label="批次信息" prop="serial_info">
        <el-input type="textarea" v-model="ruleForm.serial_info"></el-input>
      </el-form-item>
      <el-form-item label="报单类型" prop="quote_type" >
        <el-radio-group v-model="ruleForm.quote_type" >
          <el-radio label="新增"></el-radio>
          <el-radio label="修改"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="业务编号" prop="serial_no">
        <el-input v-model="ruleForm.serial_no"></el-input>
      </el-form-item>
      <el-form-item label="合计金额" prop="total_amount">
        <el-input v-model="ruleForm.total_amount"></el-input>
      </el-form-item>
      <el-form-item label="华林卖出利率" prop="sale_rate">
        <el-input v-model="ruleForm.sale_rate"></el-input>
      </el-form-item>
      <el-form-item label="华林买入利率" prop="buy_rate">
        <el-input v-model="ruleForm.buy_rate"></el-input>
      </el-form-item>
      <el-form-item label="承兑行信息" prop="cd_bank_type">
        <el-input v-model="ruleForm.cd_bank_type"></el-input>
      </el-form-item>
      <el-form-item label="贴现行信息" required prop="tx_bank_type" >
        <el-input v-model="ruleForm.tx_bank_type"></el-input>
      </el-form-item>

      <el-form-item label="交易日">
        <el-col :span="11">
          <el-form-item prop="deal_date">
            <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.deal_date" style="width: 100%;" format="yyyy-MM-dd" value-format="yyyy-MM-dd" @change="getDealDate">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-form-item>

      <el-form-item label="期限信息" prop="expire" >
        <el-input v-model="ruleForm.expire"></el-input>
      </el-form-item>

      <el-form-item label="华林买入机构名称" prop="rebuy_branch_name">
        <el-input v-model="ruleForm.rebuy_branch_name"></el-input>
      </el-form-item>
      <el-form-item label="华林买入机构代码" prop="rebuy_branch_code">
        <el-input v-model="ruleForm.rebuy_branch_code"></el-input>
      </el-form-item>
      <el-form-item label="华林买入非法人产品名称" prop="rebuy_prod_name">
        <el-input placeholder="非必输" v-model="ruleForm.rebuy_prod_name"></el-input>
      </el-form-item>
      <el-form-item label="华林买入非法人产品代码" prop="rebuy_prod_code">
        <el-input v-model="ruleForm.rebuy_prod_code"></el-input>
      </el-form-item>
      <el-form-item label="华林买入机构交易员名称" prop="rebuy_trader_name">
        <el-input placeholder="非必输" v-model="ruleForm.rebuy_trader_name"></el-input>
      </el-form-item>
      <el-form-item label="华林买入机构交易员 ID" prop="rebuy_trader_id" >
        <el-input v-model="ruleForm.rebuy_trader_id"></el-input>
      </el-form-item>
      <el-form-item label="华林卖出机构名称" prop="sale_branch_name">
        <el-input v-model="ruleForm.sale_branch_name"></el-input>
      </el-form-item>
      <el-form-item label="华林卖出机构代码" prop="华林卖出机构代码">
        <el-input v-model="ruleForm.sale_branch_code"></el-input>
      </el-form-item>
      <el-form-item label="华林卖出非法人产品名称" prop="sale_prod_name">
        <el-input placeholder="非必输" v-model="ruleForm.sale_prod_name"></el-input>
      </el-form-item>
      <el-form-item label="华林卖出非法人产品代码" prop="sale_prod_code">
        <el-input placeholder="非必输" v-model="ruleForm.sale_prod_code"></el-input>
      </el-form-item>
      <el-form-item label="华林卖出机构交易员名称" prop="sale_trader_name">
        <el-input placeholder="非必输" v-model="ruleForm.sale_trader_name"></el-input>
      </el-form-item>
      <el-form-item label="华林卖出机构交易员 ID" prop="sale_trader_id">
        <el-input v-model="ruleForm.sale_trader_id"></el-input>
      </el-form-item>
      <el-form-item label="联系人姓名" prop="contact_name" >
        <el-input v-model="ruleForm.contact_name"></el-input>
      </el-form-item>
      <el-form-item label="联系人电话" prop="contact_number" >
        <el-input v-model="ruleForm.contact_number"></el-input>
      </el-form-item>

      <el-upload :action="'http://' + serverIp + ':9090/entry/import'" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">明细信息导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
<!--      <el-form-item label="买入信息识别" prop="distinguishMsg">-->
<!--        <el-input type="textarea" v-model="distinguishMsg"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="卖出信息识别" prop="distinguishMsg2">-->
<!--        <el-input type="textarea" v-model="distinguishMsg2"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
<!--        <el-button @click="distinguish()">识别</el-button>-->
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {serverIp} from "../../public/config";
import { JSEncrypt } from 'jsencrypt'
export default {
  data() {
    return {
      serverIp: serverIp,
      ruleForm: {
        serial_info: '',
        quote_type: '',
        serial_no: '',
        total_amount: '',
        sale_rate: '',
        buy_rate: '',
        cd_bank_type: '',
        tx_bank_type: '',
        deal_date: '',
        expire: '',
        rebuy_branch_name: '',
        rebuy_branch_code: '',
        rebuy_prod_name: '',
        rebuy_prod_code: '',
        rebuy_trader_name: '',
        rebuy_trader_id: '',
        sale_branch_name: '',
        sale_branch_code: '',
        sale_prod_name: '',
        sale_prod_code: '',
        sale_trader_name: '',
        sale_trader_id: '',
        contact_name: '',
        contact_number: '',
        item_list: [],
        desc1:'',
        desc2:'',

      },
      distinguishMsg: '',
      distinguishMsg2: '',
      rules: {
        quote_type: [{
          required: true,
          message: '请输入',
          trigger: 'change'
        }],
        serial_no: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        total_amount: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        sale_rate: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        buy_rate: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        cd_bank_type: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        tx_bank_type: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        deal_date: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        expire: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        rebuy_branch_code: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        rebuy_trader_id: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        sale_branch_code: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        sale_trader_id: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        contact_name: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        contact_number: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
      }
    };

  },

  created(){
    this.getinfo()
  },
  // mounted:function(){
  //   this.getinfo();//需要触发的函数
  // },
  methods: {


    getinfo(){
      let self = this;

      self.s_no = this.$route.params.s_id;
      console.log(self.s_no)
      this.request.get("/entry/"+self.s_no).then(res => {
        console.log(res)
        this.ruleForm=res
        //this.load()
      })
    },

    distinguish(){
      this.request.get("/entry/distinguishTicketEntry",{
        params: {
          desc1:JSON.stringify(this.distinguishMsg),
          desc2:JSON.stringify(this.distinguishMsg2),

        }
      }).then(res =>{
        console.log(res)

        this.ruleForm.rebuy_branch_name = res.rebuy_branch_name
        this.ruleForm.rebuy_branch_code = res.rebuy_branch_code
        this.ruleForm.rebuy_trader_name = res.rebuy_trader_name
        this.ruleForm.rebuy_trader_id = res.rebuy_trader_id
        this.ruleForm.sale_branch_name = res.sale_branch_name
        this.ruleForm.sale_branch_code = res.sale_branch_code
        this.ruleForm.sale_trader_name = res.sale_trader_name
        this.ruleForm.sale_trader_id = res.sale_trader_id


        // if(res.errcode === 0){
        // }else{
        //   this.$message.error("识别失败");
        // }
      })
    },


    getDealDate (deal_date) {
      this.ruleForm.deal_date = deal_date.toString();

    },

    handleExcelImportSuccess() {
      this.$message.success("导入成功")
    },


    submitForm(ruleForm) {
      //var encrypt = new JSEncrypt();
      //encrypt = JSEncrypt.setPublicKey( window.publickey );
      this.$refs[ruleForm].validate((valid) => {
        console.log(ruleForm)
        this.request.post("/entry/exitEntry", JSON.stringify(this.ruleForm)).then(res => {

          alert(JSON.stringify(res));

        })
      });
      this.$refs[ruleForm].resetFields();
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },


  }
}
</script>

<style>
</style>
