import React, {useContext} from 'react';
import {CustomRatesContext} from "../context/CustomRatesListProvider";
import {deleteCustomRate} from "../RestApi";

const FeesTable = () => {
    const {customRates, refreshList} = useContext(CustomRatesContext);

    return (
        <>
            <table className="custom-fees-table">
                <tbody>
                {!customRates.length && <tr><td className="no-fees-saved">No Fees Saved</td></tr>}
                {customRates.map((fee, key) => <TableRow {...{...fee, key, refreshList}}/>)}
                </tbody>
            </table>
        </>
    )
};

const TableRow = ({baseCurrency, targetCurrency, rate, refreshList}) =>
    <tr>
        <td>{baseCurrency}</td>
        <td><i className="fa fa-arrow-right"/></td>
        <td>{targetCurrency}</td>
        <td>{rate}</td>
        <td className="button-wrapper">
            <button onClick={() => deleteCustomRate({baseCurrency, targetCurrency}).then(refreshList)}>
                REMOVE
            </button>
        </td>
    </tr>;

export default FeesTable;