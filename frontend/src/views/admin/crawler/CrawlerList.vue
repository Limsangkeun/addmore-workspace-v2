<script setup>
import {useToast} from "primevue/usetoast";
import {useField, useForm} from "vee-validate";
import {onMounted, reactive, ref} from "vue";
import * as yup from 'yup'

const toast = useToast();
const {handleSubmit, resetForm} = useForm();
const { value: fromDate, errorMessage: fromDateErrMsg } = useField(
    'fromDate'
);
const { value: toDate, errorMessage: toDateErrMsg } = useField(
    'toDate'
);

const showDialog = ref(false);
const testKeywordData = [
  {id:1, name:'test2'},
  {id:2, name:'test'},
  {id:3, name:'test'},
  {id:4, name:'test'},
  {id:5, name:'test'},
  {id:6, name:'test'},
  {id:7, name:'test'},
  {id:8, name:'test'},
  {id:9, name:'test'},
  {id:10, name:'test'},
  {id:11, name:'test'},
  {id:12, name:'test'},
  {id:13, name:'test'},
]
onMounted(()=> {
  fromDate.value = new Date();
  toDate.value = new Date();
});
const fnSearchHistory = handleSubmit((values) => {
  console.log(values);
  return false;
})

</script>

<template>
  <div class="card flex-1 flex flex-column">
    <h5 class="font-bold">크롤러</h5>
    <div class="flex-1 grid">
      <div class="col-4 flex flex-column">
        <data-table
            id="regionGrid"
            class="p-datatable-sm"
            :value="testKeywordData"
            scrollable
            scroll-height="250px"
            show-gridlines
        >
          <template #header>
            <div style="text-align:right">
              <Button class="p-button-info p-button-sm mr-2" label="추가"></Button>
              <Button class="p-button-danger p-button-sm" label="삭제"></Button>
            </div>
          </template>
          <column selection-mode="multiple" header-class="p-selection-header"></column>
          <column field="name" header="지역명" body-class="flex-1" header-class="flex-1"></column>
        </data-table>
        <data-table
            id="keywordGrid"
            :value="testKeywordData"
            class="p-datatable-sm mt-3"
            scrollable
            scroll-height="250px"
            show-gridlines
        >
          <template #header>
            <div style="text-align:right">
              <Button class="p-button-info p-button-sm mr-2" label="추가"></Button>
              <Button class="p-button-danger p-button-sm" label="삭제"></Button>
            </div>
          </template>
          <column selection-mode="multiple" header-class="p-selection-header"></column>
          <column field="name" header="키워드"></column>
        </data-table>
      </div>
      <div class="col-8">
        <data-table
            class="p-datatable-sm"
            scrollable
            scroll-height="500px"
            table-style="min-height: 500px"
            show-gridlines
            paginator
            :rows="10"
        >
          <template #header>
            <div style="text-align:right">
              <form id="historyForm" @submit="fnSearchHistory">
                <calendar id="fromDate" class="p-inputtext-sm mr-2" v-model="fromDate" :max-date="toDate" date-format="yy-mm-dd" style="width:100px"></calendar>
                {{fromDateErrMsg}}
                <label class="font-bold mr-2">~</label>
                <calendar id="toDate" class="p-inputtext-sm mr-2" v-model="toDate" :min-date="fromDate" date-format="yy-mm-dd" style="width:100px"></calendar>
                <Button type="submit" class="p-button-info p-button-sm mr-2" label="조회"></Button>
              </form>
            </div>
          </template>
          <column header="No." class="f-100-px"></column>
          <column header="키워드"></column>
          <column header="업체 수"></column>
          <column header="실행시간"></column>
          <column></column>
        </data-table>
      </div>
    </div>
    <Toast></Toast>
  </div>
  <Dialog v-model:visible="showDialog" :modal="true" :style="{ width: '30vw' }"
          header="부서정보">
    <div class="p-fluid">
      <div class="field">
        <label for="name1">Name</label>
        <InputText id="name1" v-model="saveModel.name" type="text" />
      </div>
    </div>
    <template #footer>
      <Button class="p-button-outlined" icon="pi pi-check" label="Ok" @click="saveDept"/>
      <Button class="p-button-outlined" icon="pi pi-check" label="Cancel" @click="fnClose"/>
    </template>
  </Dialog>
</template>

<style scoped>

</style>