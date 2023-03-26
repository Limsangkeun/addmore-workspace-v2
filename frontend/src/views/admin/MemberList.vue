<script setup>

</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <DataTable
            dataKey="id"
            :paginator="true"
            :rows="20"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            :rowsPerPageOptions="[5, 10, 25]"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
            responsiveLayout="scroll"
        >
          <template #header>
            <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
              <h5 class="m-0 font-bold">직원현황</h5>
              <div class="my-2 flex flex-row">
                <span class="block mt-2 mr-2 md:mt-0 p-input-icon-left">
                  <i class="pi pi-search" />
                  <InputText placeholder="Search..." />
                </span>
                <Button label="Search" icon="pi pi-search" class="mr-2"/>
                <Button label="New" icon="pi pi-plus" class="p-button-success mr-2"/>
                <Button label="Delete" icon="pi pi-trash" class="p-button-danger"/>
              </div>
            </div>
          </template>
          <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
        </DataTable>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>