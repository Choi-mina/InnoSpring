"use strict";

/**
 * ajax get통신
 * @param url
 * @param param
 * @param successCallback
 * @param errorCallback
 * @param headerOptions = { tenantId: integer, tenantCode: string, companyId: string }
 */
var ajaxJsonCall = function(url, param, successCallback, errorCallback, headerOptions = {} ) {

    var contentType;
    var dataParam;
    var dataType;

    if (typeof param == "string") {
        contentType = "application/json;charset=UTF-8";
        dataParam = param;
        dataType = "json"
    } else if (typeof param == "object") {
        contentType = "application/json;charset=UTF-8";
        dataParam = $.param(param);
        dataType = "json"
    } else {
        // $.alert("ajaxJsonCall Type Error", function(){ hideLoading() });
    }

    $.ajax({
        type : 'GET'
        ,url : url
        ,contentType : contentType
        ,data : dataParam
        ,dataType : dataType
        ,beforeSend : function(xmlHttpRequest) {
            xmlHttpRequest.setRequestHeader("ajax", "true");
        }
        ,success : function(data) {
            try {
                if (data) {
                    if (typeof successCallback !== 'undefined') {
                        successCallback(data);
                    }
                }
            } catch (e) {
                alert(e.message);
            }
        }
        ,error : function(xhr, status, error) {
            // $.alert("Server Response Error:" + error, function(){ hideLoading() });
        }
    });
};

/**
 * ajax get통신(Sync)
 * @param url
 * @param param
 * @param successCallback
 * @param errorCallback
 * @param headerOptions = { tenantId: integer, tenantCode: string, companyId: string }
 */
var ajaxJsonCallSync = function(url, param, successCallback, errorCallback, headerOptions = {} ) {

    var contentType;
    var dataParam;
    var dataType;

    if (typeof param == "string") {
        contentType = "application/json;charset=UTF-8";
        dataParam = param;
        dataType = "json"
    } else if (typeof param == "object") {
        contentType = "application/json;charset=UTF-8";
        dataParam = $.param(param);
        dataType = "json"
    } else {
        // $.alert("ajaxJsonCall Type Error", function(){ hideLoading() });
    }

    $.ajax({
        type : 'GET'
        ,url : url
        ,contentType : contentType
        ,data : dataParam
        ,async : false
        ,dataType : dataType
        ,beforeSend : function(xmlHttpRequest) {
            xmlHttpRequest.setRequestHeader("ajax", "true");
        }
        ,success : function(data) {
            try {
                if (data) {
                    if (typeof successCallback !== 'undefined') {
                        successCallback(data);
                    }
                }
            } catch (e) {
                //alert(e.message, function(){ hideLoading() });
                hideLoading();
            }
        }
        ,error : function(xhr, status, error) {
            $.alert("Server Response Error:" + error, function(){ hideLoading() });
        }
    });
};

/**
 * ajax post통신
 * @param url
 * @param param
 * @param successCallback
 * @param errorCallback
 * @param headerOptions = { tenantId: integer, tenantCode: string, companyId: string }
 */
var ajaxPostJsonCall = function(url, param, csrfHeader, csrfToken, successCallback, errorCallback, headerOptions = {} ) {

    var contentType;
    var dataParam;
    var dataType;

    if (typeof param == "string") {
        contentType = "application/json;charset=UTF-8";
        dataParam = param;
        dataType = "json"
    } else if (typeof param == "object") {
        contentType = "application/json;charset=UTF-8";
        dataParam = JSON.stringify(param);
        dataType = "json"
    } else {
        $.alert("ajaxPostJsonCall Type Error", function(){ hideLoading() });
    }

    $.ajax({
        type : 'POST'
        ,url : url
        ,contentType : contentType
        ,data : dataParam
        ,dataType : dataType
        ,beforeSend: function(xmlHttpRequest) {
            // 기존 헤더 설정
            xmlHttpRequest.setRequestHeader("ajax", "true");
            // CSRF 헤더 설정 추가
            if (csrfHeader && csrfToken) {
                xmlHttpRequest.setRequestHeader(csrfHeader, csrfToken);
            }
        }
        ,success : function(data) {
            try {
                if (data) {
                    if (typeof successCallback !== 'undefined') {
                        successCallback(data);
                    }
                }
            } catch (e) {
                $.alert(e.message, function(){ hideLoading() });
            }
        }
        ,error : function(xhr, status, error) {
            // $.alert("서버 응답 오류:" + error, function(){ hideLoading() });
        }
    });
};

var asyncAjaxGet = function (url, param, headerOptions = {}) {

    return new Promise( (resolve, reject) => {
        var contentType;
        var dataParam;
        var dataType;

        if (typeof param == "string") {
            contentType = "application/json;charset=UTF-8";
            dataParam = param;
            dataType = "json"
        } else if (typeof param == "object") {
            contentType = "application/json;charset=UTF-8";
            dataParam = $.param(param);
            dataType = "json"
        } else {
            $.alert("ajaxJsonCall Type Error", function(){ hideLoading() });
        }

        $.ajax({
            type : 'GET'
            ,url :  url
            ,contentType : contentType
            ,data : dataParam
            ,dataType : dataType
            ,beforeSend : function(xmlHttpRequest) {
                xmlHttpRequest.setRequestHeader("ajax", "true");
            }
            ,success : function(data) {
                try {
                    if (data) {
                        resolve(data);
                    }
                } catch (e) {
                    //alert(e.message, function(){ hideLoading() });
                    hideLoading();
                    reject(e);
                }
            }
            ,error : function(xhr, status, error) {
                reject(error);
                $.alert("Server Response Error:" + error, function(){ hideLoading() });
            }
        });
    } )

}

var asyncAjaxPost = function (url, param, headerOptions = {}) {

    return new Promise( (resolve, reject) => {
        var contentType;
        var dataParam;
        var dataType;
        var responseData = {};

        if (typeof param == "string") {
            contentType = "application/json;charset=UTF-8";
            dataParam = param;
            dataType = "json"
        } else if (typeof param == "object") {
            contentType = "application/json;charset=UTF-8";
            dataParam = JSON.stringify(param);
            dataType = "json"
        } else {
            $.alert("ajaxPostJsonCall Type Error", function () {
                hideLoading()
            });
        }

        $.ajax({
            type: 'POST'
            , url: url
            , contentType: contentType
            , data: dataParam
            , dataType: dataType
            , beforeSend: function (xmlHttpRequest) {
                xmlHttpRequest.setRequestHeader("ajax", "true");
            }
            , success: function (data) {
                try {
                    if (data) {
                        resolve(data);
                    }
                } catch (e) {
                    $.alert(e.message, function () {
                        hideLoading()
                    });
                    reject(e);
                }
            }
            , error: function (xhr, status, error) {
                $.alert("서버 응답 오류:" + error, function () {
                    hideLoading()
                });
                reject(error);
            }
        });
    })
}