<script setup>

import {inject, onMounted, reactive, ref} from "vue";
import Toast from "primevue/toast";
import {useToast} from "primevue/usetoast";

const UT = inject('$UT');
const toast = useToast();
const saveModel = reactive({
  id:'',
  username: '',
  name: '',
  email: '',
  password: '',
  dept: '',
  birth: null,
  joinDate: null,

  reset () {
    this.id = '';
    this.username = '';
    this.name = '';
    this.email = '';
    this.password = '';
    this.dept = '';
    this.birth = null;
    this.joinDate = null;
  }
});
const deptList = ref([]);
const authorities = ref([]);

onMounted(()=> {
  UT.post('/api/dept/find', {name:''}, null)
      .then(data => {
        deptList.value = data.dept_list;
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
});

const save = () => {
  const url = _.isEmpty(saveModel.id) ? 'create' : 'modify';
  UT.post('/api/user/'+url, saveModel, null)
      .then(data => {
        toast.add({ severity: 'success', summary: '성공', detail: '권한이 저장되었습니다.', life: 3000 });
      })
      .catch(msg => {
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
}

const findAuthList = () => {

}
</script>

<template>
  <TabView>
    <TabPanel header="상세정보">
      <div class="grid">
        <div class="col-12">
          <div class="flex md:justify-content-end">
            <div class="my-2 flex flex-row">
              <Button class="p-button-success mr-2" icon="pi pi-plus" label="저장"/>
              <Button class="p-button-danger mr-2" icon="pi pi-refresh" label="비밀번호 초기화"/>
            </div>
          </div>
        </div>
        <div class="col-12">
          <div class="p-fluid">
            <div class="field grid">
              <label for="mUsername" class="col-12 mb-2 md:col-2 md:mb-0">아이디</label>
              <div class="col-12 md:col-10">
                <InputText id="mUsername" type="text" class="p-inputtext-sm" maxlength="30" :value="saveModel.username"/>
              </div>
            </div>
            <div class="field grid">
              <label for="mPwd" class="col-12 mb-2 md:col-2 md:mb-0">비밀번호</label>
              <div class="col-12 md:col-10">
                <Password id="mPwd" type="password" class="p-inputtext-sm" maxlength="255" :value="saveModel.password"/>
              </div>
            </div>
            <div class="field grid">
              <label for="mName" class="col-12 mb-2 md:col-2 md:mb-0">이름</label>
              <div class="col-12 md:col-10">
                <InputText id="mName" type="text" class="p-inputtext-sm" maxlength="30" :value="saveModel.name"/>
              </div>
            </div>
            <div class="field grid">
              <label for="mEmail" class="col-12 mb-2 md:col-2 md:mb-0">이메일</label>
              <div class="col-12 md:col-10">
                <InputText id="mEmail" type="email" class="p-inputtext-sm" :value="saveModel.email"/>
              </div>
            </div>
            <div class="field grid">
              <label for="mDept" class="col-12 mb-2 md:col-2 md:mb-0">부서</label>
              <div class="col-12 md:col-10">
                <Dropdown id="mDept" class="p-inputtext-sm" :options="deptList" option-label="name" option-value="id" v-model="saveModel.dept"></Dropdown>
              </div>
            </div>
            <div class="field grid">
              <label for="mBirth" class="col-12 mb-2 md:col-2 md:mb-0">생년월일</label>
              <div class="col-12 md:col-10">
                <Calendar id="mBirth" v-model="saveModel.birth" dateFormt="yy-mm-dd"/>
              </div>
            </div>
            <div class="field grid">
              <label for="mJoinDate" class="col-12 mb-2 md:col-2 md:mb-0">입사일</label>
              <div class="col-12 md:col-10">
                <Calendar id="mJoinDate" v-model="saveModel.joinDate" dateFormt="yy-mm-dd"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </TabPanel>
    <TabPanel header="권한">
      <DataTable
          id="authGrid"
          :value="authorities"
          :show-gridlines="true"
        >
        <Column selection-mode="multiple"></Column>
        <Column header="권한명"></Column>
        <Column></Column>
      </DataTable>
    </TabPanel>
  </TabView>
</template>

<style scoped></style>