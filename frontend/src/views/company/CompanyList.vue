<script setup>
import {inject, ref} from "vue";
import _ from "lodash";

const UT = inject('$UT');
const companyList = ref([]);
const searchParam = ref({
  name: '',
  page: 0,
  size: 50,
  totalCount: 0,
  totalPage: 0
});

const fnSearch = () => {

  UT.get('/api/company'+`?page=${searchParam.value.page}&size=${searchParam.value.size}`, null)
      .then(data => {
        companyList.value = data.company_list;
        searchParam.value.totalCount = data.total_count;
        searchParam.value.totalPage = data.total_page;
      })
      .catch(msg => {
        console.log(msg);
      })
}

</script>

<template>
  <div class="card flex-1">
    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="m-0 font-bold">업체정보</h5>
      <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                  <i class="pi pi-search"/>
                  <InputText class="p-inputtext-sm" placeholder="업체명 검색" @keydown="fnSearch"/>
                </span>
      </div>
    </div>
    <DataTable
        id="companyGrid"
        class="p-datatable-sm"
        :value="companyList"
        dataKey="id"
        :paginator="true"
        :rows="searchParam.size"
        :total-records="searchParam.totalCount"
        :rowsPerPageOptions="[5, 10, 25]"
        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        responsiveLayout="scroll"
    >
      <Column headerStyle="width: 3rem" selectionMode="multiple"></Column>
      <Column field="name" header="업체명"></Column>
      <Column field="category" header="업종"></Column>
      <Column field="addr" header="주소"></Column>
      <Column field="tel" header="연락처"></Column>
      <Column field="is114" header="114등록여부"></Column>
      <Column field="regDate" header="업체등록일"></Column>
      <Column field="test" header="생성일"></Column>
      <Column field="test1" header="관련페이지"></Column>
    </DataTable>
  </div>
</template>

<style scoped>

</style>