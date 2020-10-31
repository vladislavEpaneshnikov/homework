import React from 'react';

const ToggledButton = ({onClick, text, active, icon}) =>
    active
        ? <button onClick={onClick}>{text.toUpperCase()}{icon}</button>
        : <button disabled>{text.toUpperCase()}{icon}</button>;

export default ToggledButton;