import React from 'react';

const CurrencyDropdown = ({currencies, onChange, value}) => {

    const availableCurrencyOptions = currencies.map((currency, key) =>
        <div key={key} onClick={() => onChange(currency)}>{currency}</div>);

    return (
        <div className="currency-dropdown">
            <button className="currency-dropdown-btn">{value}<i className="fa fa-angle-down"/></button>
            <div className="currency-dropdown-content">
                {availableCurrencyOptions}
            </div>
        </div>
    )
};

export default CurrencyDropdown;
