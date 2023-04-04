import axios from "axios";
import globalStore from "@/store";

const UT = {
    post : (url, data, opt) => {
        if(!url.startsWith("/api")) return;
        const options = opt || {};
        options.headers =  {
            "Authorization": "Bearer " + globalStore.state.userSession.token,
            "Content-Type": "application/json"
        }
        return axios.post("http://localhost:8080"+url, data, options);
    }
}
export default {
    install: (app, options) => {
        app.config.globalProperties.$UT = UT;
    }
}