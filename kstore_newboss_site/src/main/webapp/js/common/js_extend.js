/**
 * Created by youzipi on 2016/1/20.
 */

String.prototype.contains = function (s, matchCase) {

    if (matchCase == (void 0)) {
        matchCase = false;
    }

    if (matchCase) {
        return this.indexOf(s) > -1;
    }
    else {
        return this.toLowerCase().indexOf(s) > -1;
    }
};

//String.prototype.contains = function (s) {
//    return this.contains(s, false);
//};

//function render(str, group) {
//    str = str.replace(/\$\{([^{}]+)\}/gm, function (m, n) {
//        return (group[n] != undefined) ? group[n] : '';
//    });
//    return str;
//}