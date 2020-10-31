import React, {useContext, useState} from 'react';
import {CurrencyListContext} from "../context/CurrencyListProvider";
import CurrencyInput from 'react-currency-input-field';
import {convertCurrency} from "../RestApi";
import ToggledButton from "../common/ToggledButton";
import CurrencyDropdown from "../common/CurrencyDropdown";

const maxAmount = 999999999.99;

const ConverterForm = () => {
    const [form, setForm] = useState({baseCurrency: 'EUR', targetCurrency: 'USD'});
    const [result, setResult] = useState('');
    const {currencies} = useContext(CurrencyListContext);

    return (
        <>
            <div className="fees-form">
                <div>
                    <label htmlFor="amount">Amount</label>
                    <CurrencyInput
                        id="amount"
                        className="currency-input"
                        name="input-name"
                        groupSeparator=" "
                        value={form.amount}
                        allowDecimals
                        decimalsLimit={2}
                        onChange={(value) => setForm(prevState => ({...prevState, amount: value < maxAmount ? value : maxAmount }))}
                    />
                </div>

                <div>
                    <label>From</label>
                    <CurrencyDropdown currencies={currencies} value={form.baseCurrency} onChange={value => {
                        setForm(prevState => ({...prevState, baseCurrency: value}))
                    }}/>
                </div>

                <div>
                    <label>To</label>
                    <CurrencyDropdown currencies={currencies} value={form.targetCurrency} onChange={value => {
                        setForm(prevState => ({...prevState, targetCurrency: value}))
                    }}/>
                </div>

                <div className="button-wrapper">
                    <ToggledButton
                        icon={<i className="fa fa-angle-right"/>}
                        text="Convert"
                        active={form.amount > 0}
                        onClick={() => convertCurrency(form).then(({data}) => setResult(`${data} ${form.targetCurrency}`))}/>

                </div>
            </div>
            {!!result && <div className="convert-result">Result: {result}</div>}
        </>
    );
};

export default ConverterForm;