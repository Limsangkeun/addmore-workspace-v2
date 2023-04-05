import { createStore } from 'vuex';

const globalStore = createStore({
    state() {
        return {
            userSession: {
                token: localStorage.getItem('atk'),
                username: '',
                isValid: false
            },
            showLoading: false
        };
    },
    getters: {},
    mutations: {
        authenticated(state, sessionInfo) {
            state.userSession = sessionInfo;
        },
        setLoading(state, flag) {
            state.showLoading = flag;
        }
    }
});

export default globalStore;
