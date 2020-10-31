import React from 'react';
import ContextProvider from "./context/ContextProvider";
import FeesTable from "./fees/FeesTable";
import FeesForm from "./fees/FeesForm";
import ConverterForm from "./converter/ConverterForm";
import "./index.scss"

const App = () =>
    <ContextProvider>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
        <h1>Currency Calculator</h1>

        <h2>Calculator</h2>
        <ConverterForm/>
        <hr className="section-separator"/>

        <h2>Add Custom Fee</h2>
        <FeesForm/>

        <hr className="section-separator"/>
        <h2>Saved Fees</h2>
        <FeesTable/>
    </ContextProvider>;

export default App;
