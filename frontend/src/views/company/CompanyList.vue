<script setup>
import {inject, onMounted, reactive, ref} from "vue";
import _ from "lodash";
import {useToast} from "primevue/usetoast";

const UT = inject('$UT');
const toast = useToast();
const companyList = ref([]);
const selectedCompanyList = ref([]);
const searchParam = reactive({
  name: '',
  totalCount: 0,
  totalPage: 0,
  reset() {
    this.name = '';
    this.totalCount = 0;
    this.totalPage = 0;
  }
});

onMounted(()=> {
  fnSearch();
});

const fnKeyDown = (event) => {
  if(event && event.keyCode === 13) fnSearch();
}

const fnSearch = (event) => {
  if(_.isEmpty(event)) {
    event = {
      page : 0,
      rows : 50
    }
  }
  UT.get('/api/company'+`?page=${event.page? event.page : 0}&size=${event.rows? event.rows : 50}${_.isEmpty(searchParam.name)?'':'&name='+encodeURIComponent(searchParam.name)}`, null)
      .then(data => {
        companyList.value = data.company_list;
        searchParam.totalCount = data.total_count;
        searchParam.totalPage = data.total_page;
      })
      .catch(msg => {
        console.log(msg);
      });
};

const fnOpenSite = (siteUrl) => {
  window.open(siteUrl);
};

const fnChangeCallStatus = (data, status) => {
  const tempStatus = data.status;
  data.status = status;
  UT.post("/api/company/status", data, null)
      .catch(msg => {
        data.status = tempStatus;
        toast.add({ severity: 'error', summary: '실패', detail: msg, life: 3000 });
      });
}

</script>

<template>
  <div class="card flex-1">
    <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center mb-3">
      <h5 class="m-0 font-bold">업체정보</h5>
      <div class="my-2 flex flex-row">
            <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                  <i class="pi pi-search"/>
                  <InputText class="p-inputtext-sm" placeholder="업체명 검색" @keydown="fnKeyDown" v-model="searchParam.name"/>
                </span>
      </div>
    </div>
    <DataTable
        id="companyGrid"
        class="p-datatable-sm"
        :value="companyList"
        v-model:selection="selectedCompanyList"
        lazy
        dataKey="id"
        @page="fnSearch($event)"
        :paginator="true"
        :rows="25"
        v-model:total-records="searchParam.totalCount"
        :last="searchParam.totalPage"
        :rowsPerPageOptions="[5, 10, 25, 50]"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        show-gridlines
        scrollable
        scrollHeight="flex"
    >
      <Column class="f-40-px" selectionMode="multiple"></Column>
      <Column field="name" header="업체명"></Column>
      <Column field="category" header="업종" class="f-200-px"></Column>
      <Column field="addr" header="주소"></Column>
      <Column field="tel" header="연락처" class="justify-content-center f-120-px"></Column>
      <Column field="is114" header="114등록" class="justify-content-center f-80-px"></Column>
      <Column field="regDate" header="업체등록일" class="justify-content-center f-120-px"></Column>
      <Column field="createdAt" header="생성일" class="justify-content-center f-120-px">
        <template #body="props">
          {{props.data.createdAt.substring(0,10)}}
        </template>
      </Column>
      <Column field="test1" header="관련페이지" class="f-200-px justify-content-center">
        <template #body="props">
          <span class="p-buttonset">
            <Button label="홈" class="p-button-sm" icon="pi pi-home" style="width:80px;"
                    :disabled="_.isEmpty(props.data.homePage)" @click="fnOpenSite(props.data.homePage)"></Button>
            <Button label="상세" class="p-button-sm" icon="pi pi-sitemap" style="width:80px;"
                    :disabled="_.isEmpty(props.data.detailUrl)" @click="fnOpenSite(props.data.detailUrl)"></Button>
          </span>
        </template>
      </Column>
      <Column field="test1" header="통화여부" class="justify-content-center f-100-px">
        <template #body="props">
          <Button label="통화" class="p-button-success p-button-sm" style="width:80px;" v-if="props.data.status === 'NOT_CALLED'" @click="fnChangeCallStatus(props.data, 'CALLED')"></Button>
          <Button label="미통화" class="p-button-danger p-button-sm" style="width:80px;"  v-if="props.data.status == 'CALLED'" @click="fnChangeCallStatus(props.data, 'NOT_CALLED')"></Button>
        </template>
      </Column>
    </DataTable>
  </div>
  <Toast/>
</template>

<style scoped>

</style>