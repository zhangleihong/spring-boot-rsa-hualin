<template>
  <div style="width: 100%;">
    <!--    头部-->
    <div style="display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #eee">
      <div style="width: 300px; display: flex; padding-left: 30px">

        <div style="color:#1E90FF " >瑞琰科技线上报单系统</div>
      </div>
      <div style="flex: 1;">
        <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal"  router>
          <el-menu-item index="/entryApplication">报单申请</el-menu-item>
          <el-menu-item index="/searchPage">报单通知</el-menu-item>

        </el-menu>
      </div>

    </div>

    <div style="width: 100%;">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  name: "Front",
  data() {
    return {
    }
  },
  created() {
  },
  methods: {

  }
}
</script>

<style>
.item {
  display: inline-block;
  width: 100px;

  text-align: center;
  cursor: pointer
}

.item a {
  color: #1E90FF;
}

.item:hover {
  /* background-color: LightPink; */
  background-color: #ffe5b3;
}
</style>
