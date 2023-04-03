import { createStore } from 'vuex';

const globalStore = createStore({
    state() {
        return {
            userSession: {
                token: localStorage.getItem('atk'),
                username: '',
                isValid: false
            }
        };
    },
    getters: {},
    mutations: {
        authenticated(state, sessionInfo) {
            state.userSession = sessionInfo;
        }
    }
});

export default globalStore;
