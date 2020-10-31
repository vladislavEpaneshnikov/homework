import React, {useContext, useState} from 'react';
import {CurrencyListContext} from "../context/CurrencyListProvider";
import {convertCurrency} from "../RestApi";
import ToggledButton from "../common/ToggledButton";
import CurrencyDropdown from "../common/CurrencyDropdown";
import CurrencyInput from "../common/CurrencyInput";

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
                        currentValue={form.amount}
                        setCurrency={(value) => setForm(prevState => ({
                            ...prevState,
                            amount: value
                        }))}/>
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
                        onClick={() => convertCurrency(form)
                            .then(({data}) => setResult(`${data} ${form.targetCurrency}`))
                        }/>
                </div>
            </div>
            {!!result && <div className="convert-result">Result: {result}</div>}
        </>
    );
};

export default ConverterForm;