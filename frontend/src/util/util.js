import axios from "axios";
import globalStore from "@/store";

class UT {
    post (url, data, opt) {
        if(!url.startsWith("/api")) return;
        const options = opt || {};
        options.headers =  {
            "Authorization": "Bearer " + globalStore.state.userSession.token,
            "Content-Type": "application/json"
        }
        return axios.post(url, data, options);
    }
};

export default new UT();