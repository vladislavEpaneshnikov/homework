import React, {useContext, useState} from 'react';
import {CurrencyListContext} from "../context/CurrencyListProvider";
import {CustomRatesContext} from "../context/CustomRatesListProvider";
import {editCustomRate} from "../RestApi";
import ToggledButton from "../common/ToggledButton";
import CurrencyDropdown from "../common/CurrencyDropdown";

const maxRate = 999999999.99;

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

                <input
                    id="fee-input"
                    className="currency-input"
                    type="number"
                    value={form.rate}
                    onChange={({target: {value}}) => setForm(prevState => ({
                        ...prevState,
                        rate: (value || 0) < maxRate ? value.substr(0, 30) : prevState.rate
                    }))}
                />
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