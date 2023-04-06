import {defineStore} from "pinia";

const useCommonStore = defineStore('commonStore', {
    state : () => ({
        isLoading : false
    }),
    getters: {

    },
    actions: {
        setLoading (flag) {
            this.isLoading = flag;
        }
    }
})

export default useCommonStore;