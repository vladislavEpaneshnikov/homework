import React, {createContext, useEffect, useState} from 'react';
import {getList} from "../RestApi";

const CustomRatesContext = createContext({});

const CustomRatesListProvider = ({children}) => {
    const [customRates, setCustomRates] = useState([]);

    const refreshList = () => {
        getList().then(({data}) => setCustomRates(data));
    };

    useEffect(() => {
        refreshList();
    }, []);

    return (
        <CustomRatesContext.Provider value={{customRates, refreshList}}>{children}</CustomRatesContext.Provider>
    )
};

export {CustomRatesContext, CustomRatesListProvider};