import {reactive} from "vue";
import router from '@/router';

export const store = reactive({
    userInfo: {
        id : '',
        username : '',
        token: ''
    }
});