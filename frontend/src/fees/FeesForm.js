import React, {useContext, useState} from 'react';
import {CurrencyListContext} from "../context/CurrencyListProvider";
import {CustomRatesContext} from "../context/CustomRatesListProvider";
import {editCustomRate} from "../RestApi";
import ToggledButton from "../common/ToggledButton";
import CurrencyDropdown from "../common/CurrencyDropdown";
import CurrencyInput from "../common/CurrencyInput";

const FeesForm = () => {
    const [form, setForm] = useState({baseCurrency: 'EUR', targetCurrency: 'USD'});
    const {currencies} = useContext(CurrencyListContext);
    const {refreshList} = useContext(CustomRatesContext);

    return (
        <div className="fees-form">

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

            <div>
                <label htmlFor="fee-input">Fee</label>
                <CurrencyInput
                    id="fee-input"
                    currentValue={form.rate}
                    setCurrency={(value) => setForm(prevState => ({
                        ...prevState,
                        rate: value
                    }))}/>
            </div>

            <div className="button-wrapper">
                <ToggledButton
                    icon={<i className="fa fa-plus"/>}
                    text="Add"
                    active={form.rate > 0}
                    onClick={() => editCustomRate(form).then(refreshList)}/>
            </div>
        </div>
    );
};

export default FeesForm;