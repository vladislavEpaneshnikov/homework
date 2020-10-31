import React from 'react';

const CurrencyInput = ({currentValue, setCurrency, id}) =>
    <input
        id={id}
        className="currency-input"
        type="number"
        value={currentValue}
        onChange={({target: {value}}) =>
            setCurrency((value || 0) < maxAmount ? value.substr(0, lengthLimit) : currentValue)}
    />;

const lengthLimit = 30;
const maxAmount = 999999999.99;

export default CurrencyInput;
