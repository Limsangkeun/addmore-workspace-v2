import axios from "axios";
import useSessionStore from "@/store/sessionStore";
import useCommonStore from "@/store/commonStore";

class UT {
    /**
     *
     * @param url
     * @param data
     * @param opt
     * @returns {Promise<unknown>}
     */
    static post (url, data, opt) {
        const sessionStore = useSessionStore();
        const commonStore = useCommonStore();
        const options = opt || {};
        options.headers =  {
            "Authorization": "Bearer " + sessionStore.token,
            "Content-Type": "application/json"
        }
        return new Promise((resolve, reject) => {
            commonStore.setLoading(true);
            axios.post(url, data, options).then(response => {
                if(response.data.isError) {
                    const msg = response.data.msg || '상세 에러 내용이 없습니다.';
                    reject(msg);
                    return;
                }
                resolve(response.data);
            }). catch(err => {
                const msg = err.response.data.msg || '상세 에러 내용이 없습니다.';
                reject(msg);
            }).finally(()=>{
                commonStore.setLoading(false);
            });
        });
    };

    static get (url, opt) {
        const sessionStore = useSessionStore();
        const commonStore = useCommonStore();
        const options = opt || {};
        options.headers =  {
            "Authorization": "Bearer " + sessionStore.token,
        }
        return new Promise((resolve, reject) => {
            commonStore.setLoading(true);
            axios.get(url, options).then(response => {
                if(response.data.isError) {
                    const msg = response.data.msg || '상세 에러 내용이 없습니다.';
                    reject(msg);
                    return;
                }
                resolve(response.data);
            }). catch(err => {
                if(err.response.status === 403) {
                    alert('세션이 만료되어 로그인 페이지로 이동합니다.');
                }
                const msg = err.response.data.msg || '상세 에러 내용이 없습니다.';
                reject(msg);
            }).finally(()=>{
                commonStore.setLoading(false);
            });
        });
    };
};

export default UT;