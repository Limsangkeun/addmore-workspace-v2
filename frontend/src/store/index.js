import { createStore } from 'vuex';

const store = createStore({
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

export default store;
