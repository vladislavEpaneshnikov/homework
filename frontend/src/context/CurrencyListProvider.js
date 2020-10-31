import React, {createContext, useEffect, useState} from 'react';
import {getCurrencies} from "../RestApi";

const CurrencyListContext = createContext({});

const CurrencyListProvider = ({children}) => {
    const [currencies, setCurrencies] = useState([]);

    useEffect(() => {
        getCurrencies().then(({data}) => setCurrencies(data))
    }, []);

    return (
        <CurrencyListContext.Provider value={{currencies}}>{children}</CurrencyListContext.Provider>
    )
};

export {CurrencyListContext, CurrencyListProvider};