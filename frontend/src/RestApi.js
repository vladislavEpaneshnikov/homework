import axios from 'axios';

export const getCurrencies = () => axios.get(restLinkCurrencies, requestConfig());

export const getList = () => axios.get(restLinkList, requestConfig());

export const convertCurrency = (params) => axios.get(restLinkCurrency, requestConfig(params));

export const editCustomRate = (params) => axios.post(restLinkCurrency, null, requestConfig(params));

export const deleteCustomRate = (params) => axios.delete(restLinkCurrency, requestConfig(params));

const requestConfig = (params) => ({headers: {Accept: "application/json"}, params});

const restLinkList = "http://localhost:8080/list";
const restLinkCurrency = "http://localhost:8080/currency";
const restLinkCurrencies = "http://localhost:8080/currencies";