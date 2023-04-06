import {defineStore} from "pinia";

const useSessionStore = defineStore('sessionStore', {
    state: () => ({
        token: localStorage.getItem('atk'),
        username: '',
        isValid : false
    }),
    getters: {
        getUserSessionInfo () {
            return {
                token : this.token,
                username : this.username,
                isValid : this.isValid
            }
        }
    },
    actions: {
        authenticated (sessionInfo) {
            this.token = sessionInfo.token;
            this.username = sessionInfo.username;
            this.isValid = true;
        }
    }
});

export default useSessionStore;
