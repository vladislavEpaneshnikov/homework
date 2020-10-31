import React from 'react';
import {CustomRatesListProvider} from "./CustomRatesListProvider";
import {CurrencyListProvider} from "./CurrencyListProvider";

const ContextProvider = ({children}) =>
    <CurrencyListProvider>
        <CustomRatesListProvider>
            {children}
        </CustomRatesListProvider>
    </CurrencyListProvider>;

export default ContextProvider;