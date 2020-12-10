
export const Utils = {
    checkIsPhone(phone) {
        var regex = /^(13[0-9]{9})|(15[0-9]{9})|(17[0-9]{9})|(18[0-9]{9})|(19[0-9]{9})$/;
        if (!regex.test(phone)) {
            return false;
        }
        return true;
    },
    checkisNum(str) {
        let res = parseInt(str);
        if (res != str) {
            let res2 = parseFloat(str);
            if (res2 == str) {
                return true;
            }
            // Object.is(parseFloat("akda"), NaN)
            return false;
        }
        return true;
    }
};