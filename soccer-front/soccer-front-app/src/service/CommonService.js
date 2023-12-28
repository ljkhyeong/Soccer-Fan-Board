
export const handleKeyDown = (event, callback) => {
    if (event.key === 'Enter') {
        if (event.nativeEvent.isComposing === false) {
            event.preventDefault();
            callback();
        }
    }
}

export const handleInputChange = (e, data, setData) => {
    const {name, value} = e.target;
    setData({
        ...data,
        [name] : value
    });
};

export const initStateObject = (state) => {
    const resetState = {};
    for (const key in state) {
        resetState[key] = "";
    }
    return resetState;
};

export const resetStates = (...setters) => {
    setters.forEach(setter => {
        setter('');
    });
};