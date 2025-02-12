import axios, {AxiosRequestConfig} from "axios";
import {BASE_URL} from "./system";
import * as authService from '../services/auth-service';
import {history} from "./history";

export function requestBackend(config: AxiosRequestConfig) {
    const headers = config.withCredentials
        ? {
            ...config.headers,
            Authorization: "Bearer " + authService.getAccessToken()
        }
        : config.headers;

    const fullConfig = { ...config, baseURL: BASE_URL, headers };
    
    console.log("Making request:", {
        method: fullConfig.method,
        url: fullConfig.url,
        headers: {
            ...fullConfig.headers,
            Authorization: fullConfig.headers?.Authorization ? "[HIDDEN]" : undefined
        },
        data: fullConfig.data
    });

    return axios(fullConfig)
        .catch(error => {
            if (error.response) {
                console.error("Response Error:", {
                    status: error.response.status,
                    statusText: error.response.statusText,
                    data: error.response.data,
                    headers: error.response.headers
                });
            } else if (error.request) {
                console.error("Request Error:", error.request);
            } else {
                console.error("Error:", error.message);
            }
            throw error;
        });
}

// REQUEST INTERCEPTOR
axios.interceptors.request.use(
    function (config) {
        return config;
    },
    function (error) {
        console.error("Request Interceptor Error:", error);
        return Promise.reject(error);
    }
);

// RESPONSE INTERCEPTOR
axios.interceptors.response.use(
    function (response) {
        return response;
    },
    function (error) {
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    console.error("Authentication failed:", error.response.data);
                    history.push("/login");
                    break;
                case 403:
                    console.error("Authorization failed:", error.response.data);
                    history.push("/catalog");
                    break;
                case 422:
                    console.error("Validation error:", error.response.data);
                    break;
                default:
                    console.error("Unexpected error:", error.response.data);
            }
        }
        return Promise.reject(error);
    }
);